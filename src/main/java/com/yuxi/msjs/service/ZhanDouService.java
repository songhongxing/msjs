package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxi.msjs.bean.conste.Bzzy;
import com.yuxi.msjs.bean.entity.*;
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
import java.util.Set;
import java.util.UUID;

/**
 * 战斗逻辑
 *
 * @author songhongxing
 * @date 2023/04/04 3:40 下午
 */
@Service
public class ZhanDouService {

    @Autowired
    private CityService cityService;
    @Autowired
    private UserService userService;
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean chuzheng(Chuzheng chuzheng) {
        boolean bl = checkBl(chuzheng);
        if(bl){
            int sd = sd(chuzheng);
            chuzheng.setSd(sd);
            //计算距离 距离*100/部队速度
            int jl = Math.abs(chuzheng.getCzZb() - chuzheng.getGdZb());
            int xjsj = 0;//行军时间
            if(chuzheng.getCzBuff() == 1){
                xjsj = (int)(jl * 100 / (sd*1.5));
            } else {
                xjsj = jl * 100 / sd;
            }
            int ddsj = (int)(DateUtil.currentSeconds() + xjsj);
            chuzheng.setDdsj(ddsj);
            chuzheng.setCzId(UUID.randomUUID().toString().replaceAll("-", ""));
            mongoTemplate.save(chuzheng);
        }
        return bl;
    }

    /**
     * 获取出征最小速度
     *
     * @param chuzheng
     * @author songhongxing
     * @date 2023/04/04 3:54 下午
     */
    public Integer sd(Chuzheng chuzheng) {
        Integer zxsd = 120;
        if (chuzheng.getBb() != 0 && Bzzy.getSudu("步兵") < zxsd) {
            zxsd = Bzzy.getSudu("步兵");
        }
        if (chuzheng.getQb() != 0 && Bzzy.getSudu("枪兵") < zxsd) {
            zxsd = Bzzy.getSudu("枪兵");
        }
        if (chuzheng.getNb() != 0 && Bzzy.getSudu("弩兵") < zxsd) {
            zxsd = Bzzy.getSudu("弩兵");
        }
        if (chuzheng.getQq() != 0 && Bzzy.getSudu("轻骑") < zxsd) {
            zxsd = Bzzy.getSudu("轻骑");
        }
        if (chuzheng.getHq() != 0 && Bzzy.getSudu("虎骑") < zxsd) {
            zxsd = Bzzy.getSudu("虎骑");
        }
        if (chuzheng.getZq() != 0 && Bzzy.getSudu("重骑") < zxsd) {
            zxsd = Bzzy.getSudu("重骑");
        }
        if (chuzheng.getCh() != 0 && Bzzy.getSudu("斥候") < zxsd) {
            zxsd = Bzzy.getSudu("斥候");
        }
        if (chuzheng.getCc() != 0 && Bzzy.getSudu("冲车") < zxsd) {
            zxsd = Bzzy.getSudu("冲车");
        }
        if (chuzheng.getTsc() != 0 && Bzzy.getSudu("投石") < zxsd) {
            zxsd = Bzzy.getSudu("投石");
        }
        if (chuzheng.getGb() != 0 && Bzzy.getSudu("工兵") < zxsd) {
            zxsd = Bzzy.getSudu("工兵");
        }
        //速度受武将的速度影响,武将提供速度加成
        Query wjQuery = new Query(Criteria.where("wjId").is(chuzheng.getCzWjId()));
        Wujiang wujiang = mongoTemplate.findOne(wjQuery, Wujiang.class);
        zxsd = zxsd * (1 + wujiang.getSd() / 2 / 100);
        return zxsd;
    }

