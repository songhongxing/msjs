package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxi.msjs.bean.conste.Ancang;
import com.yuxi.msjs.bean.conste.Bzzy;
import com.yuxi.msjs.bean.conste.Chanliang;
import com.yuxi.msjs.bean.conste.Rongliang;
import com.yuxi.msjs.bean.entity.Chuzheng;
import com.yuxi.msjs.bean.entity.SlgMap;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.Wujiang;
import com.yuxi.msjs.bean.entity.ZengYuan;
import com.yuxi.msjs.bean.entity.ZhanBao;
import com.yuxi.msjs.util.GameUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    //增援列表
    private Map<String, ZengYuan> zengyuans = new HashMap<>();
    //增援方的每条增援记录占比
    private Map<String, Double> zyzb = new HashMap<>();
    //增援兵力总数
    private Map<String, Integer> zyblzs = new HashMap<>();
    //增援兵力剩余总数
    private Map<String, Integer> zyblsyzs = new HashMap<>();
    //武将经验<武将id, "经验,是否流亡">
    private Map<String, String> wjjy = new HashMap<>();
    //玩家军功俘虏
    private Map<String, String> wjjgfl = new HashMap<>();
    //防守城池的防御力占比
    private double fszb = 0;
    //防守城池的总兵力
    private Integer fszbl = 0;
    //参战玩家列表
    Set<String> czwj = new HashSet<>();


    public boolean chuzheng(Chuzheng chuzheng) {
        boolean bl = checkBl(chuzheng);
        if (bl) {
            int sd = sd(chuzheng);
            chuzheng.setSd(sd);
            //计算距离 距离*100/部队速度
            int jl = Math.abs(chuzheng.getCzZb() - chuzheng.getGdZb());
            int xjsj = 0;//行军时间
            if (chuzheng.getCzBuff() == 1) {
                xjsj = (int) (jl * 100 / (sd * 1.5));
            } else {
                xjsj = jl * 100 / sd;
            }
            int ddsj = (int) (DateUtil.currentSeconds() + xjsj);
            chuzheng.setDdsj(ddsj);
            chuzheng.setXjsj(xjsj);
            chuzheng.setCzId(UUID.randomUUID().toString().replaceAll("-", ""));
            mongoTemplate.save(chuzheng);
        }
        //更改武将的出征状态
        Query query = new Query(Criteria.where("wjId").is(chuzheng.getCzWjId()));
        Update update = new Update();
        update.set("czzt", 1);
        mongoTemplate.updateFirst(query, update, Wujiang.class);
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
     *
     * @param chuzheng
     */
    public void jianmie(Chuzheng chuzheng) {
        if (StrUtil.isEmpty(chuzheng.getGdUserId())) {
            //是npc,就查询这个坐标的等级,胜利后占领土地
            jianlieNPC(chuzheng);
        } else {
            jianmieWJ(chuzheng);
        }
    }

    /**
     * 劫掠模式
     *
     * @param chuzheng
     */
    public void jielue(Chuzheng chuzheng) {

        if (StrUtil.isEmpty(chuzheng.getGdUserId())) {
            //是npc,就查询这个坐标的等级,胜利后占领土地
            jianlieNPC(chuzheng);
        } else {
            jianmieWJ(chuzheng);
        }
    }

    public void jianlieNPC(Chuzheng chuzheng) {
        zengyuans.clear();
        zyzb.clear();
        zyblzs.clear();
        czwj.clear();
        wjjy.clear();
        wjjgfl.clear();
        Query query = new Query(Criteria.where("id").is(chuzheng.getGdZb()));
        SlgMap slgMap = mongoTemplate.findOne(query, SlgMap.class);
        //计算攻击方的攻击总值
        Long gjl = gjl(chuzheng);
        //计算防守方的防御总值和防守方玩家的百分比
        Long fyl = (long) slgMap.getDksj() * Bzzy.getFangyu(slgMap.getSjlx());
        //计算双方实力差距
        if (gjl > fyl) {
            query = new Query(Criteria.where("id").is(chuzheng.getCzCityId()));
            UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
            double slcj = new BigDecimal(gjl).divide(new BigDecimal(fyl), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            gjl = (long) (gjl * slcj);
            double sszb = new BigDecimal(gjl - fyl).divide(new BigDecimal(gjl), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            gjfsy(sszb, chuzheng);
            wjjy.put(chuzheng.getCzWjId(), slgMap.getDksj() + ",0");
            zjwjjy();
            query = new Query(Criteria.where("id").is(chuzheng.getCzUserId()));
            User user = mongoTemplate.findOne(query, User.class);
            Update update = new Update();
            update.set("sswjId", user.getUserId());
            update.set("sswjName", user.getName());
            update.set("lmId", user.getLmId());
            update.set("lmmc", user.getLmmc());
            update.set("cityId", chuzheng.getCzCityId());
            mongoTemplate.updateFirst(query, update, SlgMap.class);
        } else {
            double slcj = new BigDecimal(fyl).divide(new BigDecimal(gjl), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }


    }

    public void jianmieWJ(Chuzheng chuzheng) {
        zengyuans.clear();
        zyzb.clear();
        zyblzs.clear();
        czwj.clear();
        wjjy.clear();
        wjjgfl.clear();
        Query gdQuery = new Query(Criteria.where("cityId").is(chuzheng.getGdCityId()));
        UserCity userCity = mongoTemplate.findOne(gdQuery, UserCity.class);
        Query zyQuery = new Query(Criteria.where("zyCityId").is(chuzheng.getGdCityId()));
        List<ZengYuan> zengYuans = mongoTemplate.find(zyQuery, ZengYuan.class);
        czwj.add(chuzheng.getCzUserId());
        czwj.add(chuzheng.getGdUserId());
        if (CollUtil.isNotEmpty(zengYuans)) {
            for (ZengYuan zengYuan : zengYuans) {
                zengyuans.put(zengYuan.getZyId(), zengYuan);
                czwj.add(zengYuan.getUserId());
                zyblzs.put(zengYuan.getZyId(), fsfbl(zengYuan));
            }
        } else {
            fszb = 1;
        }


        Map<String, Integer> jlzy = new HashMap<>();//劫掠资源

        //计算攻击方的攻击总值
        Long gjl = gjl(chuzheng);
        //计算防守方的防御总值和防守方玩家的百分比
        Long fyl = fyl(userCity);
        fszbl = GameUtil.zbl(userCity);
        //计算双方实力差距
        double slcj = fyl == 0 ? 1 : new BigDecimal(gjl).divide(new BigDecimal(fyl), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //计算冲车对城墙的伤害,双方实力1:1,60辆冲车可砸破城墙,
        //攻击方弱于防守方时, 实力差距 * 冲车数量 * 0.2 = 砸破城墙等级
        //0.6 * 100 * 0.2 = 12级
        int gjcq = (int) (slcj * chuzheng.getCc() * 0.2);
        //计算被破坏后的城墙等级,每一级增加1%的防御力
        int cqdj = userCity.getCq() - gjcq < 0 ? 0 : userCity.getCq() - gjcq;
        double cqjc = 1 + cqdj / 100;
        fyl = (long) (fyl * cqjc);
        int gjfbl = gjfbl(chuzheng);
        //攻击方胜利
        if (gjl > fyl) {
            slcj = 1 + slcj / 20;
            gjl = (long) (gjl * slcj);
            //获取损失的百分比
            double sszb = 0;
            if (fyl != 0) {
                sszb = new BigDecimal(gjl - fyl).divide(new BigDecimal(gjl), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            gjfsy(sszb, chuzheng);
            jlzy = jlzy(sszb, chuzheng, userCity);
            //计算攻击方剩余兵力
            int gjfsy = (int) (gjfbl * sszb);
            fsfsy(1, userCity, false);
            Map<String, String> fsfjg = fsfjg(chuzheng, userCity, false, gjfbl, sszb);
            Map<String, String> gjfjg = gjfjg(chuzheng, true, 1);
            String zbnr = zbnr(chuzheng, gjfbl, gjfsy, fszbl, 0, jlzy, fsfjg, gjfjg);
            fszbFunc("攻方胜", chuzheng, zbnr, fsfjg, gjfjg);
        }
        //防守方胜利
        else {
            slcj = new BigDecimal(fyl).divide(new BigDecimal(gjl), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            slcj = 1 + slcj / 20;
            fyl = (long) (fyl * slcj);
            //获取损失的百分比
            gjfsy(1, chuzheng);
            jlzy = jlzy(1, chuzheng, userCity);
            BigDecimal divide = new BigDecimal(fyl - gjl).divide(new BigDecimal(fyl), 2, BigDecimal.ROUND_HALF_UP);
            double sszb = divide.doubleValue();
            //计算防守方剩余兵力
            int fsfsy = (int) (fszbl * sszb);
            fsfsy(sszb, userCity, true);
            Map<String, String> fsfjg = fsfjg(chuzheng, userCity, true, gjfbl, 1);
            Map<String, String> gjfjg = gjfjg(chuzheng, false, sszb);
            String zbnr = zbnr(chuzheng, gjfbl, 0, fszbl, fsfsy, jlzy, fsfjg, gjfjg);
            fszbFunc("守方胜", chuzheng, zbnr, fsfjg, gjfjg);

        }
        zjwjjy();
        Chuzheng fanhui = new Chuzheng();
        BeanUtils.copyProperties(chuzheng, fanhui);
        fanhui.setCzId(UUID.randomUUID().toString().replaceAll("-", ""));
        fanhui.setDdsj((int) (DateUtil.currentSeconds() + chuzheng.getXjsj()));
        fanhui.setCzlx("返回");
        mongoTemplate.save(fanhui);
    }

    /**
     * 增加武将经验
     */
    public void zjwjjy() {
        Set<String> keys = wjjy.keySet();
        for (String key : keys) {
            cityService.zjwjjy(key, wjjy.get(key));
        }

    }

    /**
     * 发送战报
     *
     * @param slf
     * @param chuzheng
     * @param zbnr
     */
    public void fszbFunc(String slf, Chuzheng chuzheng, String zbnr, Map<String, String> fsjgfl, Map<String, String> gjjgfl) {
        //发送战报
        ZhanBao zhanBao = null;
        List<ZhanBao> zhanBaos = new ArrayList<>();
        for (String userId : czwj) {
            zhanBao = new ZhanBao();
            zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
            zhanBao.setUserId(userId);
            zhanBao.setDate(DateUtil.now());
            zhanBao.setZdfs(chuzheng.getCzlx());
            zhanBao.setSf(slf);
            zhanBao.setXxbt(chuzheng.getCzCityName() + "攻打" + chuzheng.getGdCityName() + "战报");
            zhanBao.setXxnr(zbnr);
            zhanBaos.add(zhanBao);
        }
        mongoTemplate.insertAll(zhanBaos);
    }

    /**
     * 计算攻击方军功和俘虏
     *
     * @param sl    是否胜利
     * @param fsssl 防守损失率,防守方损失率为n%,防守方全灭,该值为1
     */
    public Map<String, String> gjfjg(Chuzheng chuzheng, boolean sl, double fsssl) {
        int jg = fszbl;
        int fl = 0;
        String userId = chuzheng.getCzUserId();
        Map<String, String> gjfjg = new HashMap<>();
        if (CollUtil.isNotEmpty(zyblzs)) {
            Set<String> keys = zyblzs.keySet();
            for (String key : keys) {
                jg = jg + zyblzs.get(key);
            }
        }
        jg = fsssl == 1 ? jg : jg - (int) (jg * fsssl);
        if (sl) {
            //俘虏率5%
            fl = (int) (jg * 0.05);
        }
        gjfjg.put(userId, jg + "," + fl);
        Query userQuery = new Query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(userQuery, User.class);
        Update update = new Update();
        update.set("jungong", user.getJungong() + jg);
        update.set("flsl", user.getFlsl() + fl);
        mongoTemplate.updateFirst(userQuery, update, User.class);
        int lwzt = sl ? 0 : 1;
        wjjy.put(chuzheng.getCzWjId(), jg + "," + lwzt);
        return gjfjg;
    }

    /**
     * 防守方军功计算
     *
     * @param sl    胜利/失败
     * @param gjfbl 攻击方兵力
     * @param gjssl 攻击方损失率,攻击方损失率为n%,攻击方全灭,该值为1
     */
    public Map<String, String> fsfjg(Chuzheng chuzheng, UserCity userCity, boolean sl, Integer gjfbl, double gjssl) {
        int zjg = 0;//总军功
        int jg = 0;//个人军功
        int zfl = 0;//俘虏
        int fl = 0;//个人俘虏
        Map<String, String> fsjgfl = new HashMap<>();
        Map<String, Integer> yhjg = new HashMap<>();
        Map<String, Integer> yhfl = new HashMap<>();
        Set<String> keys = zengyuans.keySet();
        ZengYuan zengYuan;
        zjg = sl ?  gjfbl : gjfbl - (int) (gjfbl * gjssl);
        zfl = sl ? (int) (zjg * 0.05) : 0;
        int lwzt = sl ? 0 : 1;
        //如果带了投石车,胜利后破坏城市的建筑
        if (!sl && chuzheng.getTsc() > 0) {
            phjz(chuzheng.getTsc(), userCity);
        }
        if (CollUtil.isEmpty(keys)) {
            yhjg.put(chuzheng.getGdUserId(), zjg);
            yhfl.put(chuzheng.getGdUserId(), zfl);
            if (!"无".equals(userCity.getCcts())) {
                wjjy.put(userCity.getCcts(), zjg + "," + lwzt);
            }
        } else {
            for (String key : keys) {
                zengYuan = zengyuans.get(key);
                String userId = zengYuan.getUserId();
                jg = (int) (zyzb.get(key) * zjg);
                fl = (int) (zyzb.get(key) * zfl);
                wjjy.put(zengYuan.getCzWj(), jg + "," + lwzt);
                if (yhjg.get(userId) == null) {
                    yhjg.put(userId, jg);
                    yhfl.put(userId, fl);
                } else {
                    yhjg.put(userId, yhjg.get(userId) + jg);
                    yhfl.put(userId, yhjg.get(userId) + fl);
                }
            }
            yhjg.put(chuzheng.getGdUserId(), (int) (zjg * fszb));
            yhfl.put(chuzheng.getGdUserId(), (int) (zfl * fszb));

        }
        User user;
        Query query;
        Update update;
        Set<String> yhs = yhjg.keySet();
        for (String userId : yhs) {
            fsjgfl.put(userId, yhjg.get(userId) + "," + yhfl.get(userId));
            query = new Query(Criteria.where("userId").is(userId));
            user = mongoTemplate.findOne(query, User.class);
            update = new Update();
            update.set("jungong", user.getJungong() + yhjg.get(userId));
            update.set("flsl", user.getFlsl() + yhfl.get(userId));
            mongoTemplate.updateFirst(query, update, User.class);
        }
        return fsjgfl;

    }

    /**
     * 破坏城市建筑,每2个投石车可以破坏建筑1级
     *
     * @param tsc
     * @param userCity
     */
    public void phjz(Integer tsc, UserCity userCity) {
        int jzdj = userCity.getZgm();
        do {
            if (userCity.getNzt() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setNzt(userCity.getNzt() - 1);
            }
            if (userCity.getTqt() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setTqt(userCity.getTqt() - 1);
            }
            if (userCity.getJg() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setJg(userCity.getJg() - 1);
            }
            if (userCity.getJs() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setJs(userCity.getJs() - 1);
            }
            if (userCity.getBy() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setBy(userCity.getBy() - 1);
            }
            if (userCity.getLc() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setLc(userCity.getLc() - 1);
            }
            if (userCity.getCk() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setCk(userCity.getCk() - 1);
            }
            if (userCity.getAc() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setAc(userCity.getAc() - 1);
            }
            if (userCity.getCq() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setCq(userCity.getCq() - 1);
            }
            if (userCity.getLinchang() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setLinchang(userCity.getLinchang() - 1);
            }
            if (userCity.getShikuang() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setShikuang(userCity.getShikuang() - 1);
            }
            if (userCity.getTiekuang() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setTiekuang(userCity.getTiekuang() - 1);
            }
            if (userCity.getNongtian() > 0 && tsc >= 2) {
                tsc -= 2;
                jzdj -= 1;
                userCity.setNongtian(userCity.getNongtian() - 1);
            }
        } while (tsc >= 2 && jzdj > 0);
        Query query = new Query(Criteria.where("cityId").is(userCity.getCityId()));
        Update update = new Update();
        update.set("nzt", userCity.getNzt());
        update.set("tqt", userCity.getTqt());
        update.set("jg", userCity.getJg());
        update.set("js", userCity.getJs());
        update.set("by", userCity.getBy());
        update.set("lc", userCity.getLc());
        update.set("ck", userCity.getCk());
        update.set("ac", userCity.getAc());
        update.set("cq", userCity.getCq());
        update.set("linchang", userCity.getLinchang());
        update.set("shikuang", userCity.getShikuang());
        update.set("tiekuang", userCity.getTiekuang());
        update.set("nongtian", userCity.getNongtian());
        update.set("ckcc", Rongliang.getRongliang(userCity.getCk()));
        update.set("lccc", Rongliang.getRongliang(userCity.getLc()));
        update.set("mucl", Chanliang.getChanliang(userCity.getLinchang()));
        update.set("shicl", Chanliang.getChanliang(userCity.getShikuang()));
        update.set("tiecl", Chanliang.getChanliang(userCity.getTiekuang()));
        update.set("liangcl", Chanliang.getChanliang(userCity.getNongtian()));
        update.set("ccts", "无");
        update.set("cctsName", "无");
        update.set("zgm", GameUtil.zgm(userCity));
        mongoTemplate.updateFirst(query, update, UserCity.class);
    }

    /**
     * 战报内容
     *
     * @param gjfbl
     * @param gjfsy
     * @param jlzy
     * @return
     */
    public String zbnr(Chuzheng chuzheng, Integer gjfbl, Integer gjfsy, Integer fsfbl, Integer fsfsy, Map<String, Integer> jlzy, Map<String, String> fsfjg, Map<String, String> gjfjg) {
        StringBuffer sb = new StringBuffer();
        Set<String> zyIds = zengyuans.keySet();
        sb.append("攻击方:" + chuzheng.getCzUserName() + "-" + chuzheng.getCzCityName() + "<br/>");
        sb.append("兵力剩余:" + gjfsy + "/" + gjfbl + "<br/>");
        sb.append("获取军功:" + gjfjg.get(chuzheng.getCzUserId()).split(",")[0] + "  获取俘虏:" + gjfjg.get(chuzheng.getCzUserId()).split(",")[1] + "<br/> <br/>");
        sb.append("防守方:" + chuzheng.getGdUserName() + "-" + chuzheng.getGdCityName() + "<br/>");
        sb.append("兵力剩余:" + fsfsy + "/" + fsfbl + "<br/>");
        sb.append("获取军功:" + fsfjg.get(chuzheng.getGdUserId()).split(",")[0] + "  获取俘虏:" + fsfjg.get(chuzheng.getGdUserId()).split(",")[1] + "<br/> <br/>");
        ZengYuan zengyuan = null;
        for (String key : zyIds) {
            zengyuan = zengyuans.get(key);
            sb.append("防守方:" + zengyuan.getUserName() + "-" + zengyuan.getCityName() + "<br/>");
            sb.append("兵力剩余:" + zyblsyzs.get(key) + "/" + zyblzs.get(key) + "<br/>");
            sb.append("获取军功:" + fsfjg.get(key).split(",")[0] + "  获取俘虏:" + fsfjg.get(key).split(",")[1] + "<br/> <br/>");
        }
        if (jlzy != null) {
            sb.append("劫掠资源:木" + jlzy.get("mu") + ",石" + jlzy.get("shi") + ",铁" + jlzy.get("tie") + ",粮" + jlzy.get("liang"));
        } else {
            sb.append("劫掠资源:木0,石0,铁0,粮0");
        }
        return sb.toString();
    }

    /**
     * 计算出征总兵力
     *
     * @param chuzheng
     * @return
     */
    public Integer gjfbl(Chuzheng chuzheng) {
        int zbl = chuzheng.getBb() + chuzheng.getQb() + chuzheng.getNb() + chuzheng.getQq() + chuzheng.getHq() + chuzheng.getZq() + chuzheng.getCh() + chuzheng.getGb() + chuzheng.getCc() + chuzheng.getTsc();
        return zbl;
    }

    /**
     * 防守方总兵力
     *
     * @param zengyuan
     * @return
     */
    public Integer fsfbl(ZengYuan zengyuan) {
        int zbl = zengyuan.getBb() + zengyuan.getQb() + zengyuan.getNb() + zengyuan.getQq() + zengyuan.getHq() + zengyuan.getZq() + zengyuan.getCh() + zengyuan.getGb() + zengyuan.getCc() + zengyuan.getTsc();
        return zbl;
    }

    /**
     * 计算攻击方剩余兵力
     *
     * @author songhongxing
     * @date 2023/04/13 9:54 上午
     */
    public void gjfsy(double sszb, Chuzheng chuzheng) {
        Map<String, Integer> jlzy;
        if (sszb != 0) {
            int bb = sszb < 1 ? (int) (chuzheng.getBb() * sszb) : 0;
            int qb = sszb < 1 ? (int) (chuzheng.getQb() * sszb) : 0;
            int nb = sszb < 1 ? (int) (chuzheng.getNb() * sszb) : 0;
            int qq = sszb < 1 ? (int) (chuzheng.getQq() * sszb) : 0;
            int hq = sszb < 1 ? (int) (chuzheng.getHq() * sszb) : 0;
            int zq = sszb < 1 ? (int) (chuzheng.getZq() * sszb) : 0;
            int cc = sszb < 1 ? (int) (chuzheng.getCc() * sszb) : 0;
            int tsc = sszb < 1 ? (int) (chuzheng.getTsc() * sszb) : 0;
            Update update = new Update();
            update.set("bb", bb);
            update.set("qb", qb);
            update.set("nb", nb);
            update.set("qq", qq);
            update.set("zq", zq);
            update.set("hq", hq);
            update.set("cc", cc);
            update.set("tsc", tsc);
            chuzheng.setBb(bb);
            chuzheng.setQq(qb);
            chuzheng.setNb(nb);
            chuzheng.setQq(qq);
            chuzheng.setHq(hq);
            chuzheng.setZq(zq);
            chuzheng.setCc(cc);
            chuzheng.setTsc(tsc);
        }
    }

    public Map<String, Integer> jlzy(double sszb, Chuzheng chuzheng, UserCity userCity) {
        return jlzy(userCity, zyz(chuzheng.getBb(), chuzheng.getQq(), chuzheng.getNb(), chuzheng.getQb(), chuzheng.getHq(), chuzheng.getZq()));

    }

    /**
     * 兵力回到城市
     *
     * @param chuzheng
     * @return
     */
    public void fanhui(Chuzheng chuzheng) {
        Query cityQuery = new Query(Criteria.where("cityId").is(chuzheng.getCzCityId()));
        UserCity userCity = mongoTemplate.findOne(cityQuery, UserCity.class);
        userCity.setBb(userCity.getBb() - chuzheng.getBb());
        userCity.setQb(userCity.getQb() - chuzheng.getQb());
        userCity.setNb(userCity.getNb() - chuzheng.getNb());
        userCity.setQq(userCity.getQq() - chuzheng.getQq());
        userCity.setHq(userCity.getHq() - chuzheng.getHq());
        userCity.setZq(userCity.getZq() - chuzheng.getZq());
        userCity.setCc(userCity.getCc() - chuzheng.getCc());
        userCity.setTsc(userCity.getTsc() - chuzheng.getTsc());
        Update update = new Update();
        update.set("bb", userCity.getBb());
        update.set("qb", userCity.getQb());
        update.set("nb", userCity.getNb());
        update.set("qq", userCity.getQq());
        update.set("zq", userCity.getZq());
        update.set("hq", userCity.getHq());
        update.set("cc", userCity.getCc());
        update.set("tsc", userCity.getTsc());
        update.set("zhl", GameUtil.zhl(userCity));
        update.set("zbl", GameUtil.zbl(userCity));
        mongoTemplate.updateFirst(cityQuery, update, UserCity.class);
    }

    /**
     * 防守兵力剩余
     *
     * @param sszb
     */
    public void fsfsy(double sszb, UserCity userCity, boolean sl) {
        Query cityQuery = new Query(Criteria.where("cityId").is(userCity.getCityId()));
        int bb = sl ? (int) (userCity.getBb() * sszb) : 0;
        int qb = sl ? (int) (userCity.getQb() * sszb) : 0;
        int nb = sl ? (int) (userCity.getNb() * sszb) : 0;
        int qq = sl ? (int) (userCity.getQq() * sszb) : 0;
        int hq = sl ? (int) (userCity.getHq() * sszb) : 0;
        int zq = sl ? (int) (userCity.getZq() * sszb) : 0;
        int cc = sl ? (int) (userCity.getCc() * sszb) : 0;
        int tsc = sl ? (int) (userCity.getTsc() * sszb) : 0;
        int gb = sl ? (int) (userCity.getGb() * sszb) : 0;
        int ch = sl ? (int) (userCity.getCh() * sszb) : 0;
        Update update = new Update();
        update.set("bb", bb);
        update.set("qb", qb);
        update.set("nb", nb);
        update.set("qq", qq);
        update.set("zq", zq);
        update.set("hq", hq);
        update.set("cc", cc);
        update.set("tsc", tsc);
        update.set("gb", gb);
        update.set("ch", ch);
        update.set("zhl", GameUtil.zhl(userCity));
        update.set("zbl", GameUtil.zbl(userCity));
        mongoTemplate.updateFirst(cityQuery, update, UserCity.class);
        ZengYuan zengYuan;
        UserCity zyUserCity;
        Query zyCityQuery;
        Query deleteQuery;
        if (CollUtil.isNotEmpty(zengyuans)) {
            Set<String> keys = zengyuans.keySet();
            for (String key : keys) {
                zengYuan = zengyuans.get(key);
                update = new Update();
                zyCityQuery = new Query(Criteria.where("cityId").is(zengYuan.getCityId()));
                zyUserCity = mongoTemplate.findOne(zyCityQuery, UserCity.class);
                update.set("bb", sl ? zyUserCity.getBb() - (int) (zengYuan.getBb() * sszb) : zyUserCity.getBb() - zengYuan.getBb());
                update.set("qb", sl ? zyUserCity.getQb() - (int) (zengYuan.getQb() * sszb) : zyUserCity.getQb() - zengYuan.getQb());
                update.set("nb", sl ? zyUserCity.getNb() - (int) (zengYuan.getNb() * sszb) : zyUserCity.getNb() - zengYuan.getNb());
                update.set("qq", sl ? zyUserCity.getQq() - (int) (zengYuan.getQq() * sszb) : zyUserCity.getQq() - zengYuan.getQq());
                update.set("zq", sl ? zyUserCity.getZq() - (int) (zengYuan.getZq() * sszb) : zyUserCity.getZq() - zengYuan.getZq());
                update.set("hq", sl ? zyUserCity.getHq() - (int) (zengYuan.getHq() * sszb) : zyUserCity.getHq() - zengYuan.getHq());
                update.set("cc", sl ? zyUserCity.getCc() - (int) (zengYuan.getCc() * sszb) : zyUserCity.getCc() - zengYuan.getCc());
                update.set("tsc", sl ? zyUserCity.getTsc() - (int) (zengYuan.getTsc() * sszb) : zyUserCity.getTsc() - zengYuan.getTsc());
                update.set("gb", sl ? zyUserCity.getGb() - (int) (zengYuan.getGb() * sszb) : zyUserCity.getGb() - zengYuan.getGb());
                update.set("ch", sl ? zyUserCity.getCh() - (int) (zengYuan.getCh() * sszb) : zyUserCity.getCh() - zengYuan.getCh());
                mongoTemplate.updateFirst(zyCityQuery, update, UserCity.class);
                if (!sl) {
                    deleteQuery = new Query(Criteria.where("zyId").is(zengYuan.getZyId()));
                    mongoTemplate.remove(deleteQuery, ZengYuan.class);
                }
            }
        }

    }

    /**
     * 劫掠资源
     *
     * @param userCity
     * @param yzl
     * @return
     */
    public Map<String, Integer> jlzy(UserCity userCity, Integer yzl) {
        int mu = userCity.getMucc() - Ancang.getRongliang(userCity.getAc());
        int shi = userCity.getMucc() - Ancang.getRongliang(userCity.getAc());
        int tie = userCity.getMucc() - Ancang.getRongliang(userCity.getAc());
        int liang = userCity.getMucc() - Ancang.getRongliang(userCity.getAc());
        return jlzy(mu, shi, tie, liang, yzl);
    }

    /**
     * 劫掠资源
     * @param mu
     * @param shi
     * @param tie
     * @param liang
     * @param yzl
     * @return
     */
    public Map<String, Integer> jlzy(int mu, int shi, int tie, int liang, Integer yzl) {
        Map<String, Integer> jlzy = new HashMap<>();
        int i = 4;
        List<String> list = new ArrayList<>();
        jlzy.put("mu", 0);
        jlzy.put("shi", 0);
        jlzy.put("tie", 0);
        jlzy.put("liang", 0);
        boolean flag = true;
        while (flag) {
            int zyfp = yzl / i;
            if (!list.contains("mu")) {
                if (mu > zyfp) {
                    mu = mu - zyfp;
                    jlzy.put("mu", jlzy.get("mu") + zyfp);
                    yzl -= zyfp;
                } else {
                    jlzy.put("mu", jlzy.get("mu") + mu);
                    yzl -= mu;
                    mu = 0;
                    i -= 1;
                    list.add("mu");
                }
            }
            if (!list.contains("shi")) {
                if (shi > zyfp) {
                    shi = shi - zyfp;
                    jlzy.put("shi", jlzy.get("shi") + zyfp);
                    yzl -= zyfp;
                } else {
                    jlzy.put("shi", jlzy.get("shi") + shi);
                    yzl -= shi;
                    shi = 0;
                    i -= 1;
                    list.add("shi");
                }
            }

            if (!list.contains("tie")) {
                if (tie > zyfp) {
                    tie = tie - zyfp;
                    jlzy.put("tie", jlzy.get("tie") + zyfp);
                    yzl -= zyfp;
                } else {
                    jlzy.put("tie", jlzy.get("tie") + tie);
                    yzl -= tie;
                    tie = 0;
                    i -= 1;
                    list.add("tie");
                }
            }
            if (!list.contains("liang")) {
                if (liang > zyfp) {
                    liang = liang - zyfp;
                    jlzy.put("liang", jlzy.get("liang") + zyfp);
                    yzl -= zyfp;
                } else {
                    jlzy.put("liang", jlzy.get("liang") + liang);
                    yzl -= liang;
                    liang = 0;
                    i -= 1;
                    list.add("liang");
                }
            }
            if (i <= 0 || yzl <= 0 || zyfp <= 0) {
                flag = false;
            }
        }
        return jlzy;
    }


    /**
     * 计算总运载
     *
     * @return
     */
    public Integer zyz(int bb, int qb, int nb, int qq, int hq, int zq) {
        Integer zyz = 0;
        zyz += Bzzy.BB.getYz() * bb;
        zyz += Bzzy.QB.getYz() * qb;
        zyz += Bzzy.NB.getYz() * nb;
        zyz += Bzzy.QQ.getYz() * qq;
        zyz += Bzzy.ZQ.getYz() * zq;
        zyz += Bzzy.HQ.getYz() * hq;
        return zyz;
    }

    /**
     * 获取攻击力
     *
     * @param chuzheng
     * @return
     */
    public Long gjl(Chuzheng chuzheng) {
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
     * 防守方和增援方的防守占比计算
     */
    public Long fyl(UserCity userCity) {
        long fyzz = 0;//防御总值
        //每个增援数据的防御力
        Map<String, Long> zyfFyl = new HashMap<>();
        //城池的防御力
        Long ccfyl = ccFyl(userCity);
        fyzz += ccfyl;
        Set<String> zengyuanList = zengyuans.keySet();
        ZengYuan zengYuan = null;
        if (CollUtil.isNotEmpty(zengyuanList)) {
            for (String key : zengyuanList) {
                zengYuan = zengyuans.get(key);
                Long fyl = zyFyl(zengYuan);
                zyfFyl.put(zengYuan.getZyId(), fyl);
                fyzz += fyl;
            }
        }
        //遍历每个玩家的防御占比
        Set<String> wjcc = zyfFyl.keySet();
        for (String key : wjcc) {
            //玩家部队防御
            Long wjbdfy = zyfFyl.get(key);
            zyzb.put(key, Double.valueOf(String.format("%.2f", (double) (wjbdfy / fyzz))));
        }
        if (fyzz == 0) {
            fszb = 0;
            fszbl = 0;
        } else {
            fszb = Double.valueOf(String.format("%.2f", (double) (ccfyl / fyzz)));
            fszbl = GameUtil.zbl(userCity);
        }
        return fyzz;
    }

    /**
     * 城池防御力
     *
     * @param userCity
     * @return
     */
    public Long ccFyl(UserCity userCity) {
        //查询武将
        double wljc = 1.0;
        if (!"无".equals(userCity.getCcts())) {
            Query wjQuery = new Query(Criteria.where("wjId").is(userCity.getCcts()));
            Wujiang wujiang = mongoTemplate.findOne(wjQuery, Wujiang.class);
            wljc = (wujiang.getFy() + 100) / 100;//攻击加成
        }
//        fsbl.put(userCity.getCityId(), GameUtil.zbl(userCity));
//        czlb.put(userCity.getCityId(), "防守方" + "-" + userCity.getUserId() + "-" + userName + "-" + userCity.getCityName());
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
     *
     * @param chuzheng
     */
    public void jiancheng(Chuzheng chuzheng) {
        cityService.createCity(chuzheng.getCzUserId(), "新的城市", chuzheng.getCzZb());
        //发系统消息
        ZhanBao zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getCzUserId());
        zhanBao.setZdfs(chuzheng.getCzlx());
        zhanBao.setXxbt("分城建造完成");
        zhanBao.setDate(DateUtil.now());
        zhanBao.setXxnr("工兵在" + chuzheng.getCzZb() + "坐标处建造一座分城，请主公前往。");
        mongoTemplate.save(zhanBao);
    }

    /**
     * 侦察
     *
     * @param chuzheng
     */
    public void zhencha(Chuzheng chuzheng) {
        Query query = new Query(Criteria.where("userId").is(chuzheng.getGdUserId()));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        //发系统消息
        ZhanBao zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getCzUserId());
        zhanBao.setDate(DateUtil.now());
        zhanBao.setZdfs(chuzheng.getCzlx());
        zhanBao.setSf("攻方胜");
        zhanBao.setXxbt("侦察" + chuzheng.getCzCityName() + "战报");
        StringBuffer zbnr = new StringBuffer();

        zbnr.append("城市名:" + userCity.getCityName() + "<br/> <br/>");
        if (userCity.getBb() > 0) {
            zbnr.append("步兵:" + userCity.getBb() + "<br/>");
        }
        if (userCity.getQb() > 0) {
            zbnr.append("枪兵:" + userCity.getQb() + "<br/>");
        }
        if (userCity.getNb() > 0) {
            zbnr.append("弩兵:" + userCity.getNb() + "<br/>");
        }
        if (userCity.getQq() > 0) {
            zbnr.append("轻骑:" + userCity.getQq() + "<br/>");
        }
        if (userCity.getHq() > 0) {
            zbnr.append("虎骑:" + userCity.getHq() + "<br/>");
        }
        if (userCity.getZq() > 0) {
            zbnr.append("重骑:" + userCity.getZq() + "<br/>");
        }
        if (userCity.getCh() > 0) {
            zbnr.append("斥候:" + userCity.getCh() + "<br/>");
        }
        if (userCity.getCc() > 0) {
            zbnr.append("冲车:" + userCity.getCc() + "<br/>");
        }
        if (userCity.getTsc() > 0) {
            zbnr.append("投石车:" + userCity.getTsc() + "<br/>");
        }
        zbnr.append("资源:木" + userCity.getMucc() + ",石" + userCity.getShicc() + ",铁" + userCity.getTiecc() + ",粮" + userCity.getLiangcc());
        zhanBao.setXxnr(zbnr.toString());
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
        zengYuan.setXjsj(chuzheng.getXjsj());
        Query query = new Query(Criteria.where("cityId").is(chuzheng.getGdCityId()));
        Update update = new Update();
        update.set("zhl", cityZhl(chuzheng.getGdCityId()));
        mongoTemplate.updateFirst(query, update, UserCity.class);
        //发系统消息
        ZhanBao zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getGdUserId());
        zhanBao.setXxbt("增援到达");
        zhanBao.setXxnr(chuzheng.getCzUserName() + "的增援的兵力已到达" + chuzheng.getGdCityName() + "城池,请主公前往查看。");
        mongoTemplate.save(zhanBao);
        zhanBao = new ZhanBao();
        zhanBao.setZbId(UUID.randomUUID().toString().replaceAll("-", ""));
        zhanBao.setUserId(chuzheng.getCzUserId());
        zhanBao.setXxbt("增援到达");
        zhanBao.setXxnr(chuzheng.getCzUserName() + "的增援的兵力已到达" + chuzheng.getGdCityName() + "城池,请主公前往查看。");
        mongoTemplate.save(zhanBao);
    }

    /**
     * 计算城市总耗粮
     *
     * @param cityId
     * @return
     */
    public Integer cityZhl(String cityId) {
        Integer zhl = 0;
        Query query = new Query(Criteria.where("cityId").is(cityId));
        UserCity userCity = mongoTemplate.findOne(query, UserCity.class);
        zhl += GameUtil.zhl(userCity);
        query = new Query(Criteria.where("zyCityId").is(cityId));
        List<ZengYuan> zengYuans = mongoTemplate.find(query, ZengYuan.class);
        if (CollUtil.isNotEmpty(zengYuans)) {
            for (ZengYuan zengYuan : zengYuans) {
                zhl += GameUtil.zyzhl(zengYuan);
            }
        }
        return zhl;
    }


    public List<ZhanBao> zblb(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.with(Sort.by(Sort.Direction.DESC, "date"));
        return mongoTemplate.find(query, ZhanBao.class);

    }

    public void zbyd(String zbId) {
        Query query = new Query(Criteria.where("zbId").is(zbId));
        Update update = new Update();
        update.set("yd", 1);
        mongoTemplate.updateFirst(query, update, ZhanBao.class);
    }

    /**
     * 部队召回
     *
     * @param zyId
     * @return
     */
    public void bdzh(String zyId) {
        Query query = new Query(Criteria.where("zyId").is(zyId));
        ZengYuan zengYuan = mongoTemplate.findOne(query, ZengYuan.class);
        Chuzheng chuzheng = new Chuzheng();
        chuzheng.setCzId(UUID.randomUUID().toString().replaceAll("-", ""));
        chuzheng.setCzUserId(zengYuan.getUserId());
        chuzheng.setCzUserName(zengYuan.getUserName());
        chuzheng.setCzCityId(zengYuan.getCityId());
        chuzheng.setCzZb(0);
        chuzheng.setCzlx("返回");
        chuzheng.setCzWjId(zengYuan.getCzWj());
        chuzheng.setDdsj((int) (DateUtil.currentSeconds() + zengYuan.getXjsj()));
        chuzheng.setBb(zengYuan.getBb());
        chuzheng.setQb(zengYuan.getQb());
        chuzheng.setNb(zengYuan.getNb());
        chuzheng.setQq(zengYuan.getQq());
        chuzheng.setHq(zengYuan.getHq());
        chuzheng.setZq(zengYuan.getZq());
        chuzheng.setCh(zengYuan.getCh());
        chuzheng.setCc(zengYuan.getCc());
        chuzheng.setTsc(zengYuan.getTsc());
        chuzheng.setGb(zengYuan.getGb());
        chuzheng.setXjsj(zengYuan.getXjsj());
        mongoTemplate.save(chuzheng);
        mongoTemplate.remove(query, ZengYuan.class);
        Integer zhl = cityZhl(zengYuan.getZyCityId());
        Update update = new Update();
        update.set("zhl", zhl);
        query = new Query(Criteria.where("cityId").is(zengYuan.getZyCityId()));
        mongoTemplate.updateFirst(query, update, UserCity.class);
    }

    public List<ZengYuan> zywd(String userId) {
        Query query = new Query(Criteria.where("zyUserId").is(userId));
        return mongoTemplate.find(query, ZengYuan.class);
    }

    public List<ZengYuan> wzyd(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, ZengYuan.class);
    }
}
