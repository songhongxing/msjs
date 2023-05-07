package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.yuxi.msjs.bean.conste.Daoju;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.UserDaoju;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/03 11:34 上午
 */
@Service
public class DaojuService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private HuobiService huobiService;


    public List<UserDaoju> insert(List<UserDaoju> userDaojus) {
        Query query;
        //判断这个用户有没有这个道具,有的话就+1
        for (UserDaoju userDaoju : userDaojus) {
            query = new Query(Criteria.where("userId").is(userDaoju.getUserId()).and("name").is(userDaoju.getName()));
            userDaoju.setMiaoshu(Daoju.getMiaoshuz(userDaoju.getName()));
            userDaoju.setKsy(Daoju.getKsyz(userDaoju.getName()));
            UserDaoju daoju = mongoTemplate.findOne(query, UserDaoju.class);
            if(daoju == null){
                mongoTemplate.save(userDaoju);
            } else {
                Update update = new Update();
                update.set("sl", daoju.getSl() + userDaoju.getSl());
                mongoTemplate.updateFirst(query, update, UserDaoju.class);
            }
        }
        query = new Query(Criteria.where("userId").is(userDaojus.get(0).getUserId()));
        userDaojus = mongoTemplate.find(query, UserDaoju.class);
        return userDaojus;
    }

    public List<UserDaoju> lb(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        List<UserDaoju> userDaojus = mongoTemplate.find(query, UserDaoju.class);
        return userDaojus;
    }

    /**
     * 道具使用
     * @author songhongxing
     * @date 2023/03/07 10:02 上午
     */
    public void djsy(String userId,String name, Integer sl){
        Query query = new Query(Criteria.where("userId").is(userId).and("name").is(name));
        UserDaoju daoju = mongoTemplate.findOne(query, UserDaoju.class);
        if(daoju == null){
            return;
        }
        //计算剩余数量
        int sy = daoju.getSl() - sl;
        //如果剩余数量=0,把这个记录删除
        if (sy == 0) {
            mongoTemplate.remove(query, UserDaoju.class);
        } else {
            Update update = new Update();
            update.set("sl", sy);
            mongoTemplate.updateFirst(query, update, UserDaoju.class);
        }
        if ("小袋黄金".equals(name)) {
            huobiService.huobi(userId, 50 * sl, "hj");
        } else if ("中袋黄金".equals(name)) {
            huobiService.huobi(userId, 100 * sl, "hj");
        } else if ("大袋黄金".equals(name)) {
            huobiService.huobi(userId, 200 * sl, "hj");
        }
    }

    /**
     * 使用道具增加资源
     * @param cityId
     * @param djname  数量
     */
    public Map<String, Object> zjzy(String userId, String cityId, String djname, Integer sl){
        Query djQuery = new Query(Criteria.where("userId").is(userId).and("name").is(djname));
        UserDaoju userDaoju = mongoTemplate.findOne(djQuery, UserDaoju.class);
        if(userDaoju == null){
            return djsl(userId);
        }
        int zysl = 0;
        if("大箱资源".equals(djname)){
            zysl = 200000 * sl;
        } else {
            zysl = 50000 * sl;
        }
        Query query = new Query(Criteria.where("cityId").is(cityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Update update = new Update();
        update.set("mucc", userCity.getMucc() + zysl > userCity.getCkcc() ? userCity.getCkcc() : userCity.getMucc() + zysl);
        update.set("tiecc", userCity.getTiecc() + zysl > userCity.getCkcc() ? userCity.getCkcc() : userCity.getTiecc() + zysl);
        update.set("shicc", userCity.getShicc() + zysl > userCity.getCkcc() ? userCity.getCkcc() : userCity.getShicc() + zysl);
        update.set("liangcc", userCity.getLiangcc() + zysl > userCity.getLccc() ? userCity.getLccc() : userCity.getLiangcc() + zysl);
        mongoTemplate.updateFirst(query, update, UserCity.class);
        update = new Update();
        update.set("sl", userDaoju.getSl() - 1);
        mongoTemplate.updateFirst(djQuery, update, UserDaoju.class);
        return djsl(userId);
    }

    public Map<String, Object> djsl(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        List<UserDaoju> userDaojus = mongoTemplate.find(query, UserDaoju.class);
        Map<String, Object> map = new HashMap<>();
        if (CollUtil.isNotEmpty(userDaojus)) {
            for (UserDaoju userDaoju : userDaojus) {
                map.put(userDaoju.getName(), userDaoju.getSl());
            }
        }
        return map;
    }

    /**
     * 购买道具
     *
     * @param userId
     * @param gg     是否是广告
     * @param hblx   货币类型(黄金/声望)
     */
    public User gmdj(String userId,String daojuName, Integer gg, String hblx ) {
        Query query = new Query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(query, User.class);
        if (gg == 0) {
            if ("hj".equals(hblx)) {
                if(user.getHj() > Daoju.getHjz(daojuName)){
                    int hj = -Daoju.getHjz(daojuName);
                    huobiService.huobi(userId, hj, "hj");
                } else {
                    return user;
                }
            } else if ("sw".equals(hblx)) {
                if(user.getSw() > Daoju.getSwz(daojuName)){
                    int sw = -Daoju.getSwz(daojuName);
                    huobiService.huobi(userId, sw, "sw");
                } else {
                    return user;
                }
            }
        }
        UserDaoju userDaoju = new UserDaoju();
        userDaoju.setUserId(userId);
        userDaoju.setName(daojuName);
        userDaoju.setMiaoshu(Daoju.getMiaoshuz(daojuName));
        userDaoju.setSl(1);
        userDaoju.setKsy(Daoju.getKsyz(daojuName));
        insert(ListUtil.toList(userDaoju));
        query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

}
