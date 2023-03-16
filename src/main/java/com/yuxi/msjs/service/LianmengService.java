package com.yuxi.msjs.service;

import com.yuxi.msjs.bean.entity.Lianmeng;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.vo.LianmengList;
import com.yuxi.msjs.bean.vo.Lmcy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/01 5:39 下午
 */
@Service
public class LianmengService {
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 创建联盟
     * @param userId
     * @param lmmc
     * @author songhongxing
     * @date 2023/03/01 5:41 下午
     */
    public List<Lmcy> cjlm(String userId,String lmmc){
        Lianmeng lianmeng = new Lianmeng();
        lianmeng.setLmmc(lmmc);
        lianmeng.setLmId(UUID.randomUUID().toString().replaceAll("-", ""));
        lianmeng.setLmrl(30);
        lianmeng.setLmrs(1);
        Query query = new Query(Criteria.where("userId").is(userId));
        mongoTemplate.save(lianmeng);
        Update update = new Update();
        update.set("lmId", lianmeng.getLmId());
        update.set("lmgz", 2);
        update.set("lmgzZw", "盟主");
        mongoTemplate.updateFirst(query, update, "user");
        return lmcyList(lianmeng.getLmId());
    }

    public List<Lmcy> lmcyList(String lmId){
        List<Lmcy> lmcyList = new ArrayList<>();
        Query query = new Query(Criteria.where("lmId").is(lmId));
        query.with(Sort.by(Sort.Order.desc("lmgz")));
        List<User> users = mongoTemplate.find(query, User.class);
        Lmcy lmcy;
        for(User user : users){
            lmcy = new Lmcy();
            lmcy.setUserId(user.getUserId());
            lmcy.setName(user.getName());
            lmcy.setLmgx(user.getLmgx());
            lmcy.setJunxian(user.getJunxian());
            lmcy.setLmgzZw(user.getLmgzZw());
            lmcyList.add(lmcy);
        }
        return lmcyList;
    }

    /**
     * 联盟列表
     * @author songhongxing
     * @date 2023/03/02 10:15 上午
     */
    public List<LianmengList> lmlb(){
//        List<Lianmeng> lianmengList = mongoTemplate.findAll(Lianmeng.class);
        List<Lianmeng> lianmengList = mongoTemplate.findAll(Lianmeng.class, "lianmeng");
        LianmengList lianmeng;
        List<LianmengList> result = new ArrayList<>();
        Query query;
        User one;
        for (Lianmeng bean : lianmengList) {
            lianmeng = new LianmengList();
            lianmeng.setLmId(bean.getLmId());
            query = new Query(Criteria.where("lmId").is(bean.getLmId()).and("lmgz").is(2));
            one = mongoTemplate.findOne(query, User.class);
            lianmeng.setLmmc(bean.getLmmc());
            lianmeng.setMz(one.getName());
            lianmeng.setLmdj(bean.getLmdj());
            lianmeng.setLmrs(bean.getLmrs());
            lianmeng.setLmrl(bean.getLmrl());
            result.add(lianmeng);
        }
        return result;
    }

    public Lianmeng info(String lmId) {
        Query lm = new Query(Criteria.where("lmId").is(lmId));
        return mongoTemplate.findOne(lm, Lianmeng.class);
    }
}
