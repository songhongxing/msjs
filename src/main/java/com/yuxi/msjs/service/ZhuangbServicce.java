package com.yuxi.msjs.service;

import cn.hutool.core.util.StrUtil;
import com.yuxi.msjs.bean.conste.Zhuangbei;
import com.yuxi.msjs.bean.entity.UserZb;
import com.yuxi.msjs.bean.entity.Wujiang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 装备相关
 *
 * @author songhongxing
 * @date 2023/03/28 3:24 下午
 */
@Service
public class ZhuangbServicce {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加装备
     * @param userId
     * @param zbmc
     * @return
     */
    public List<UserZb> insert(String userId, String zbmc){
        UserZb userZb = new UserZb();
        userZb.setUserId(userId);
        userZb.setCityId(null);
        userZb.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        userZb.setZblx(Zhuangbei.getLx(zbmc));
        userZb.setZbmc(zbmc);
        userZb.setWy(Zhuangbei.getWyz(zbmc));
        userZb.setFy(Zhuangbei.getFyz(zbmc));
        userZb.setSd(Zhuangbei.getSdz(zbmc));
        userZb.setZm(Zhuangbei.getZlz(zbmc));
        mongoTemplate.save(userZb);
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, UserZb.class);
    }

    /**
     * 个人装备列表
     * @param userId
     * @return
     */
    public List<UserZb> find(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, UserZb.class);
    }

    /**
     * 武将装备
     * @param userId
     * @param cityId
     * @param wjmc
     * @return
     */
    public Map<String, Object> wjzb(String userId, String cityId, String wjmc){
        Query query = new Query(Criteria.where("userId").is(userId).and("cityId").is(cityId).and("name").is(wjmc));
        Wujiang wujiang = mongoTemplate.findOne(query, Wujiang.class);
        Map<String, Object> result = new HashMap<>();
        result.put("wq", wujiang.getWq());
        result.put("fj", wujiang.getFj());
        result.put("zq", wujiang.getZq());
        result.put("sp", wujiang.getSp());
        return result;
    }

    /**
     * 添加武将装备
     * @param userId
     * @param cityId
     * @param wjmc
     * @return
     */
    public Map<String, Object> tjwjzb(String userId, String cityId, String wjmc, String zbId){
        //查询武将现在的装备,如果有装备,先卸下来再添加新的装备
        Query query = new Query(Criteria.where("userId").is(userId).and("cityId").is(cityId).and("name").is(wjmc));
        Wujiang wujiang = mongoTemplate.findOne(query, Wujiang.class);
        Query zbQuery = new Query(Criteria.where("userId").is(userId).and("zbId").is(zbId));
        UserZb userZb = mongoTemplate.findOne(zbQuery, UserZb.class);
        Update wjUpdate = new Update();
        if(userZb.getZblx().equals("武器")){
            if(StrUtil.isNotBlank(wujiang.getWq())){
                wjUpdate.set("wqId", userZb.getZbId());
                wjUpdate.set("wq", userZb.getZbmc());
            }
        }
        return null;
    }
}
