package com.yuxi.msjs.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.mongodb.client.result.UpdateResult;
import com.yuxi.msjs.bean.entity.SlgMap;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
     * 用户信息
     * @param userId
     * @return
     */
    public User userInfo(String userId){
        return mongoTemplate.findById(userId, User.class);
    }

    public User insertUser(String name){
        String userId= UUID.randomUUID().toString().replaceAll("-", "");
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


    public User findById(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }
}
