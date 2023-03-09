package com.yuxi.msjs.service;

import com.yuxi.msjs.bean.entity.Lmxx;
import com.yuxi.msjs.bean.entity.Shijielt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/09 10:16 上午
 */
@Service
public class LiaotianService {

    @Autowired
    private MongoTemplate mongoTemplate;



    public List<Shijielt> shijieltList(){
        Query query = new Query();
        query.limit(20);
        query.limit(0);
        query.with(Sort.by(Sort.Order.desc("sjc")));
        return mongoTemplate.find(query, Shijielt.class);
    }

    public List<Shijielt> tianjia(Shijielt shijielt){
        mongoTemplate.save(shijielt);
        return shijieltList();
    }

    public List<Lmxx> lmxx(String lmId) {
        Query query = new Query(Criteria.where("lmId").is(lmId));
        query.limit(20);
        query.limit(0);
        query.with(Sort.by(Sort.Order.desc("sjc")));
        return mongoTemplate.find(query, Lmxx.class);
    }


    public List<Lmxx> tjlm(Lmxx lmxx){
        mongoTemplate.save(lmxx);
        return lmxx(lmxx.getLmId());
    }

}
