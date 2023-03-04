package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import com.yuxi.msjs.bean.Xtxx;
import com.yuxi.msjs.bean.Ydxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author songhongxing
 * @date 2023/03/04 10:34 上午
 */
@Service
public class XtxxService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void create(Xtxx xtxx){
        xtxx.setXxId(UUID.randomUUID().toString().replaceAll("-", ""));
        mongoTemplate.save(xtxx);
    }

    public List<Xtxx> xxlb(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        List<Ydxx> ydxxes = mongoTemplate.find(query, Ydxx.class);
        List<Xtxx> xtxxes = mongoTemplate.findAll(Xtxx.class);
        if(CollUtil.isNotEmpty(ydxxes)){
            List<String> xxIds = ydxxes.stream().map(Ydxx::getXxId).collect(Collectors.toList());
            for(Xtxx xtxx : xtxxes){
                if(xxIds.contains(xtxx.getXxId())){
                    xtxx.setYd(1);
                }
            }
        }

        return xtxxes;
    }

    public List<Xtxx> dqxx(String userId, String xxId){
        Ydxx ydxx = new Ydxx();
        ydxx.setUserId(userId);
        ydxx.setXxId(xxId);
        mongoTemplate.save(ydxx);
        //TODO 将消息内容添加到用户的背包中
        return xxlb(userId);
    }
}
