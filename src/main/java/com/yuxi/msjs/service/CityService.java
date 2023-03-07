package com.yuxi.msjs.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.yuxi.msjs.bean.Bzzy;
import com.yuxi.msjs.bean.Jianzhu;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.Meinv;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.UserDaoju;
import com.yuxi.msjs.bean.entity.Wujiang;
import com.yuxi.msjs.bean.entity.ZhengBing;
import com.yuxi.msjs.bean.vo.CityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DaojuService daojuService;


    /**
     * 创建城市
     *
     * @param userId
     * @param userName
     * @param zuobiao
     */
    public UserCity createCity(String userId, String userName, Integer zuobiao) {
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


    public List<Wujiang> xzwj(String userId, String cityId, String name, Integer wl, Integer fy, Integer sd, Integer zl){
        Wujiang wujiang = new Wujiang();
        wujiang.setUserId(userId);
        wujiang.setCityId(cityId);
        wujiang.setName(name);
        wujiang.setWl(wl);
        wujiang.setFy(fy);
        wujiang.setZl(zl);
        wujiang.setSd(sd);
        mongoTemplate.save(wujiang);
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return  mongoTemplate.find(query, Wujiang.class);
    }

    public List<Wujiang> wjlb(String cityId){
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return  mongoTemplate.find(query, Wujiang.class);
    }

    /**
     * 新增美女
     * @return
     */
    public List<Meinv> xzmv(String userId, String cityId, String name, Integer ly, Integer sc, Integer zl, Integer cy, Integer ml, Integer dj) {
        Meinv meinv = new Meinv();
        meinv.setUserId(userId);
        meinv.setCityId(cityId);
        meinv.setName(name);
        meinv.setSc(sc);
        meinv.setLy(ly);
        meinv.setCy(cy);
        meinv.setZl(zl);
        meinv.setMl(ml);
        meinv.setDj(dj);
        mongoTemplate.save(meinv);
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, Meinv.class);
    }

    public List<Meinv> mvlb(String cityId) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, Meinv.class);
    }

    public List<Meinv> tjmvsx(String userId, String cityId, String name, String sx, Integer sxd) {
        Query query = new Query(Criteria.where("cityId").is(cityId).and("name").is(name));
        Meinv meinv = mongoTemplate.findOne(query, Meinv.class);
        Update update = new Update();
        if ("礼仪".equals(sx)) {
            update.set("ly", meinv.getLy() + sxd);
        } else if ("身材".equals(sx)) {
            update.set("sc", meinv.getSc() + sxd);
        } else if ("智力".equals(sx)) {
            update.set("zl", meinv.getZl() + sxd);
        } else if ("才艺".equals(sx)) {
            update.set("cy", meinv.getCy() + sxd);
        } else if ("魅力".equals(sx)) {
            update.set("ml", meinv.getMl() + sxd);
        }
        mongoTemplate.updateFirst(query, update, "meinv");
        //扣减道具数量
        daojuService.djsy(userId, "玉女心经", 1);
        Query lbQuery = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(lbQuery, Meinv.class);
    }

    public Integer ynxjsl(String userId, String name) {
        Query query = new Query(Criteria.where("userId").is(userId).and("name").is(name));
        UserDaoju one = mongoTemplate.findOne(query, UserDaoju.class);
        if (one != null) {
            return one.getSl();
        }
        return 0;

    }

    public List<Meinv> xq(String userId, String cityId, String name) {
        Query query = new Query(Criteria.where("userId").is(userId).and("cityId").is(cityId).and("name").is(name));
        mongoTemplate.remove(query, Meinv.class);
        query = new Query(Criteria.where("userId").is(userId).and("cityId").is(cityId));
        return mongoTemplate.find(query, Meinv.class);
    }

    /**
     * 添加武将
     */
    public List<Wujiang> tjwj(String userId, String cityId, String name, Integer xj, Integer wl, Integer fy, Integer sd, Integer zl) {
        Wujiang wujiang = new Wujiang();
        wujiang.setUserId(userId);
        wujiang.setCityId(cityId);
        wujiang.setName(name);
        wujiang.setXj(xj);
        wujiang.setWl(wl);
        wujiang.setFy(fy);
        wujiang.setSd(sd);
        wujiang.setZl(zl);
        wujiang.setWjId(UUID.randomUUID().toString().replaceAll("-", ""));
        mongoTemplate.save(wujiang);
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, Wujiang.class);
    }

    /**
     * 添加武将经验
     *
     * @param wjId
     * @param sl
     */
    public List<Wujiang> wjjy(String wjId, Integer sl) {
        Query query = new Query(Criteria.where("wjId").is(wjId));
        Wujiang wujiang = mongoTemplate.findOne(query, Wujiang.class);
        Integer jy = wujiang.getJy();
        jy = jy + sl * 5000;//每个卷轴5000经验
        Integer olddj = wujiang.getDj();//当前等级
        Integer sxjy = wujiang.getSjsx();//所需经验
        Integer xdj = sj(jy, olddj);//新等级
        Update update = new Update();
        update.set("dj", xdj);
        update.set("sjsx", 10000 + 200 * xdj * xdj);
        //新等级大于旧等级的时候,对武将的属性进行加点
        if (xdj > olddj) {
            int sjs = xdj - olddj;
            update.set("wl", wujiang.getWl() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getWl() + sjs);
            update.set("fy", wujiang.getFy() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getFy() + sjs);
            update.set("sd", wujiang.getSd() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getSd() + sjs);
            update.set("zl", wujiang.getZl() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getZl() + sjs);
        }
        mongoTemplate.updateFirst(query, update, Wujiang.class);
        query = new Query(Criteria.where("cityId").is(wujiang.getCityId()));
        return mongoTemplate.find(query, Wujiang.class);
    }

    /**
     * 根据经验返回等级
     *
     * @param jy
     * @param dj
     * @return
     */
    private Integer sj(Integer jy, Integer dj) {
        Integer sxjy = 10000 + 200 * dj * dj;
        while (jy > sxjy) {
            sxjy = 10000 + 200 * dj * dj;
            jy = jy - sxjy;
            dj += 1;
        }
        return dj;
    }

//    public static void main(String[] args) {
//        Integer dj = 0;//当前等级
//        Integer jy = 0;
//        Integer sxjy = 10000;//所需经验
//        Integer sl = 12;
//        jy = jy + sl * 5000;
//        while (jy > sxjy) {
//            sxjy = 10000 + 200*dj*dj;
//            jy = jy - sxjy;
//            dj += 1;
//        }
//        System.out.println(jy);
//        System.out.println(dj);
//        System.out.println(sxjy);
//        System.out.println(10000 + 106*(10-1)*(10-1));
//    }
}
