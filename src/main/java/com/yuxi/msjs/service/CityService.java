package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.mongodb.client.result.UpdateResult;
import com.yuxi.msjs.bean.conste.Acsj;
import com.yuxi.msjs.bean.conste.Ancang;
import com.yuxi.msjs.bean.conste.Bysj;
import com.yuxi.msjs.bean.conste.Bzzy;
import com.yuxi.msjs.bean.conste.Chanliang;
import com.yuxi.msjs.bean.conste.Cksj;
import com.yuxi.msjs.bean.conste.Cqsj;
import com.yuxi.msjs.bean.conste.Jgsj;
import com.yuxi.msjs.bean.conste.Jianzhu;
import com.yuxi.msjs.bean.conste.Jssj;
import com.yuxi.msjs.bean.conste.Lcsj;
import com.yuxi.msjs.bean.conste.Ntsj;
import com.yuxi.msjs.bean.conste.Nztsj;
import com.yuxi.msjs.bean.conste.Rongliang;
import com.yuxi.msjs.bean.conste.Shoujun;
import com.yuxi.msjs.bean.conste.Sksj;
import com.yuxi.msjs.bean.conste.Tksj;
import com.yuxi.msjs.bean.conste.Tqtsj;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.Lianmeng;
import com.yuxi.msjs.bean.entity.Meinv;
import com.yuxi.msjs.bean.entity.SlgMap;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.UserDaoju;
import com.yuxi.msjs.bean.entity.Wujiang;
import com.yuxi.msjs.bean.entity.ZhengBing;
import com.yuxi.msjs.bean.vo.CityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        userCity.setCityName("新的城市");
        mongoTemplate.save(userCity);
        //修改地图类型为玩家城池
        Query query = new Query(Criteria.where("_id").is(zuobiao));
        Update update = new Update();
        update.set("dklx", "玩家城池");
        update.set("dkmc", userName);
        update.set("sswjId", userId);
        update.set("sswjName", userName);
        //新手保护期3天
        update.set("mzbz", 2);
        update.set("mzdq", (int) (DateUtil.currentSeconds() + 3*24* 3600));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update,"slg_map");
        assert updateResult.wasAcknowledged();

        return userCity;
    }

    /**
     * 用户城市列表
     * @param userId
     * @author songhongxing
     * @date 2023/02/27 10:42 上午
     */
    public List<UserCity> userCitys(String userId){
        List<CityList> cityList = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return  mongoTemplate.find(query, UserCity.class);
    }

    /**
     * 用户城市信息
     *
     * @param cityId
     * @return
     */
    public UserCity userCity(String cityId) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        //用户的兵力减去出征的兵力
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
//        query = new Query(Criteria.where("czCityId").is(cityId));
//        List<Chuzheng> chuzhengs = mongoTemplate.find(query, Chuzheng.class);
//        if (CollUtil.isNotEmpty(chuzhengs)) {
//            for (Chuzheng chuzheng : chuzhengs) {
//                userCity.setBb(userCity.getBb() - chuzheng.getBb());
//                userCity.setQb(userCity.getQb() - chuzheng.getQb());
//                userCity.setNb(userCity.getNb() - chuzheng.getNb());
//                userCity.setQq(userCity.getQq() - chuzheng.getQq());
//                userCity.setHq(userCity.getHq() - chuzheng.getHq());
//                userCity.setZq(userCity.getZq() - chuzheng.getZq());
//                userCity.setCc(userCity.getCc() - chuzheng.getCc());
//                userCity.setCh(userCity.getCh() - chuzheng.getCh());
//                userCity.setGb(userCity.getGb() - chuzheng.getGb());
//                userCity.setTsc(userCity.getTsc() - chuzheng.getTsc());
//
//            }
//        }


        return userCity;
    }

    /**
     * 添加建筑升级
     *
     * @param cityId 城市id
     * @param jzName 建筑名称
     * @author songhongxing
     * @date 2023/02/28 4:55 下午
     */
    public List<HomeUp> jzshengji(String cityId, String jzName) {
        //根据升级建筑和等级扣减资源
        Query query = new Query(Criteria.where("cityId").is(cityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Map<String, Object> sjzy = getSjzy(jzName, userCity);
        boolean flag = false;//判断城内资源是否足够升级
        int mu = MapUtil.getInt(sjzy, "mu");
        int shi = MapUtil.getInt(sjzy, "shi");
        int tie = MapUtil.getInt(sjzy, "tie");
        int liang = MapUtil.getInt(sjzy, "liang");
        int sj = MapUtil.getInt(sjzy, "sj");
        int dj = MapUtil.getInt(sjzy, "dj");
        if (mu > userCity.getMucc()) {
            flag = true;
        }
        if (shi > userCity.getShicc()) {
            flag = true;
        }
        if (tie > userCity.getTiecc()) {
            flag = true;
        }
        if (liang > userCity.getLiangcc()) {
            flag = true;
        }
        //资源足够的情况下才能创建队列
        if (flag == false) {
            HomeUp homeUp = new HomeUp();
            homeUp.setCityId(cityId);
            homeUp.setJzName(jzName);
            homeUp.setJzdj(dj);
            int dqsj = (int) DateUtil.currentSeconds() + sj;
            homeUp.setDqsj(dqsj);
            mongoTemplate.save(homeUp);
            //扣减城内资源
            Update update = new Update();
            update.set("mucc", userCity.getMucc() - mu);
            update.set("shicc", userCity.getShicc() - shi);
            update.set("tiecc", userCity.getTiecc() - tie);
            update.set("liangcc", userCity.getLiangcc() - liang);
            mongoTemplate.updateFirst(query, update, UserCity.class);
        }

        return mongoTemplate.find(query, HomeUp.class);
    }

    private Map<String, Object> getSjzy(String jzName, UserCity userCity) {
        Map<String, Object> result = new HashMap<>();
        int jzDj = 0;
        if (jzName.equals("内政厅")) {
            jzDj = userCity.getNzt() + 1;
            result.put("mu", Nztsj.getMuz(jzDj));
            result.put("shi", Nztsj.getShiz(jzDj));
            result.put("tie", Nztsj.getTiez(jzDj));
            result.put("liang", Nztsj.getLiangz(jzDj));
            result.put("sj", Nztsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("铜雀台")) {
            jzDj = userCity.getTqt() + 1;
            result.put("mu", Tqtsj.getMuz(jzDj));
            result.put("shi", Tqtsj.getShiz(jzDj));
            result.put("tie", Tqtsj.getTiez(jzDj));
            result.put("liang", Tqtsj.getLiangz(jzDj));
            result.put("sj", Tqtsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("兵营")) {
            jzDj = userCity.getBy() + 1;
            result.put("mu", Bysj.getMuz(jzDj));
            result.put("shi", Bysj.getShiz(jzDj));
            result.put("tie", Bysj.getTiez(jzDj));
            result.put("liang", Bysj.getLiangz(jzDj));
            result.put("sj", Bysj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("酒馆")) {
            jzDj = userCity.getJg() + 1;
            result.put("mu", Jgsj.getMuz(jzDj));
            result.put("shi", Jgsj.getShiz(jzDj));
            result.put("tie", Jgsj.getTiez(jzDj));
            result.put("liang", Jgsj.getLiangz(jzDj));
            result.put("sj", Jgsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("集市")) {
            jzDj = userCity.getJs() + 1;
            result.put("mu", Jssj.getMuz(jzDj));
            result.put("shi", Jssj.getShiz(jzDj));
            result.put("tie", Jssj.getTiez(jzDj));
            result.put("liang", Jssj.getLiangz(jzDj));
            result.put("sj", Jssj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("仓库")) {
            jzDj = userCity.getCk() + 1;
            result.put("mu", Cksj.getMuz(jzDj));
            result.put("shi", Cksj.getShiz(jzDj));
            result.put("tie", Cksj.getTiez(jzDj));
            result.put("liang", Cksj.getLiangz(jzDj));
            result.put("sj", Cksj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("粮仓")) {
            jzDj = userCity.getLc() + 1;
            result.put("mu", Lcsj.getMuz(jzDj));
            result.put("shi", Lcsj.getShiz(jzDj));
            result.put("tie", Lcsj.getTiez(jzDj));
            result.put("liang", Lcsj.getLiangz(jzDj));
            result.put("sj", Lcsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("暗仓")) {
            jzDj = userCity.getAc() + 1;
            result.put("mu", Acsj.getMuz(jzDj));
            result.put("shi", Acsj.getShiz(jzDj));
            result.put("tie", Acsj.getTiez(jzDj));
            result.put("liang", Acsj.getLiangz(jzDj));
            result.put("sj", Acsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("城墙")) {
            jzDj = userCity.getCq() + 1;
            result.put("mu", Cqsj.getMuz(jzDj));
            result.put("shi", Cqsj.getShiz(jzDj));
            result.put("tie", Cqsj.getTiez(jzDj));
            result.put("liang", Cqsj.getLiangz(jzDj));
            result.put("sj", Cqsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("农田")) {
            jzDj = userCity.getNongtian() + 1;
            result.put("mu", Ntsj.getMuz(jzDj));
            result.put("shi", Ntsj.getShiz(jzDj));
            result.put("tie", Ntsj.getTiez(jzDj));
            result.put("liang", Ntsj.getLiangz(jzDj));
            result.put("sj", Ntsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("林场")) {
            jzDj = userCity.getLinchang() + 1;
            result.put("mu", Lcsj.getMuz(jzDj));
            result.put("shi", Lcsj.getShiz(jzDj));
            result.put("tie", Lcsj.getTiez(jzDj));
            result.put("liang", Lcsj.getLiangz(jzDj));
            result.put("sj", Lcsj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("铁矿")) {
            jzDj = userCity.getTiekuang() + 1;
            result.put("mu", Tksj.getMuz(jzDj));
            result.put("shi", Tksj.getShiz(jzDj));
            result.put("tie", Tksj.getTiez(jzDj));
            result.put("liang", Tksj.getLiangz(jzDj));
            result.put("sj", Tksj.getShijian(jzDj));
            result.put("dj", jzDj);
        } else if (jzName.equals("石矿")) {
            jzDj = userCity.getShikuang() + 1;
            result.put("mu", Sksj.getMuz(jzDj));
            result.put("shi", Sksj.getShiz(jzDj));
            result.put("tie", Sksj.getTiez(jzDj));
            result.put("liang", Sksj.getLiangz(jzDj));
            result.put("sj", Sksj.getShijian(jzDj));
            result.put("dj", jzDj);
        }
        return result;
    }

    /**
     * 建筑升级完成
     *
     * @param cityId
     * @param jzName
     * @author songhongxing
     * @date 2023/02/28 5:02 下午
     */
    public List<HomeUp> sjwc(String cityId, String jzName) {
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
        update.set("zgm", userCity.getZgm() + 1);
        //如果是资源田的升级,需要更新产量
        if (jzName.equals("农田")) {
            Integer newchanliang = Chanliang.getChanliang(userCity.getNongtian() + 1);//下一级的产量
            Integer jccl = (int)(newchanliang * userCity.getLiangjc());
            update.set("liangjccl", jccl);
        } else if (jzName.equals("林场")) {
            Integer newchanliang = Chanliang.getChanliang(userCity.getLinchang() + 1);//下一级的产量
            Integer jccl = (int)(newchanliang * userCity.getMujc());
            update.set("liangjccl", jccl);
        } else if (jzName.equals("石矿")) {
            Integer newchanliang = Chanliang.getChanliang(userCity.getShikuang() + 1);//下一级的产量
            Integer jccl = (int)(newchanliang * userCity.getShijc());
            update.set("liangjccl", jccl);
        } else if (jzName.equals("铁矿")) {
            Integer newchanliang = Chanliang.getChanliang(userCity.getTiekuang() + 1);//下一级的产量
            Integer jccl = (int)(newchanliang * userCity.getTiejc());
            update.set("liangjccl", jccl);
        } else if (jzName.equals("仓库")) {
            Integer rl = Rongliang.getRongliang(userCity.getCk() + 1);
            update.set("ckcc", rl);
        } else if (jzName.equals("粮仓")) {
            Integer rl = Rongliang.getRongliang(userCity.getLc() + 1);
            update.set("lccc", rl);
        } else if (jzName.equals("暗仓")) {
            Integer rl = Ancang.getRongliang(userCity.getAc() + 1);
            update.set("acrl", rl);
        }
        mongoTemplate.updateFirst(query, update, UserCity.class);
        return mongoTemplate.find(query, HomeUp.class);
    }

    /**
     * 获取各资源产量
     * @param cityId
     * @param lx  资源类型
     * @return
     */
    public Integer lscl(String cityId, String lmId, String lx){
        Query query =  new Query(Criteria.where("cityId").is(cityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Integer chanliang=0;
        if("农田".equals(lx)){
            //农田产量
            chanliang = Chanliang.getChanliang(userCity.getNongtian());
            //美女加成
            chanliang += userCity.getLiangjccl();
        } else if("铁矿".equals(lx)){
            chanliang = Chanliang.getChanliang(userCity.getTiekuang());
            chanliang += userCity.getTiejccl();
        } else if("林场".equals(lx)){
            chanliang = Chanliang.getChanliang(userCity.getLinchang());
            chanliang += userCity.getMujccl();
        } else if("石矿".equals(lx)){
            chanliang = Chanliang.getChanliang(userCity.getShikuang());
            chanliang += userCity.getShijccl();
        }
        //联盟加成
        if(!"无".equals(lmId)){
            query = new Query(Criteria.where("lmId").is(lmId));
            Lianmeng lianmeng = mongoTemplate.findOne(query, Lianmeng.class);
            chanliang = (int)(chanliang * (lianmeng.getZyscdj() * 0.02+1));
        }
        query =  new Query(Criteria.where("cityId").is(cityId).and("dklx").is(lx));
        List<SlgMap> slgMaps = mongoTemplate.find(query, SlgMap.class);
        //占领的农田产量
        if(CollUtil.isNotEmpty(slgMaps)){
            for(SlgMap slgMap : slgMaps){
                chanliang += Shoujun.getZjclz(slgMap.getDkdj());
            }
        }
        return  chanliang;
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
     *
     * @param cityId
     * @param bz
     * @param sl
     * @param dghs
     * @author songhongxing
     * @date 2023/03/02 3:53 下午
     */
    public List<ZhengBing> zhengbing(String cityId, String bz, Integer sl, Double dghs) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        //扣除城市资源
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        int mu = Bzzy.getMuz(bz) * sl;
        int shi = Bzzy.getShiz(bz) * sl;
        int tie = Bzzy.getTiez(bz) * sl;
        int liang = Bzzy.getLiangz(bz) * sl;
        if (userCity.getMucc() >= mu && userCity.getShicc() >= shi
                && userCity.getTiecc() >= tie && userCity.getLiangcc() >= liang) {
            ZhengBing zhengBing = new ZhengBing();
            zhengBing.setCityId(cityId);
            zhengBing.setBz(bz);
            zhengBing.setSl(sl);
            zhengBing.setYzm(0);
            //计算征兵加成
            Query userQuery = new Query(Criteria.where("userId").is(userCity.getUserId()));
            User user = mongoTemplate.findOne(userQuery, User.class);
            if (!"无".equals(user.getLmId())) {
                userQuery = new Query(Criteria.where("lmId").is(user.getLmId()));
                Lianmeng lianmeng = mongoTemplate.findOne(userQuery, Lianmeng.class);
                dghs = (Bzzy.getShijian(bz) * (1 - userCity.getZbjc() - lianmeng.getZbsddj() * 0.02));
            } else {
                dghs = (Bzzy.getShijian(bz) * (1 - userCity.getZbjc()));
            }
            zhengBing.setDghs(dghs);
            zhengBing.setZhs((int) (dghs * sl));
            zhengBing.setKssj((int) DateUtil.currentSeconds());
            zhengBing.setJssj(zhengBing.getKssj() + zhengBing.getZhs());
            mongoTemplate.save(zhengBing);
            Update update = new Update();
            update.set("mucc", userCity.getMucc() - Bzzy.getMuz(bz) * sl);
            update.set("shicc", userCity.getShicc() - Bzzy.getShiz(bz) * sl);
            update.set("tiecc", userCity.getTiecc() - Bzzy.getTiez(bz) * sl);
            update.set("liangcc", userCity.getLiangcc() - Bzzy.getLiangz(bz) * sl);
            mongoTemplate.updateFirst(query, update, "user_city");
        }
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

    /**
     * 添加武将
     * @param userId
     * @param cityId
     * @param name
     * @param wl
     * @param fy
     * @param sd
     * @param zl
     * @return
     */
    public List<Wujiang> xzwj(String userId, String userName, String cityId, String name, Integer wl, Integer fy, Integer sd, Integer zl){
        Wujiang wujiang = new Wujiang();
        wujiang.setUserId(userId);
        wujiang.setCityId(cityId);
        wujiang.setName(name);
        wujiang.setWl(wl);
        wujiang.setFy(fy);
        wujiang.setZl(zl);
        wujiang.setSd(sd);
        wujiang.setUserName(userName);
        mongoTemplate.save(wujiang);
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return  mongoTemplate.find(query, Wujiang.class);
    }

    public List<Wujiang> wjlb(String cityId, Integer czzt){
        Query query = new Query(Criteria.where("cityId").is(cityId));
        if(czzt == 1){
            query.addCriteria(Criteria.where("czzt").is(0).and("lwzt").is(0));
            query.with(Sort.by(Sort.Order.desc("wl")));
        }
        return  mongoTemplate.find(query, Wujiang.class);
    }

    /**
     * 新增美女
     * @return
     */
    public List<Meinv> xzmv(String userId,String userName, String cityId, String name, Integer ly, Integer sc, Integer zl, Integer cy, Integer ml, Integer dj) {
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
        meinv.setUserName(userName);
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
        Query cityQuery = new Query(Criteria.where("cityId").is(cityId));
        UserCity city = mongoTemplate.findOne(cityQuery, UserCity.class);
        mongoTemplate.updateFirst(query, update, Meinv.class);
        Update cityUpdate = new Update();
        if (meinv.getGz().equals("林务官")) {
            city.setMujc(city.getMujc() + sxd / 100D);
            city.setMujccl((int) (city.getMucl() * city.getMujc()));
            city.setMucl(city.getMucl() + city.getMujccl());
            cityUpdate.set("mujc", city.getMujc());
            cityUpdate.set("mujccl", city.getMujccl());
            mongoTemplate.updateFirst(cityQuery, cityUpdate, UserCity.class);
        } else if (meinv.getGz().equals("石务官")) {
            city.setShijc(city.getShijc() + sxd / 100D);
            city.setShijccl((int) (city.getShicl() * city.getShijc()));
            city.setShicl(city.getShicl() + city.getShijccl());
            cityUpdate.set("shijc", city.getShijc());
            cityUpdate.set("shijccl", city.getShijccl());
            mongoTemplate.updateFirst(cityQuery, cityUpdate, UserCity.class);
        } else if (meinv.getGz().equals("铁务官")) {
            city.setTiejc(city.getTiejc() + sxd / 100D);
            city.setTiejccl((int) (city.getTiecl() * city.getTiejc()));
            city.setTiecl(city.getTiecl() + city.getTiejccl());
            cityUpdate.set("tiejc", city.getTiejc());
            cityUpdate.set("tiejccl", city.getTiejccl());
            mongoTemplate.updateFirst(cityQuery, cityUpdate, UserCity.class);
        } else if (meinv.getGz().equals("粮务官")) {
            city.setLiangjc(city.getLiangjc() + sxd / 100D);
            city.setLiangjccl((int) (city.getLiangcl() * city.getLiangjc()));
            city.setLiangcl(city.getLiangcl() + city.getLiangjccl());
            cityUpdate.set("liangjc", city.getLiangjc());
            cityUpdate.set("liangjccl", city.getLiangjccl());
            mongoTemplate.updateFirst(cityQuery, cityUpdate, UserCity.class);
        } else if (meinv.getGz().equals("军务官")) {
            city.setZbjc(city.getZbjc() + sxd/4/ 100D);
            cityUpdate.set("zbjc", city.getZbjc());
            mongoTemplate.updateFirst(cityQuery, cityUpdate, UserCity.class);
        }

        //扣减道具数量
        daojuService.djsy(userId, "玉女心经", sxd);
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
        Integer xdj = olddj;//新等级
        Integer sxjy = wujiang.getSjsx();
        while (jy >= sxjy && xdj<50) {
            sxjy = 10000 + 200 * olddj * olddj;
            jy = jy - sxjy;
            xdj += 1;
        }
        Update update = new Update();
        update.set("dj", xdj);
        update.set("jy", jy);
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
        daojuService.djsy(wujiang.getUserId(), "经验卷轴", sl);
        query = new Query(Criteria.where("cityId").is(wujiang.getCityId()));
        return mongoTemplate.find(query, Wujiang.class);
    }


    /**
     * 战斗后增加武将经验
     *
     * @param wjId
     * @param jyxx
     */
    public void zjwjjy(String wjId, String jyxx) {
        int zjjy =  Integer.valueOf(jyxx.split(",")[0]);
        int lwzt = Integer.valueOf(jyxx.split(",")[1]);
        Query query = new Query(Criteria.where("wjId").is(wjId));
        Wujiang wujiang = mongoTemplate.findOne(query, Wujiang.class);
        Integer jy = wujiang.getJy();
        jy = jy + zjjy;
        Integer olddj = wujiang.getDj();//当前等级
        Integer xdj = olddj;//新等级
        Integer sxjy = wujiang.getSjsx();
        while (jy >= sxjy) {
            sxjy = 10000 + 200 * olddj * olddj;
            jy = jy - sxjy;
            xdj += 1;
        }
        Update update = new Update();
        update.set("dj", xdj);
        update.set("jy", jy);
        update.set("sjsx", 10000 + 200 * xdj * xdj);
        //新等级大于旧等级的时候,对武将的属性进行加点
        if (xdj > olddj) {
            int sjs = xdj - olddj;
            update.set("wl", wujiang.getWl() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getWl() + sjs);
            update.set("fy", wujiang.getFy() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getFy() + sjs);
            update.set("sd", wujiang.getSd() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getSd() + sjs);
            update.set("zl", wujiang.getZl() + sjs > 60 + wujiang.getXj() * 10 ? 60 + wujiang.getXj() * 10 : wujiang.getZl() + sjs);
        }
        if(lwzt == 1){
            update.set("lwzt", 1);
        }
        mongoTemplate.updateFirst(query, update, Wujiang.class);
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

    public List<Wujiang> wjfz(String cityId, String wjId) {
        Query query = new Query(Criteria.where("wjId").is(wjId));
        mongoTemplate.remove(query, Wujiang.class);
        query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, Wujiang.class);
    }

    /**
     * 资源转换
     *
     * @param cityId
     * @param lyzy
     * @param lysl
     * @param mbzy
     * @param mbsl
     * @author songhongxing
     * @date 2023/03/20 3:12 下午
     */
    public UserCity zyzh(String cityId, String lyzy, Integer lysl, String mbzy, Integer mbsl) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Update update = new Update();
        if ("木".equals(lyzy) && lysl < userCity.getMucc()) {
            update.set("mucc", userCity.getMucc() - lysl);
            setZy(update, userCity, mbzy, mbsl);
        } else if ("石".equals(lyzy) && lysl < userCity.getShicc()) {
            update.set("shicc", userCity.getShicc() - lysl);
            setZy(update, userCity, mbzy, mbsl);
        } else if ("铁".equals(lyzy) && lysl < userCity.getTiecc()) {
            update.set("tiecc", userCity.getTiecc() - lysl);
            setZy(update, userCity, mbzy, mbsl);
        } else if ("粮".equals(lyzy) && lysl < userCity.getLiangcc()) {
            update.set("liangcc", userCity.getLiangcc() - lysl);
            setZy(update, userCity, mbzy, mbsl);
        }
        mongoTemplate.updateFirst(query, update, UserCity.class);
        return mongoTemplate.findOne(query, UserCity.class);
    }

    /**
     * 分城运输资源
     *
     * @param lycityId
     * @param lyzy
     * @param lysl
     * @param mbcityId
     * @param mbzy
     * @param mbsl
     * @author songhongxing
     * @date 2023/03/20 4:16 下午
     */
    public UserCity zyys(String lycityId, String lyzy, Integer lysl, String mbcityId, String mbzy, Integer mbsl) {
        Query query = new Query(Criteria.where("cityId").is(lycityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Query mbQuery = new Query(Criteria.where("cityId").is(mbcityId));
        Update lyupdate = new Update();
        Update mbudate = new Update();
        UserCity mbCity = new UserCity();
        if ("木".equals(lyzy) && lysl < userCity.getMucc()) {
            lyupdate.set("mucc", userCity.getMucc() - lysl);
            mbCity = mongoTemplate.findOne(mbQuery, UserCity.class);
        } else if ("石".equals(lyzy) && lysl < userCity.getShicc()) {
            lyupdate.set("shicc", userCity.getShicc() - lysl);
            mbCity = mongoTemplate.findOne(mbQuery, UserCity.class);
        } else if ("铁".equals(lyzy) && lysl < userCity.getTiecc()) {
            lyupdate.set("tiecc", userCity.getTiecc() - lysl);
            mbCity = mongoTemplate.findOne(mbQuery, UserCity.class);
        } else if ("粮".equals(lyzy) && lysl < userCity.getLiangcc()) {
            lyupdate.set("liangcc", userCity.getLiangcc() - lysl);
            mbCity = mongoTemplate.findOne(mbQuery, UserCity.class);
        }
        mongoTemplate.updateFirst(query, lyupdate, UserCity.class);
        mbudate = getUpdate(mbCity, mbzy, mbsl);
        mongoTemplate.updateFirst(mbQuery, mbudate, UserCity.class);
        return mongoTemplate.findOne(query, UserCity.class);

    }

    private void setZy(Update update, UserCity userCity, String mbzy, Integer mbsl) {
        if ("木".equals(mbzy)) {
            update.set("mucc", userCity.getMucc() + mbsl);
        } else if ("石".equals(mbzy)) {
            update.set("shicc", userCity.getShicc() + mbsl);
        } else if ("铁".equals(mbzy)) {
            update.set("tiecc", userCity.getTiecc() + mbsl);
        } else if ("粮".equals(mbzy)) {
            update.set("liangcc", userCity.getLiangcc() + mbsl);
        }
    }

    private Update getUpdate(UserCity userCity, String mbzy, Integer mbsl) {
        Update update = new Update();
        if ("木".equals(mbzy)) {
            update.set("mucc", userCity.getMucc() + mbsl);
        } else if ("石".equals(mbzy)) {
            update.set("shicc", userCity.getShicc() + mbsl);
        } else if ("铁".equals(mbzy)) {
            update.set("tiecc", userCity.getTiecc() + mbsl);
        } else if ("粮".equals(mbzy)) {
            update.set("liangcc", userCity.getLiangcc() + mbsl);
        }
        return update;
    }

    /**
     * 任命官职
     *
     * @param cityId
     * @param name
     * @param gz
     * @return
     */
    public List<Meinv> rmmv(String cityId, String name, String gz) {
        Query query = new Query(Criteria.where("cityId").is(cityId).and("name").is(name));
        Meinv meinv = mongoTemplate.findOne(query, Meinv.class);
        Query cityQuery = new Query(Criteria.where("cityId").is(cityId));
        UserCity city = mongoTemplate.findOne(cityQuery, UserCity.class);
        Update update = new Update();
        update.set("gz", gz);
        mongoTemplate.updateFirst(query, update, Meinv.class);
        Update cityUpdate = new Update();
        if (gz.equals("林务官")) {
            city.setMujc(city.getMujc() + meinv.getLy() / 100D);
            city.setMujccl((int) (city.getMucl() * city.getMujc()));
            cityUpdate.set("mujc", city.getMujc());
            cityUpdate.set("mujccl", city.getMujccl());
        } else if (gz.equals("石务官")) {
            city.setShijc(city.getShijc() + meinv.getSc() / 100D);
            city.setShijccl((int) (city.getShicl() * city.getShijc()));
            city.setShicl(city.getShicl() + city.getShijccl());
            cityUpdate.set("shijc", city.getShijc());
            cityUpdate.set("shijccl", city.getShijccl());
        } else if (gz.equals("铁务官")) {
            city.setTiejc(city.getTiejc() + meinv.getZl() / 100D);
            city.setTiejccl((int) (city.getTiecl() * city.getTiejc()));
            city.setTiecl(city.getTiecl() + city.getTiejccl());
            cityUpdate.set("tiejc", city.getTiejc());
            cityUpdate.set("tiejccl", city.getTiejccl());
        } else if (gz.equals("粮务官")) {
            city.setLiangjc(city.getLiangjc() + meinv.getCy() / 100D);
            city.setLiangjccl((int) (city.getLiangcl() * city.getLiangjc()));
            city.setLiangcl(city.getLiangcl() + city.getLiangjccl());
            cityUpdate.set("liangjc", city.getLiangjc());
            cityUpdate.set("liangjccl", city.getLiangjccl());
        } else if (gz.equals("军务官")) {
            city.setZbjc(city.getZbjc() + meinv.getMl() / 100D);
            cityUpdate.set("zbjc", city.getZbjc());
        }
        mongoTemplate.updateFirst(cityQuery, cityUpdate, UserCity.class);
        query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, Meinv.class);
    }

    /**
     * 解除美女
     *
     * @param cityId
     * @param name
     * @return
     */
    public List<Meinv> jiechumv(String cityId, String name) {
        Query query = new Query(Criteria.where("cityId").is(cityId).and("name").is(name));
        Meinv meinv = mongoTemplate.findOne(query, Meinv.class);
        Query cityQuery = new Query(Criteria.where("cityId").is(cityId));
        UserCity city = mongoTemplate.findOne(cityQuery, UserCity.class);
        Update update = new Update();
        update.set("gz", "");
        mongoTemplate.updateFirst(query, update, Meinv.class);
        Update cityUpdate = new Update();
        if (meinv.getGz().equals("林务官")) {
            city.setMujc(city.getMujc() - meinv.getLy() / 100D);
            city.setMujccl((int) (city.getMucl() * city.getMujc()));
            city.setMucl(city.getMucl() - city.getMujccl());
            cityUpdate.set("mujc", city.getMujc());
            cityUpdate.set("mujccl", city.getMujccl());
        } else if (meinv.getGz().equals("石务官")) {
            city.setShijc(city.getShijc() - meinv.getSc() / 100D);
            city.setShijccl((int) (city.getShicl() * city.getShijc()));
            city.setShicl(city.getShicl() + city.getShijccl());
            cityUpdate.set("shijc", city.getShijc());
            cityUpdate.set("shijccl", city.getShijccl());
        } else if (meinv.getGz().equals("铁务官")) {
            city.setTiejc(city.getTiejc() - meinv.getZl() / 100D);
            city.setTiejccl((int) (city.getTiecl() * city.getTiejc()));
            cityUpdate.set("tiejc", city.getTiejc());
            cityUpdate.set("tiejccl", city.getTiejccl());
        } else if (meinv.getGz().equals("粮务官")) {
            city.setLiangjc(city.getLiangjc() - meinv.getCy() / 100D);
            city.setLiangjccl((int) (city.getLiangcl() * city.getLiangjc()));
            cityUpdate.set("liangjc", city.getLiangjc());
            cityUpdate.set("liangjccl", city.getLiangjccl());
            cityUpdate.set("liangcl", city.getLiangcl());
        } else if (meinv.getGz().equals("军务官")) {
            city.setZbjc(city.getZbjc() - meinv.getMl() / 100D);
            cityUpdate.set("zbjc", city.getZbjc());
        }
        mongoTemplate.updateFirst(cityQuery, cityUpdate, UserCity.class);
        query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, Meinv.class);
    }

    /**
     * 计算资源田加成
     *
     * @param cityId
     * @param zylx   资源类型
     * @param cljc   产量加成
     */
    public void zytjc(String cityId, String zylx, Integer cljc) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Update update = new Update();
        if("农田".equals(zylx)){
            update.set("liangjccl", userCity.getLiangjccl() + cljc);
        } else if("林场".equals(zylx)){
            update.set("mujccl", userCity.getMujccl() + cljc);
        } else if("石矿".equals(zylx)){
            update.set("shijccl", userCity.getShijccl() + cljc);
        } else if("铁矿".equals(zylx)){
            update.set("tiejccl", userCity.getTiekuang() + cljc);
        }
        mongoTemplate.updateFirst(query, update, UserCity.class);
    }

    /**
     * 查询城市所属的资源田
     * @param cityId
     * @return
     */
    public List<SlgMap> cityZytes(String cityId){
        Query query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, SlgMap.class);
    }

    /**
     * 放弃资源田
     * @param cityId
     * @param zb
     * @return
     */
    public List<SlgMap> fqzyt(String cityId, Integer zb) {
        Query query = new Query(Criteria.where("id").is(zb));
        Update update = new Update();
        update.set("sswjId", "");
        update.set("sswjName", "");
        update.set("lmId", "");
        update.set("lmmc", "无");
        update.set("cityId", "");
        mongoTemplate.updateFirst(query, update, SlgMap.class);
        return cityZytes(cityId);
    }

    public UserCity xgcsm(String cityId, String name) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        Update update = new Update();
        update.set("cityName", name);
        mongoTemplate.updateFirst(query, update, UserCity.class);
        return mongoTemplate.findOne(query, UserCity.class);
    }

    public Wujiang xgwjm(String wjId, String name){
        Query query = new Query(Criteria.where("wjId").is(wjId));
        Update update = new Update();
        update.set("name", name);
        mongoTemplate.updateFirst(query, update, Wujiang.class);
        return mongoTemplate.findOne(query, Wujiang.class);
    }

    public Meinv xgmvm(String mvId, String name){
        Query query = new Query(Criteria.where("mvId").is(mvId));
        Update update = new Update();
        update.set("name", name);
        mongoTemplate.updateFirst(query, update, Meinv.class);
        return mongoTemplate.findOne(query, Meinv.class);
    }

    public void gaiming(String cityId, String name) {
        Query query = new Query(Criteria.where("cityId").is(cityId));
        Update update = new Update();
        update.set("cityName", name);
        mongoTemplate.updateFirst(query, update, UserCity.class);
        //修改地图上的名称
        query = new Query(Criteria.where("cityId").is(cityId));
        update = new Update();
        update.set("dkmc", name);
        mongoTemplate.updateFirst(query, update, SlgMap.class);
    }

    public List<ZhengBing> tzzb(String cityId, String bz) {
        Query query = new Query(Criteria.where("cityId").is(cityId).and("bz").is(bz));
        mongoTemplate.remove(query, ZhengBing.class);
        query = new Query(Criteria.where("cityId").is(cityId));
        return mongoTemplate.find(query, ZhengBing.class);
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
