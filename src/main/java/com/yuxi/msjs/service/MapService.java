package com.yuxi.msjs.service;

import com.yuxi.msjs.bean.entity.SlgMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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

    public void insert(){
        SlgMap slgMap = new SlgMap();
        slgMap.setId(2);
        slgMap.setDklx("ssss");
        mongoTemplate.save(slgMap);
    }
}
