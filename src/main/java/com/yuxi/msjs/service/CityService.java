package com.yuxi.msjs.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.yuxi.msjs.bean.Bzzy;
import com.yuxi.msjs.bean.Jianzhu;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.ZhengBing;
import com.yuxi.msjs.bean.vo.CityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public List<CityList> userCitys(String userId){
        List<CityList> cityList = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<UserCity> userCities = mongoTemplate.find(query, UserCity.class);
        CityList city;
        for(UserCity userCity : userCities){
            city = new CityList();
            city.setCityId(userCity.getCityId());
            city.setCityName(userCity.getCityName());
            city.setZgm(userCity.getZgm());
            city.setZbl(userCity.getZbl());
            city.setZuobiao(userCity.getZuobiao());
            cityList.add(city);
        }
        return cityList;
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
    public List<HomeUp> jzshengji(String cityId, String jzName, Integer jzdj, Integer sjsj) {
        HomeUp homeUp = new HomeUp();
        homeUp.setCityId(cityId);
        homeUp.setJzName(jzName);
        homeUp.setJzdj(jzdj+1);
        long dqsj = System.currentTimeMillis() / 1000 + sjsj;
        homeUp.setDqsj((int) dqsj);
        mongoTemplate.save(homeUp);
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, HomeUp.class);
    }

    /**
     * 建筑升级完成
     * @param cityId
     * @param jzName
     * @author songhongxing
     * @date 2023/02/28 5:02 下午
     */
    public List<HomeUp> sjwc(String cityId, String jzName){
        //删除建筑队列
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(cityId).and("jzName").is(jzName));
        HomeUp homeUp = mongoTemplate.findOne(query, HomeUp.class);
        mongoTemplate.remove(query, "home_up");
        //修改城市的建筑等级
        query = new Query();
        query.addCriteria(Criteria.where("cityId").is(cityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Update update = new Update();
        update.set(Jianzhu.getKeyByValue(jzName), homeUp.getJzdj());
        update.set("zgm", userCity.getZgm() + 10);
        mongoTemplate.updateFirst(query, update, UserCity.class);
        return mongoTemplate.find(query, HomeUp.class);
    }

    /**
     * 建筑队列
     * @param cityId
     * @return
     */
    public List<HomeUp> jzdl(String cityId){
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, HomeUp.class);
    }

    /**
     * 征兵队列
     * @param cityId
     * @param bz
     * @param sl
     * @param dghs
     * @author songhongxing
     * @date 2023/03/02 3:53 下午
     */
    public List<ZhengBing> zhengbing(String cityId, String bz, Integer sl, Integer dghs) {
        ZhengBing zhengBing = new ZhengBing();
        zhengBing.setCityId(cityId);
        zhengBing.setBz(bz);
        zhengBing.setSl(sl);
        zhengBing.setYzm(0);
        zhengBing.setDghs(dghs);
        zhengBing.setZhs(dghs*sl);
        zhengBing.setKssj((int)DateUtil.currentSeconds());
        zhengBing.setJssj(zhengBing.getKssj() +zhengBing.getZhs());
        mongoTemplate.save(zhengBing);
        Query query = new Query(Criteria.where("cityId").is(cityId));
        //扣除城市资源
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Update update = new Update();
        update.set("mucc", userCity.getMucc() - Bzzy.getMuz(bz) * sl);
        update.set("shicc", userCity.getShicc() - Bzzy.getShiz(bz) * sl);
        update.set("tiecc", userCity.getTiecc() - Bzzy.getTiez(bz) * sl);
        update.set("liangcc", userCity.getLiangcc() - Bzzy.getLiangz(bz) * sl);
        mongoTemplate.updateFirst(query, update, "user_city");
        return mongoTemplate.find(query, ZhengBing.class);

    }

    /**
     * 征兵队列
     * @param cityId
     * @author songhongxing
     * @date 2023/03/02 4:03 下午
     */
    public List<ZhengBing> zbdl(String cityId) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, ZhengBing.class);
    }
}
