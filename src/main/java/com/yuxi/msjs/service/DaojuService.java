package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import com.yuxi.msjs.bean.conste.Daoju;
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


    public List<UserDaoju> insert(UserDaoju userDaoju){
        //判断这个用户有没有这个道具,有的话就+1
        Query query = new Query(Criteria.where("userId").is(userDaoju.getUserId()).and("name").is(userDaoju.getName()));
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
        query = new Query(Criteria.where("userId").is(userDaoju.getUserId()));
        List<UserDaoju> userDaojus = mongoTemplate.find(query, UserDaoju.class);
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
        //计算剩余数量
        int sy = daoju.getSl() - sl;
        //如果剩余数量=0,把这个记录删除
        if(sy == 0){
            mongoTemplate.remove(query, UserDaoju.class);
        } else {
            Update update = new Update();
            update.set("sl", sy);
            mongoTemplate.updateFirst(query, update, UserDaoju.class);
        }

    }

    public Map<String, Object> djsl(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        List<UserDaoju> userDaojus = mongoTemplate.find(query, UserDaoju.class);
        Map<String, Object> map = new HashMap<>();
        if(CollUtil.isNotEmpty(userDaojus)){
            for (UserDaoju userDaoju : userDaojus) {
                map.put(userDaoju.getName(), userDaoju.getSl());
            }
        }
        return map;
    }

}
