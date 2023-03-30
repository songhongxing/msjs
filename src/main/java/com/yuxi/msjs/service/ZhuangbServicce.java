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
        userZb.setZl(Zhuangbei.getZlz(zbmc));
        userZb.setPz(Zhuangbei.getZbpz(zbmc));
        mongoTemplate.save(userZb);
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, UserZb.class);
    }

    /**
     * 个人装备列表
     *
     * @param userId
     * @return
     */
    public List<UserZb> find(String userId, String zblx) {
        Query query = new Query(Criteria.where("userId").is(userId));
        if (StrUtil.isNotEmpty(zblx)) {
            query.addCriteria(Criteria.where("zblx").is(zblx).and("wjId").is("无"));
        }
        return mongoTemplate.find(query, UserZb.class);
    }

    /**
     * 武将装备
     *
     * @param userId
     * @param cityId
     * @param wjId
     * @return
     */
    public Map<String, Object> wjzb(String userId, String cityId, String wjId) {
        Query query = new Query(Criteria.where("userId").is(userId).and("cityId").is(cityId).and("wjId").is(wjId));
        Wujiang wujiang = mongoTemplate.findOne(query, Wujiang.class);
        Map<String, Object> result = new HashMap<>();
        result.put("wq", wujiang.getWqId() + "|" + wujiang.getWq());
        result.put("fj", wujiang.getFjId() + "|" + wujiang.getFj());
        result.put("zq", wujiang.getZqId() + "|" + wujiang.getZq());
        result.put("sp", wujiang.getSpId() + "|" + wujiang.getSp());
        return result;
    }

    /**
     * 添加武将装备
     * @param userId
     * @param cityId
     * @param wjId
     * @return
     */
    public Map<String, Object> tjwjzb(String userId, String cityId, String wjId, String zbId){
        //查询武将现在的装备,如果有装备,先卸下来再添加新的装备
        Query wjQuery = new Query(Criteria.where("userId").is(userId).and("cityId").is(cityId).and("wjId").is(wjId));
        Wujiang wujiang = mongoTemplate.findOne(wjQuery, Wujiang.class);
        Query zbQuery = new Query(Criteria.where("userId").is(userId).and("zbId").is(zbId));
        UserZb userZb = mongoTemplate.findOne(zbQuery, UserZb.class);
        Update wjUpdate = new Update();
        Query oldzbQuery = new Query();
        Update zbUpdate = new Update();//本次装备更新
        Update oldzbUpdate = new Update();//老装备更新
        if(userZb.getZblx().equals("武器")){
            //如果武将原来有装备,卸下原装备,新装备的武将id改成当前武将
            if (!wujiang.getWq().equals("无")) {
                oldzbQuery = new Query(Criteria.where("zbId").is(wujiang.getWqId()));
                UserZb olduserZb = mongoTemplate.findOne(oldzbQuery, UserZb.class);//老装备
                oldzbUpdate.set("wjId", "无");
                oldzbUpdate.set("wjName", "无");
                int wy = wujiang.getWl() - olduserZb.getWy() + userZb.getWy();
                wjUpdate.set("wl", wy);
                mongoTemplate.updateFirst(oldzbQuery, oldzbUpdate, UserZb.class);
            } else {
                int wy = wujiang.getWl() + userZb.getWy();
                wjUpdate.set("wl", wy);
            }
            wjUpdate.set("wqId", userZb.getZbId());
            wjUpdate.set("wq", userZb.getZbmc());
        } else if (userZb.getZblx().equals("防具")) {
            //如果武将原来有装备,卸下原装备,新装备的武将id改成当前武将
            if (!wujiang.getFj().equals("无")) {
                oldzbQuery = new Query(Criteria.where("zbId").is(wujiang.getFjId()));
                UserZb olduserZb = mongoTemplate.findOne(oldzbQuery, UserZb.class);//老装备
                int fy = wujiang.getFy() - olduserZb.getFy() + userZb.getFy();
                wjUpdate.set("fy", fy);
                oldzbUpdate.set("wjId", "无");
                oldzbUpdate.set("wjName", "无");
                mongoTemplate.updateFirst(oldzbQuery, oldzbUpdate, UserZb.class);
            } else {
                int fy = wujiang.getFy() + userZb.getFy();
                wjUpdate.set("fy", fy);
            }
            wjUpdate.set("fjId", userZb.getZbId());
            wjUpdate.set("fj", userZb.getZbmc());
        } else if (userZb.getZblx().equals("坐骑")) {
            //如果武将原来有装备,卸下原装备,新装备的武将id改成当前武将
            if (!wujiang.getZq().equals("无")) {
                oldzbQuery = new Query(Criteria.where("zbId").is(wujiang.getZqId()));
                UserZb olduserZb = mongoTemplate.findOne(oldzbQuery, UserZb.class);//老装备
                int sd = wujiang.getSd() - olduserZb.getSd() + userZb.getSd();
                wjUpdate.set("sd", sd);
                oldzbUpdate.set("wjId", "无");
                oldzbUpdate.set("wjName", "无");
                mongoTemplate.updateFirst(oldzbQuery, oldzbUpdate, UserZb.class);
            } else {
                int sd = wujiang.getSd() + userZb.getSd();
                wjUpdate.set("sd", sd);
            }
            wjUpdate.set("zqId", userZb.getZbId());
            wjUpdate.set("zq", userZb.getZbmc());
        } else if(userZb.getZblx().equals("饰品")){
            //如果武将原来有装备,卸下原装备,新装备的武将id改成当前武将
            if (!wujiang.getSp().equals("无")) {
                oldzbQuery = new Query(Criteria.where("zbId").is(wujiang.getSpId()));
                UserZb olduserZb = mongoTemplate.findOne(oldzbQuery, UserZb.class);//老装备
                int zl = wujiang.getZl() - olduserZb.getZl() + userZb.getZl();
                wjUpdate.set("sd", zl);
                oldzbUpdate.set("wjId", "无");
                oldzbUpdate.set("wjName", "无");
                mongoTemplate.updateFirst(oldzbQuery, oldzbUpdate, UserZb.class);
            } else {
                int zl = wujiang.getZl() + userZb.getZl();
                wjUpdate.set("sd", zl);
            }
            wjUpdate.set("spId", userZb.getZbId());
            wjUpdate.set("sp", userZb.getZbmc());
        }
        zbUpdate.set("wjId", wujiang.getWjId());
        zbUpdate.set("wjName", wujiang.getName());
        mongoTemplate.updateFirst(zbQuery, zbUpdate, UserZb.class);
        mongoTemplate.updateFirst(wjQuery, wjUpdate, Wujiang.class);
        return wjzb(userId, cityId, wjId);
    }
}
