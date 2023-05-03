package com.yuxi.msjs.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.mongodb.client.result.UpdateResult;
import com.yuxi.msjs.bean.entity.Fuwuqi;
import com.yuxi.msjs.bean.entity.SlgMap;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.ZhangHao;
import com.yuxi.msjs.util.GameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MapService mapService;
    @Autowired
    private CityService cityService;


    /**
     * 服务器列表
     * @return
     */
    public List<Fuwuqi> fwqLb(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "index"));
        return mongoTemplate.find(query, Fuwuqi.class);
    }

    /**
     * 注册账号
     * @param zh
     * @param mm
     * @param fwqId
     * @param fwq
     * @return
     */
    public ZhangHao zhuce(String zh, String mm, Integer fwqId, String fwq){
        Query query = new Query(Criteria.where("zh").is(zh).and("mm").is(mm));
        ZhangHao zhangHao = mongoTemplate.findOne(query, ZhangHao.class);
        String userId = "";
        if(zhangHao == null){
            userId = GameUtil.uuid();
            zhangHao = new ZhangHao();
            zhangHao.setZh(zh);
            zhangHao.setMm(mm);
            zhangHao.setFwqId(fwqId);
            zhangHao.setFwq(fwq);
            zhangHao.setUserId(userId);
            query = new Query(Criteria.where("index").is(fwqId));
            Fuwuqi one = mongoTemplate.findOne(query, Fuwuqi.class);
            zhangHao.setFwqUrl(one.getUrl());
            mongoTemplate.save(zhangHao);
            insertUser("玩家"+ userId.substring(userId.length() - 6), userId);
        }

        return zhangHao;
    }

    public ZhangHao login(String zh, String mm){
        Query query = new Query(Criteria.where("zh").is(zh).and("mm").is(mm));
        return mongoTemplate.findOne(query, ZhangHao.class);
    }

    /**
     * 用户信息
     * @param userId
     * @return
     */
    public User userInfo(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    /**
     * 创建角色
     * @param name
     * @param userId
     * @return
     */
    public User insertUser(String name, String userId){
        //创建城市
        Integer zuobiao = mapService.zuobiao();
        //创建账户
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        UserCity city = cityService.createCity(userId, name, zuobiao);
        user.setZcId(city.getCityId());
        mongoTemplate.save(user);
        return user;
    }


    public User findById(String userId, boolean gxsj) {
        Query query = new Query(Criteria.where("userId").is(userId));
        if(gxsj){
            Update update = new Update();
            update.set("dlsj",DateUtil.now());
            update.set("sjms",(int)DateUtil.currentSeconds());
            mongoTemplate.updateFirst(query, update, User.class);
        }
        User user = mongoTemplate.findOne(query, User.class);
        Update update = new Update();
        update.set("xdl",1);
        mongoTemplate.updateFirst(query, update, User.class);
        return user;
    }

    public Long zhcheck(String zh) {
        Query zhanghao = new Query(Criteria.where("zh").is(zh));
        return mongoTemplate.count(zhanghao, ZhangHao.class);
    }

    /**
     * 改名
     * @param userId
     * @param name
     */
    public User gaiming(String userId, String name) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.set("name", name);
        mongoTemplate.updateFirst(query, update, User.class);
        return findById(userId, false);

    }
}