    /**
     * 校验兵力
     * @param chuzheng
     * @return
     */
    public boolean checkBl(Chuzheng chuzheng){
        Query cityQuery = new Query(Criteria.where("cityId").is(chuzheng.getCzCityId()));
        UserCity userCity = mongoTemplate.findOne(cityQuery, UserCity.class);
        boolean flag = true;
        if(chuzheng.getBb() != 0 && userCity.getBb() < chuzheng.getBb()){
            flag = false;
        }
        if(chuzheng.getQb() != 0 && userCity.getQb() < chuzheng.getQb()){
            flag = false;
        }
        if(chuzheng.getNb() != 0 && userCity.getNb() < chuzheng.getNb()){
            flag = false;
        }
        if(chuzheng.getQq() != 0 && userCity.getQq() < chuzheng.getQq()){
            flag = false;
        }
        if(chuzheng.getHq() != 0 && userCity.getHq() < chuzheng.getHq()){
            flag = false;
        }
        if(chuzheng.getZq() != 0 && userCity.getZq() < chuzheng.getZq()){
            flag = false;
        }
        if(chuzheng.getCh() != 0 && userCity.getCh() < chuzheng.getCh()){
            flag = false;
        }
        if(chuzheng.getCc() != 0 && userCity.getCc() < chuzheng.getCc()){
            flag = false;
        }
        if(chuzheng.getTsc() != 0 && userCity.getTsc() < chuzheng.getTsc()){
            flag = false;
        }
        if(chuzheng.getGb() != 0 && userCity.getGb() < chuzheng.getGb()){
            flag = false;
        }
        return flag;
    }


    public Map junqing(String userId) {
        Map<String, Object> hashMap = new HashMap();
        Criteria criteria = Criteria.where("gdUserId").is(userId);
        criteria.orOperator(Criteria.where("czlx").is("歼灭"),Criteria.where("czlx").is("劫掠"));
        Query dxQuery = new Query(criteria);
        Query zyQuery = new Query(Criteria.where("czlx").is("增援").and("gdUserId").is(userId));
        Query czQuery = new Query(Criteria.where("czUserId").is(userId));

        hashMap.put("敌袭", mongoTemplate.count(dxQuery, Chuzheng.class));
        hashMap.put("出征", mongoTemplate.count(czQuery, Chuzheng.class));
        hashMap.put("增援", mongoTemplate.count(zyQuery, Chuzheng.class));
        return hashMap;
    }

    public List<Chuzheng> dixi(String userId, String lx) {
        Criteria criteria =  Criteria.where("gdUserId").is(userId);
        criteria.orOperator(Criteria.where("czlx").is("歼灭"),Criteria.where("czlx").is("劫掠"));
        Query dxQuery = new Query(criteria);
        Query zyQuery = new Query(Criteria.where("czlx").is("增援").and("gdUserId").is(userId));
        Query czQuery = new Query(Criteria.where("czUserId").is(userId));
        if("敌袭".equals(lx)){
            return mongoTemplate.find(dxQuery, Chuzheng.class);
        } else if ("出征".equals(lx)){
            return mongoTemplate.find(czQuery, Chuzheng.class);
        } else if ("增援".equals(lx)){
            return mongoTemplate.find(zyQuery, Chuzheng.class);
        }
        return new ArrayList<>();
    }

    /**
     * 歼灭模式
     * @param chuzheng
     */
    public void zhandou(Chuzheng chuzheng){
        Query gdQuery = new Query(Criteria.where("cityId").is(chuzheng.getGdCityId()));
        UserCity userCity = mongoTemplate.findOne(gdQuery, UserCity.class);
        Map<String, Object> zb = new HashMap<>();
        //计算攻击方的攻击总值
        Long gjl = gjl(chuzheng);
        //计算防守方的防御总值和防守方玩家的百分比
        Long fyl = fyl(chuzheng, userCity, zb);
        //计算双方实力差距
        double slcj = Double.valueOf(String.format("%.2f", gjl / fyl));
        //计算冲车对城墙的伤害,双方实力1:1,60辆冲车可砸破城墙,
        //攻击方弱于防守方时, 实力差距 * 冲车数量 * 0.2 = 砸破城墙等级
        //0.6 * 100 * 0.2 = 12级
        int gjcq = (int) (slcj * chuzheng.getCc() * 0.2);
        //计算被破坏后的城墙等级,每一级增加1%的防御力
        int cqdj = userCity.getCq() - gjcq < 0 ? 0 : userCity.getCq() - gjcq;
        double cqjc = 1+ cqdj / 100;
        fyl = (long) (fyl * cqjc);
        //攻击方胜利
        if(gjl > fyl){
            if(slcj > 1){
                slcj = Double.valueOf(String.format("%.2f", 1 + slcj / 100));
            }
            gjl = (long) (gjl * slcj);
            //获取损失的百分比
            double sszb = Double.valueOf(String.format("%.2f", (gjl - fyl) / gjl));
        }
        //防守方胜利
        else {

        }



    }

