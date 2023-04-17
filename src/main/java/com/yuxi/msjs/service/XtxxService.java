package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxi.msjs.bean.entity.Lmsq;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserDaoju;
import com.yuxi.msjs.bean.entity.Xtxx;
import com.yuxi.msjs.bean.entity.Ydxx;
import com.yuxi.msjs.bean.entity.ZhanBao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private DaojuService daojuService;

    public void create(Xtxx xtxx) {
        xtxx.setXxId(UUID.randomUUID().toString().replaceAll("-", ""));
        mongoTemplate.save(xtxx);
    }

    public List<Xtxx> xxlb(String userId) {
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

    public List<Xtxx> dqxx(String userId, String xxId) {
        Ydxx ydxx = new Ydxx();
        ydxx.setUserId(userId);
        ydxx.setXxId(xxId);
        mongoTemplate.save(ydxx);
        //将消息内容添加到用户的背包中
        Query query = new Query(Criteria.where("xxId").is(xxId));
        Xtxx xtxx = mongoTemplate.findOne(query, Xtxx.class);
        List<UserDaoju> userDaojus = new ArrayList<>();
        if (StrUtil.isNotEmpty(xtxx.getJl1())) {
            userDaojus.add(new UserDaoju(userId, xtxx.getJl1().split("\\*")[0], Integer.valueOf(xtxx.getJl1().split("\\*")[1])));
        }
        if (StrUtil.isNotEmpty(xtxx.getJl2())) {
            userDaojus.add(new UserDaoju(userId, xtxx.getJl2().split("\\*")[0], Integer.valueOf(xtxx.getJl2().split("\\*")[1])));
        }
        if (StrUtil.isNotEmpty(xtxx.getJl3())) {
            userDaojus.add(new UserDaoju(userId, xtxx.getJl3().split("\\*")[0], Integer.valueOf(xtxx.getJl3().split("\\*")[1])));
        }
        if (StrUtil.isNotEmpty(xtxx.getJl4())) {
            userDaojus.add(new UserDaoju(userId, xtxx.getJl4().split("\\*")[0], Integer.valueOf(xtxx.getJl4().split("\\*")[1])));
        }
        if (StrUtil.isNotEmpty(xtxx.getJl5())) {
            userDaojus.add(new UserDaoju(userId, xtxx.getJl5().split("\\*")[0], Integer.valueOf(xtxx.getJl5().split("\\*")[1])));
        }
        daojuService.insert(userDaojus);
        return xxlb(userId);
    }

    /**
     * 未读消息
     *
     * @param userId
     * @return
     */
    public Map<String, Long> wdxx(String userId) {
        Map<String, Long> map = new HashMap<>();
        Query query = new Query(Criteria.where("userId").is(userId));
        long count = mongoTemplate.count(query, ZhanBao.class);
        map.put("战报", count);
        List<Xtxx> xtxxes = mongoTemplate.findAll(Xtxx.class);
        List<Ydxx> ydxxes = mongoTemplate.find(query, Ydxx.class);
        if (CollUtil.isNotEmpty(ydxxes)) {
            List<String> collect = ydxxes.stream().map(Ydxx::getXxId).collect(Collectors.toList());
            long xtxxsl = 0;
            for (Xtxx xtxx : xtxxes) {
                if (!collect.contains(xtxx.getXxId())) {
                    xtxxsl += 1;
                }
            }
            map.put("系统", xtxxsl);
        } else {
            map.put("系统", (long) xtxxes.size());
        }
        map.put("入盟申请", 0l);
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null && user.getLmgz() > 0) {
            query = new Query(Criteria.where("lmId").is(user.getLmId()));
            long lmsq = mongoTemplate.count(query, Lmsq.class);
            map.put("入盟申请", lmsq);
        }
        return map;
    }
}
