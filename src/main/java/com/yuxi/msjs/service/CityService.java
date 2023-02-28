package com.yuxi.msjs.service;

import cn.hutool.core.lang.UUID;
import com.yuxi.msjs.bean.Jianzhu;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.UserCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 用户城市列表
     * @param userId
     * @author songhongxing
     * @date 2023/02/27 10:42 上午
     */
    public List<UserCity> userCitys(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, UserCity.class);
    }

    /**
     * 用户城市信息
     *
     * @param cityId
     * @return
     */
    public UserCity userCity(String cityId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(cityId));
        return mongoTemplate.findOne(query, UserCity.class);
    }

    /**
     * 添加建筑升级
     *
     * @param cityId 城市id
     * @param jzName 建筑名称
     * @param jzdj   建筑等级
     * @param sjsj   升级时间
     * @author songhongxing
     * @date 2023/02/28 4:55 下午
     */
    public void jzshengji(String cityId, String jzName, Integer jzdj, Integer sjsj) {
        HomeUp homeUp = new HomeUp();
        homeUp.setCityId(cityId);
        homeUp.setJzName(jzName);
        homeUp.setJzdj(jzdj+1);
        homeUp.setDqsj(System.currentTimeMillis()/1000 + sjsj);
        mongoTemplate.save(homeUp);
    }

    /**
     * 建筑升级完成
     * @param cityId
     * @param jzName
     * @author songhongxing
     * @date 2023/02/28 5:02 下午
     */
    public void sjwc(String cityId, String jzName){
        //删除建筑队列
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(cityId).and("jzName").is(jzName));
        HomeUp homeUp = mongoTemplate.findOne(query, HomeUp.class);
        mongoTemplate.remove(query, "home_up");
        //修改城市的建筑等级
        query = new Query();
        query.addCriteria(Criteria.where("cityId").is(cityId));
        Update update = new Update();
        update.set(Jianzhu.getKeyByValue(jzName), homeUp.getJzdj());
        mongoTemplate.updateFirst(query, update, UserCity.class);
    }
}
