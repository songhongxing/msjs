package com.yuxi.msjs.service;

import cn.hutool.core.lang.UUID;
import com.yuxi.msjs.bean.entity.UserCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 创建城市
     * @param userId
     * @param userName
     * @param zuobiao
     */
    public UserCity createCity(String userId, String userName, Integer zuobiao){
        UserCity userCity = new UserCity();
        userCity.setCityId(UUID.randomUUID().toString().replaceAll("-", ""));
        userCity.setUserId(userId);
        userCity.setZuobiao(zuobiao);
        userCity.setCityName(userName);
        mongoTemplate.save(userCity);
        return userCity;
    }
}
