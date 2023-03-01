package com.yuxi.msjs.service;

import com.yuxi.msjs.bean.entity.Lianmeng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    public void cjlm(String userId,String lmmc){

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
        mongoTemplate.updateFirst(query, update, "user");
    }
}
