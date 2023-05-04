package com.yuxi.msjs.service;

import com.yuxi.msjs.bean.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 货币加减
 *
 * @author songhongxing
 * @date 2023/04/20 3:36 下午
 */
@Service
public class HuobiService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void huobi(String userId, Integer hj, String hblx){
        Query query = new Query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(query, User.class);
        Update update = new Update();
        if("hj".equals(hblx)){
            update.set("hj", user.getHj() + hj);
        } else {
            update.set("sw", user.getSw() + hj);
        }
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void zyzj (){

    }
}
