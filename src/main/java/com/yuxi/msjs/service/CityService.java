package com.yuxi.msjs.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
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
import com.yuxi.msjs.bean.conste.Sksj;
import com.yuxi.msjs.bean.conste.Tksj;
import com.yuxi.msjs.bean.conste.Tqtsj;
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
            Integer liangcl = userCity.getLiangcl();//当前产量
            Integer oldchanliang = Chanliang.getChanliang(userCity.getNongtian());//上一级的产量
            Integer newchanliang = Chanliang.getChanliang(userCity.getNongtian() + 1);//下一级的产量
            newchanliang = liangcl - oldchanliang + newchanliang;
            update.set("liangcl", newchanliang);
        } else if (jzName.equals("林场")) {
            Integer cl = userCity.getMucl();//当前产量
            Integer oldchanliang = Chanliang.getChanliang(userCity.getLinchang());//上一级的产量
            Integer newchanliang = Chanliang.getChanliang(userCity.getLinchang() + 1);//下一级的产量
            newchanliang = cl - oldchanliang + newchanliang;
            update.set("mucl", newchanliang);
        } else if (jzName.equals("石矿")) {
            Integer cl = userCity.getShicl();//当前产量
            Integer oldchanliang = Chanliang.getChanliang(userCity.getShikuang());//上一级的产量
            Integer newchanliang = Chanliang.getChanliang(userCity.getShikuang() + 1);//下一级的产量
            newchanliang = cl - oldchanliang + newchanliang;
            update.set("shicl", newchanliang);
        } else if (jzName.equals("铁矿")) {
            Integer cl = userCity.getTiecl();//当前产量
            Integer oldchanliang = Chanliang.getChanliang(userCity.getTiekuang());//上一级的产量
            Integer newchanliang = Chanliang.getChanliang(userCity.getTiekuang() + 1);//下一级的产量
            newchanliang = cl - oldchanliang + newchanliang;
            update.set("tiecl", newchanliang);
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
     * 计算耗粮
     * @param userCity
     * @return
     */
    public Integer zhl(UserCity userCity){
        Integer zhl = 0;
        zhl += userCity.getBb() * Bzzy.getHaoliang("步兵");
        zhl += userCity.getQb() * Bzzy.getHaoliang("枪兵");
        zhl += userCity.getNb() * Bzzy.getHaoliang("弩兵");
        zhl += userCity.getQq() * Bzzy.getHaoliang("轻骑");
        zhl += userCity.getHq() * Bzzy.getHaoliang("虎骑");
        zhl += userCity.getCh() * Bzzy.getHaoliang("斥候");
        zhl += userCity.getZq() * Bzzy.getHaoliang("重骑");
        zhl += userCity.getCc() * Bzzy.getHaoliang("冲车");
        zhl += userCity.getTsc() * Bzzy.getHaoliang("投石车");
        zhl += userCity.getGb() * Bzzy.getHaoliang("工兵");
        return zhl;
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
        mongoTemplate.updateFirst(query, update, Wujiang.class);
        daojuService.djsy(wujiang.getUserId(), "经验卷轴", sl);
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