    /**
     * 获取攻击力
     * @param chuzheng
     * @return
     */
    public Long gjl(Chuzheng chuzheng){
        //查询武将
        Query wjQuery = new Query(Criteria.where("wjId").is(chuzheng.getCzWjId()));
        Wujiang wujiang = mongoTemplate.findOne(wjQuery, Wujiang.class);
        double wljc = (wujiang.getWl() + 100) / 100;//攻击加成
        long gjl = 0;
        gjl += chuzheng.getBb() * Bzzy.getGongji("步兵");
        gjl += chuzheng.getQb() * Bzzy.getGongji("枪兵");
        gjl += chuzheng.getNb() * Bzzy.getGongji("弩兵");
        gjl += chuzheng.getQq() * Bzzy.getGongji("轻骑");
        gjl += chuzheng.getHq() * Bzzy.getGongji("虎骑");
        gjl += chuzheng.getZq() * Bzzy.getGongji("重骑");
        gjl = (long) (gjl * wljc);
        //神行出征增加20的攻击力
        if(chuzheng.getCzBuff() == 1){
            gjl *= 1.2;
        }
        return gjl;
    }

    /**
     * 防守方
     * @param chuzheng
     * @param zb
     * @return
     */
    public Long fyl(Chuzheng chuzheng,UserCity userCity, Map<String, Object> zb){
        long fyzz = 0;//防御总值
        //每个玩家的防御力
        Map<String, Long> wjgj = new HashMap<>();
        Long ccfyl = ccFyl(userCity);
        wjgj.put(userCity.getCityId(),ccfyl);
        fyzz += ccfyl;
        Query zyQuery = new Query(Criteria.where("zyCityId").is(chuzheng.getGdCityId()));
        List<ZengYuan> zengYuans = mongoTemplate.find(zyQuery, ZengYuan.class);
        if(CollUtil.isNotEmpty(zengYuans)){
            for(ZengYuan zengYuan : zengYuans){
                Long fyl = zyFyl(zengYuan);
                wjgj.put(zengYuan.getCityId(),zyFyl(zengYuan));
                fyzz += fyl;
            }
        }
        //遍历每个玩家的防御占比
        Set<String> wjcc = wjgj.keySet();
        for(String key : wjcc){
            //玩家部队防御
            Long wjbdfy = wjgj.get(key);
            zb.put(key, String.format("%.2f", wjbdfy/fyzz));
        }
        return fyzz;
    }

    /**
     * 城池防御力
     * @param userCity
     * @return
     */
    public Long ccFyl(UserCity userCity){
        //查询武将
        double wljc = 1.0;
        if(!"无".equals(userCity.getCcts())){
            Query wjQuery = new Query(Criteria.where("wjId").is(userCity.getCcts()));
            Wujiang wujiang = mongoTemplate.findOne(wjQuery, Wujiang.class);
            wljc = (wujiang.getFy() + 100) / 100;//攻击加成
        }
        long gjl = 0;
        gjl += userCity.getBb() * Bzzy.getFangyu("步兵");
        gjl += userCity.getQb() * Bzzy.getFangyu("枪兵");
        gjl += userCity.getNb() * Bzzy.getFangyu("弩兵");
        gjl += userCity.getQq() * Bzzy.getFangyu("轻骑");
        gjl += userCity.getHq() * Bzzy.getFangyu("虎骑");
        gjl += userCity.getZq() * Bzzy.getFangyu("重骑");
        gjl = (long) (gjl * wljc);
        return gjl;
    }

    /**
     * 增援防御力
     * @param zengyuan
     * @return
     */
    public Long zyFyl(ZengYuan zengyuan){
        //查询武将
        Query wjQuery = new Query(Criteria.where("wjId").is(zengyuan.getCzWj()));
        Wujiang wujiang = mongoTemplate.findOne(wjQuery, Wujiang.class);
        double wljc = (wujiang.getFy() + 100) / 100;//攻击加成
        long gjl = 0;
        gjl += zengyuan.getBb() * Bzzy.getFangyu("步兵");
        gjl += zengyuan.getQb() * Bzzy.getFangyu("枪兵");
        gjl += zengyuan.getNb() * Bzzy.getFangyu("弩兵");
        gjl += zengyuan.getQq() * Bzzy.getFangyu("轻骑");
        gjl += zengyuan.getHq() * Bzzy.getFangyu("虎骑");
        gjl += zengyuan.getZq() * Bzzy.getFangyu("重骑");
        gjl = (long) (gjl * wljc);
        return gjl;
    }



