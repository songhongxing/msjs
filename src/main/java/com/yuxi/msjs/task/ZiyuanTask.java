package com.yuxi.msjs.task;

import cn.hutool.core.collection.CollUtil;
import com.mongodb.bulk.BulkWriteResult;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.service.CityService;
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

    @Scheduled(cron = "0 0/2 * * * ?")
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
            if (userCity.getMucc() < userCity.getCkcc()) {
                userCity.setMucc(Integer.valueOf(userCity.getMucl() / 30) + userCity.getMucc());
            }
            if (userCity.getShicc() < userCity.getCkcc()) {
                userCity.setShicc(Integer.valueOf(userCity.getShicl() / 30) + userCity.getShicc());
            }
            if (userCity.getTiecc() < userCity.getCkcc()) {
                userCity.setTiecc(Integer.valueOf(userCity.getTiecl() / 30) + userCity.getTiecc());
            }
            if (userCity.getLiangcc() < userCity.getLccc()) {
                userCity.setLiangcc(Integer.valueOf(userCity.getLiangcl() / 30) + userCity.getLiangcc());
            }
            update = new Update();
            update.set("mucc", userCity.getMucc());
            update.set("shicc", userCity.getShicc());
            update.set("tiecc", userCity.getTiecc());
            update.set("liangcc", userCity.getLiangcc());
//            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, "user_city");
//            assert updateResult.wasAcknowledged();

            Pair<Query, Update> updatePair = Pair.of(query, update);
            updateList.add(updatePair);
        }
        operations.upsert(updateList);
        BulkWriteResult execute = operations.execute();
    }

    /**
     * 建筑升级
     * @author songhongxing
     * @date 2023/03/01 1:22 下午
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void jianzhusj(){
        Query query = new Query(Criteria.where("dasj").lte(System.currentTimeMillis()/1000));
        List<HomeUp> homeUpList = mongoTemplate.find(query, HomeUp.class);
        if(CollUtil.isEmpty(homeUpList)){
            return;
        }

        for(HomeUp homeUp : homeUpList){
            cityService.sjwc(homeUp.getCityId(), homeUp.getJzName());
        }

    }
}
