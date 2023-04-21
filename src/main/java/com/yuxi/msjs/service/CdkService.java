package com.yuxi.msjs.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxi.msjs.bean.entity.CDK;
import com.yuxi.msjs.bean.entity.UserCdk;
import com.yuxi.msjs.bean.entity.UserDaoju;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * cdk相关
 *
 * @author songhongxing
 * @date 2023/04/21 11:35 上午
 */
@Service
public class CdkService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DaojuService daojuService;

    /**
     * 使用cdk
     * @param cdk
     * @param userId
     */
    public List<UserDaoju> sycdk(String cdk, String userId){
        List<UserDaoju> list = new ArrayList<>();
        Query query = new Query(Criteria.where("cdk").is(cdk));
        CDK bean = mongoTemplate.findOne(query, CDK.class);
        if (StrUtil.isNotBlank(bean.getJl1())) {
            list.add(new UserDaoju(userId, bean.getJl1().split("\\*")[0], Integer.valueOf(bean.getJl1().split("\\*")[1])));
        }
        if (StrUtil.isNotBlank(bean.getJl2())) {
            list.add(new UserDaoju(userId, bean.getJl2().split("\\*")[0], Integer.valueOf(bean.getJl2().split("\\*")[1])));
        }
        if (StrUtil.isNotBlank(bean.getJl3())) {
            list.add(new UserDaoju(userId, bean.getJl3().split("\\*")[0], Integer.valueOf(bean.getJl3().split("\\*")[1])));
        }
        if (StrUtil.isNotBlank(bean.getJl4())) {
            list.add(new UserDaoju(userId, bean.getJl4().split("\\*")[0], Integer.valueOf(bean.getJl4().split("\\*")[1])));
        }
        if (StrUtil.isNotBlank(bean.getJl5())) {
            list.add(new UserDaoju(userId, bean.getJl5().split("\\*")[0], Integer.valueOf(bean.getJl5().split("\\*")[1])));
        }
        daojuService.insert(list);
        return list;
    }

    /**
     * 检测cdk是否用过
     * @param cdk
     * @param userId
     */
    public Integer ckeckcdk(String cdk, String userId){
        Query query = new Query(Criteria.where("cdk").is(cdk).and("userId").is(userId));
        UserCdk one = mongoTemplate.findOne(query, UserCdk.class);
        return one == null ? 0 : 1;
    }

    /**
     * 删除过期的cdk
     */
    public void gqcdk(){
        Query query = new Query(Criteria.where("gqsj").lte(DateUtil.currentSeconds()));
        mongoTemplate.remove(query, CDK.class);
    }
}