    /**
     * 建造分城
     * @param chuzheng
     */
    public void jiancheng(Chuzheng chuzheng){
        cityService.createCity(chuzheng.getCzUserId(),chuzheng.getCzUserName()+"分城", chuzheng.getCzZb());
        //发系统消息
        ZhanBao zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getCzUserId());
        zhanBao.setXxbt("分城建造完成");
        zhanBao.setXxnr("工兵在"+chuzheng.getCzZb()+"坐标处建造一座分城，请主公前往。");
        mongoTemplate.save(zhanBao);
    }

    /**
     * 侦察
     * @param chuzheng
     */
    public void zhencha(Chuzheng chuzheng){
        Query query = new Query(Criteria.where("userId").is(chuzheng.getGdUserId()));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        //发系统消息
        ZhanBao zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getCzUserId());
        zhanBao.setXxbt("侦察完成");
        zhanBao.setXxnr("这是一封侦察内容");
        mongoTemplate.save(zhanBao);
    }

    /**
     * 增援兵力
     * @param chuzheng
     */
    public void zengyuan(Chuzheng chuzheng){
        ZengYuan zengYuan = new ZengYuan();
        zengYuan.setZyId(UUID.randomUUID().toString().replaceAll("-", ""));
        zengYuan.setZyUserId(chuzheng.getCzUserId());
        zengYuan.setUserId(chuzheng.getGdUserId());
        zengYuan.setCzWj(chuzheng.getCzWjId());
        zengYuan.setCityId(chuzheng.getCzCityId());
        zengYuan.setZyCityId(chuzheng.getGdCityId());
        zengYuan.setBb(chuzheng.getBb());
        zengYuan.setQb(chuzheng.getQb());
        zengYuan.setNb(chuzheng.getNb());
        zengYuan.setQq(chuzheng.getQq());
        zengYuan.setHq(chuzheng.getHq());
        zengYuan.setZq(chuzheng.getZq());
        zengYuan.setCh(chuzheng.getCh());
        zengYuan.setCc(chuzheng.getCc());
        zengYuan.setTsc(chuzheng.getTsc());
        zengYuan.setGb(chuzheng.getGb());
        Integer zhl = zhl(zengYuan) * 2;
        Query query = new Query(Criteria.where("cityId").is(chuzheng.getGdCityId()));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        Update update = new Update();
        update.set("zhl", userCity.getZbl() + zhl);
        mongoTemplate.updateFirst(query, update, UserCity.class);
        //发系统消息
        ZhanBao zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getGdUserId());
        zhanBao.setXxbt("增援到达");
        zhanBao.setXxnr(chuzheng.getCzUserName()+"玩家增援的兵力到达,请主公前往查看。");
        mongoTemplate.save(zhanBao);
        zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getCzUserId());
        zhanBao.setXxbt("增援到达");
        zhanBao.setXxnr("增援的兵力已到达"+chuzheng.getCzUserName()+"的城池,请主公前往查看。");
        mongoTemplate.save(zhanBao);
    }


    /**
     * 计算增援耗粮
     * @param userCity
     * @return
     */
    public Integer zhl(ZengYuan zengyuan){
        Integer zhl = 0;
        zhl += zengyuan.getBb() * Bzzy.getHaoliang("步兵");
        zhl += zengyuan.getQb() * Bzzy.getHaoliang("枪兵");
        zhl += zengyuan.getNb() * Bzzy.getHaoliang("弩兵");
        zhl += zengyuan.getQq() * Bzzy.getHaoliang("轻骑");
        zhl += zengyuan.getHq() * Bzzy.getHaoliang("虎骑");
        zhl += zengyuan.getCh() * Bzzy.getHaoliang("斥候");
        zhl += zengyuan.getZq() * Bzzy.getHaoliang("重骑");
        zhl += zengyuan.getCc() * Bzzy.getHaoliang("冲车");
        zhl += zengyuan.getTsc() * Bzzy.getHaoliang("投石车");
        zhl += zengyuan.getGb() * Bzzy.getHaoliang("工兵");
        return zhl;
    }
}
