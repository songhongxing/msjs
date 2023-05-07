package com.yuxi.msjs.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.bulk.BulkWriteResult;
import com.yuxi.msjs.bean.conste.Bingzhong;
import com.yuxi.msjs.bean.conste.Shoujun;
import com.yuxi.msjs.bean.entity.*;
import com.yuxi.msjs.service.CdkService;
import com.yuxi.msjs.service.CityService;
import com.yuxi.msjs.service.PaihangService;
import com.yuxi.msjs.service.ZhanDouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 更新城市资源
 *
 * @author songhongxing
 * @date 2023/02/27 11:01 上午
 */
@Component
public class ZiyuanTask {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CityService cityService;
    @Autowired
    private ZhanDouService zhanDouService;
    @Autowired
    private PaihangService paihangService;
    @Autowired
    private CdkService cdkService;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 资源变化
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void task() {
        List<UserCity> userCities = mongoTemplate.findAll(UserCity.class);
        if (CollUtil.isEmpty(userCities)) {
            return;
        }
        Query query;
        Update update;
        List<Pair<Query, Update>> updateList = new ArrayList<>(userCities.size());
        BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "user_city");
        for (UserCity userCity : userCities) {
            query = new Query();
            query.addCriteria(Criteria.where("cityId").is(userCity.getCityId()));
            int mucc = Integer.valueOf(userCity.getMucl() / 30) + userCity.getMucc() + userCity.getMujccl();
            if (mucc > userCity.getCkcc()) {
                userCity.setMucc(userCity.getCkcc());
            } else {
                userCity.setMucc(mucc);
            }
            int shicc = Integer.valueOf(userCity.getShicl() / 30) + userCity.getShicc() + userCity.getShijccl();
            if (shicc > userCity.getCkcc()) {
                userCity.setShicc(userCity.getCkcc());
            }else {
                userCity.setShicc(shicc);
            }
            int tiecc = Integer.valueOf(userCity.getTiecl() / 30) + userCity.getTiecc() + userCity.getTiejccl();
            if (tiecc > userCity.getCkcc()) {
                userCity.setTiecc(userCity.getCkcc());
            } else{
                userCity.setTiecc(tiecc);
            }
            int liangcc = Integer.valueOf(userCity.getLiangcl() / 30) + userCity.getLiangcc() + userCity.getLiangjccl();
            if (liangcc > userCity.getLccc()) {
                userCity.setLiangcc(userCity.getLccc());
            } else {
                userCity.setLiangcc(liangcc);
            }
            update = new Update();
            update.set("mucc", userCity.getMucc());
            update.set("shicc", userCity.getShicc());
            update.set("tiecc", userCity.getTiecc());
            update.set("liangcc", userCity.getLiangcc() < 0 ? 0 : userCity.getLiangcc());
            Pair<Query, Update> updatePair = Pair.of(query, update);
            updateList.add(updatePair);
        }
        operations.upsert(updateList);
        BulkWriteResult execute = operations.execute();
    }

    /**
     * 恢复地图守军
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void hfsj() {
        Query query = new Query(Criteria.where("hfsj").lte(DateUtil.currentSeconds()).andOperator(Criteria.where("hfsj").gt(0)));
        List<SlgMap> slgMaps = mongoTemplate.find(query, SlgMap.class);
        if(CollUtil.isNotEmpty(slgMaps)){
            Query updateQuery;
            Update update;
            List<Pair<Query, Update>> updateList = new ArrayList<>(slgMaps.size());
            BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "slg_map");
            for(SlgMap slgMap : slgMaps){
                updateQuery = new Query(Criteria.where("_id").is(slgMap.getId()));
                update = new Update();
                update.set("hfsj",0);
                update.set("dksj", Shoujun.getKeyByValue(slgMap.getDkdj()));
                Pair<Query, Update> updatePair = Pair.of(updateQuery, update);
                updateList.add(updatePair);
            }
            operations.upsert(updateList);
            BulkWriteResult execute = operations.execute();
        }

    }

    /**
     * 建筑升级
     * @author songhongxing
     * @date 2023/03/01 1:22 下午
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void jianzhusj(){
        Query query = new Query(Criteria.where("dqsj").lte(DateUtil.currentSeconds()));
        List<HomeUp> homeUpList = mongoTemplate.find(query, HomeUp.class);
        if (CollUtil.isEmpty(homeUpList)) {
            return;
        }

        for (HomeUp homeUp : homeUpList) {
            cityService.sjwc(homeUp.getCityId(), homeUp.getJzName());
        }

    }

    /**
     * 免战回复
     * @author songhongxing
     * @date 2023/03/01 1:22 下午
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void mianzhan(){
      zhanDouService.mzdq();
    }

    /**
     * 征兵队列
     *
     * @author songhongxing
     * @date 2023/03/02 4:05 下午
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void zhengbing() {
        List<ZhengBing> zbdl = mongoTemplate.findAll(ZhengBing.class);
        if (CollUtil.isEmpty(zbdl)) {
            return;
        }
        Query query;
        Update update;
        UserCity userCity;
        for (ZhengBing zhengBing : zbdl) {
            //判断是否征兵完成
            query = new Query(Criteria.where("cityId").is(zhengBing.getCityId()));
            userCity = mongoTemplate.findOne(query, UserCity.class);
            //计算征兵了多久
            int zbhf = (int) DateUtil.currentSeconds() - zhengBing.getKssj();
            //计算征了几个
            int yzm = (int) (zbhf / zhengBing.getDghs());
            //获取兵种简称
            String bzjc = Bingzhong.getKeyByValue(zhengBing.getBz());
            //获取兵种的数量
            int zbsl = getSl(userCity, bzjc) + yzm;
            update = new Update();
            update.set(bzjc, zbsl);
            mongoTemplate.updateFirst(query, update, "user_city");
            query = new Query(Criteria.where("cityId").is(zhengBing.getCityId()).and("bz").is(zhengBing.getBz()));
            update = new Update();
            if(zhengBing.getSl() - yzm < 0){
                update.set("sl", zhengBing.getSl());
                mongoTemplate.remove(query,"zhengbing");
            }else {
                update.set("kssj", (int) DateUtil.currentSeconds());
                update.set("sl", zhengBing.getSl() - yzm);
            }
            mongoTemplate.updateFirst(query, update, "zhengbing");

        }
    }

    /**
     * 重新设置登录时间
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void csdenglu(){
        Query query = new Query(Criteria.where("xdl").ne(0));
        Update update = new Update();
        update.set("xdl", 0);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void zongbingli() {
        //计算城市总兵力
        List<UserCity> cityList = mongoTemplate.findAll(UserCity.class);
        Query bingliQuery;
        Query query;
        User user = null;
        Update bingliUpdate = new Update();
        if(CollUtil.isNotEmpty(cityList)){
            for(UserCity city : cityList){
                query = new Query(Criteria.where("userId").is(city.getUserId()));
                bingliQuery = new Query(Criteria.where("cityId").is(city.getCityId()));
                int zgm = city.getNzt()+city.getTqt()+city.getJg()+city.getJs()+city.getBy()+city.getCk()+city.getLc()+city.getAc()+city.getCq()+city.getLc()+city.getShikuang()+city.getTiekuang()+city.getNongtian();
                int zbl = city.getBb()+city.getQb()+city.getNb()+city.getQq()+city.getHq()+city.getZq()+city.getCh()+city.getGb()+city.getCc()+city.getTsc();
                bingliUpdate.set("zbl", zbl);
                bingliUpdate.set("zgm", zgm);
                int zhl = zhanDouService.cityZhl(city.getCityId());
                bingliUpdate.set("zhl", zhl);
                user = mongoTemplate.findOne(query, User.class);
                if(city.getCityId() != null){
                    Integer lscl = cityService.lscl(city.getCityId(), user.getLmId(),"农田");
                    bingliUpdate.set("liangcl", lscl - zhl);
                    Integer tiecl = cityService.lscl(city.getCityId(), user.getLmId(),"铁矿");
                    bingliUpdate.set("tiecl", tiecl);
                    Integer shicl = cityService.lscl(city.getCityId(), user.getLmId(),"石矿");
                    bingliUpdate.set("shicl", shicl);
                    Integer mucl = cityService.lscl(city.getCityId(), user.getLmId(),"林场");
                    bingliUpdate.set("mucl", mucl);
                    mongoTemplate.updateFirst(bingliQuery, bingliUpdate, "user_city");
                }
            }
        }
    }

    /**
     * 获取兵种数量
     *
     * @param userCity
     * @param column
     * @author songhongxing
     * @date 2023/03/02 4:18 下午
     */
    private Integer getSl(UserCity userCity, String column) {
        switch (column) {
            case "bb":
                return userCity.getBb();
            case "qb":
                return userCity.getQb();
            case "nb":
                return userCity.getNb();
            case "qq":
                return userCity.getQq();
            case "hq":
                return userCity.getHq();
            case "zq":
                return userCity.getZq();
            case "ch":
                return userCity.getCh();
            case "cc":
                return userCity.getCc();
            case "tsc":
                return userCity.getTsc();
            case "gb":
                return userCity.getGb();
        }
        return 0;
    }

    /**
     * 处理战斗相关
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void zhandou() {
        Query jqQuery = new Query(Criteria.where("ddsj").lte(DateUtil.currentSeconds()));
        List<Chuzheng> chuzhengs = mongoTemplate.find(jqQuery, Chuzheng.class);
        if (CollUtil.isNotEmpty(chuzhengs)) {
            for (Chuzheng chuzheng : chuzhengs) {
                ZhanDouThread zhanDouThread = new ZhanDouThread(chuzheng.getCzId(), mongoTemplate, zhanDouService);
                executorService.execute(zhanDouThread);
            }
        }
    }

    /**
     * 每天定榜发送奖励
     */

    @Scheduled(cron = "0 0 0 * * ?")
    public void paihang() {
        paihangService.guimoph();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void gqcdk() {
        cdkService.gqcdk();
    }

    /**
     * 每半小时统计一次规模
     */
    @Scheduled(cron = "0 0/60 * * * ?")
    public void tjgm() {
        paihangService.tjgm();
    }

    /**
     * 计算攻击方战斗力
     *     计算防守方战斗力
     *     根据不同的战斗方式生成不同的结果
     *     生成战报
     *     扣除攻击方,防守方的兵力,增援方如果有剩余的兵力,返回城市
     *     刷新本次战斗所有参与城市的耗粮
     *     获取俘虏总数,获取军功计算到个人信息
     *     删除这条军情
     */
    public class ZhanDouThread implements Runnable{
        private String czId;
        private MongoTemplate mongoTemplate;
        private ZhanDouService zhanDouService;

        public ZhanDouThread(String czId, MongoTemplate mongoTemplate, ZhanDouService zhanDouService) {
            this.czId = czId;
            this.mongoTemplate = mongoTemplate;
            this.zhanDouService = zhanDouService;
        }

        @Override
        public void run() {
            Query query = new Query(Criteria.where("czId").is(this.czId));
            Chuzheng chuzheng = mongoTemplate.findOne(query, Chuzheng.class);
            //计算
            if ("歼灭".equals(chuzheng.getCzlx())) {
                zhanDouService.jianmie(chuzheng);
            } else if ("建造".equals(chuzheng.getCzlx())) {
                zhanDouService.jiancheng(chuzheng);
            } else if ("侦察".equals(chuzheng.getCzlx())) {
                zhanDouService.zhencha(chuzheng);
            } else if ("劫掠".equals(chuzheng.getCzlx())) {
                zhanDouService.jielue(chuzheng);
            } else if ("增援".equals(chuzheng.getCzlx())) {
                zhanDouService.zengyuan(chuzheng);
            } else if ("返回".equals(chuzheng.getCzlx())) {
                zhanDouService.fanhui(chuzheng);
            }
        }
    }
}
