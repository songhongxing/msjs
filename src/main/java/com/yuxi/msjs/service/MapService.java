package com.yuxi.msjs.service;

import cn.hutool.core.util.RandomUtil;
import com.yuxi.msjs.bean.Shoujun;
import com.yuxi.msjs.bean.entity.SlgMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/02/25 3:38 下午
 */
@Service
public class MapService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private String[] dklb = {"村庄","农田", "林场","村庄", "石矿", "铁矿"};

    public void insert() {

        for (int i = 0; i < 1000; i++) {
            SlgMap slgMap = new SlgMap();
            slgMap.setId(i);
            slgMap.setDklx(dklb[RandomUtil.randomInt(0, dklb.length)]);
            if ("村庄".equals(slgMap.getDklx())) {
                slgMap.setDkmc("村庄");
                slgMap.setDkdj(0);
                slgMap.setDksj(0);
            } else {
                slgMap.setDkmc(slgMap.getDklx());
                slgMap.setDkdj(RandomUtil.randomInt(1, 5));
                slgMap.setDksj(Shoujun.getKeyByValue(slgMap.getDkdj()));
            }
            slgMap.setMzbz(0);
            slgMap.setMzdq(null);
            mongoTemplate.save(slgMap);
        }
    }

    /**
     * 返回一个村庄的坐标
     * @return
     */
    public Integer zuobiao(){
        int index = RandomUtil.randomInt(100, 900);
        Query query = new Query();
        query.skip(index);
        query.limit(1);
        query.addCriteria(Criteria.where("dklx").is("村庄"));
        SlgMap slgMap = mongoTemplate.findOne(query, SlgMap.class);
        return slgMap.getId();
    }

    public List<SlgMap> ditu(Integer ym){
        Query query = new Query();
        query.limit(100);
        query.skip(100 * (ym - 1));
        return mongoTemplate.find(query, SlgMap.class);
    }

    public static void main(String[] args) {
        System.out.println(Shoujun.getKeyByValue(2));

    }
}
