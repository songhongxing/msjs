package com.yuxi.msjs.task;

import cn.hutool.core.collection.CollUtil;
import com.mongodb.bulk.BulkWriteResult;
import com.yuxi.msjs.bean.entity.UserCity;
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

    @Scheduled(cron = "0/10 * * * * ?")
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
                userCity.setMucc(userCity.getMucl() * 2 + userCity.getMucc());
            }
            if (userCity.getShicc() < userCity.getCkcc()) {
                userCity.setShicc(userCity.getShicl() * 2 + userCity.getShicc());
            }
            if (userCity.getTiecc() < userCity.getCkcc()) {
                userCity.setTiecc(userCity.getTiecl() * 2 + userCity.getTiecc());
            }
            if (userCity.getLiangcc() < userCity.getLccc()) {
                userCity.setLiangcc(userCity.getLiangcl() * 2 + userCity.getLiangcc());
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

//    @Scheduled(cron = "0/1 * * * * ?")
//    public void ss(){
//        System.out.println("aaaa");
//    }
}
