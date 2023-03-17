package com.yuxi.msjs.service;

import cn.hutool.core.collection.CollUtil;
import com.yuxi.msjs.bean.entity.LianmShenq;
import com.yuxi.msjs.bean.entity.Lianmeng;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.vo.LianmengList;
import com.yuxi.msjs.bean.vo.Lmcy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/01 5:39 下午
 */
@Service
public class LianmengService {
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 创建联盟
     * @param userId
     * @param lmmc
     * @author songhongxing
     * @date 2023/03/01 5:41 下午
     */
    public List<Lmcy> cjlm(String userId,String lmmc){
        Lianmeng lianmeng = new Lianmeng();
        lianmeng.setLmmc(lmmc);
        lianmeng.setLmId(UUID.randomUUID().toString().replaceAll("-", ""));
        lianmeng.setLmrl(30);
        lianmeng.setLmrs(1);
        Query query = new Query(Criteria.where("userId").is(userId));
        mongoTemplate.save(lianmeng);
        Update update = new Update();
        update.set("lmId", lianmeng.getLmId());
        update.set("lmgz", 2);
        update.set("lmgzZw", "盟主");
        mongoTemplate.updateFirst(query, update, "user");
        return lmcyList(lianmeng.getLmId());
    }

    public List<Lmcy> lmcyList(String lmId){
        List<Lmcy> lmcyList = new ArrayList<>();
        Query query = new Query(Criteria.where("lmId").is(lmId));
        query.with(Sort.by(Sort.Order.desc("lmgz")));
        List<User> users = mongoTemplate.find(query, User.class);
        Lmcy lmcy;
        for(User user : users){
            lmcy = new Lmcy();
            lmcy.setUserId(user.getUserId());
            lmcy.setName(user.getName());
            lmcy.setLmgx(user.getLmgx());
            lmcy.setJunxian(user.getJunxian());
            lmcy.setLmgzZw(user.getLmgzZw());
            lmcyList.add(lmcy);
        }
        return lmcyList;
    }

    /**
     * 联盟列表
     * @author songhongxing
     * @date 2023/03/02 10:15 上午
     */
    public List<LianmengList> lmlb(){
//        List<Lianmeng> lianmengList = mongoTemplate.findAll(Lianmeng.class);
        List<Lianmeng> lianmengList = mongoTemplate.findAll(Lianmeng.class, "lianmeng");
        LianmengList lianmeng;
        List<LianmengList> result = new ArrayList<>();
        Query query;
        User one;
        for (Lianmeng bean : lianmengList) {
            lianmeng = new LianmengList();
            lianmeng.setLmId(bean.getLmId());
            query = new Query(Criteria.where("lmId").is(bean.getLmId()).and("lmgz").is(2));
            one = mongoTemplate.findOne(query, User.class);
            lianmeng.setMz(one.getName());
            lianmeng.setLmmc(bean.getLmmc());
            lianmeng.setLmdj(bean.getLmdj());
            lianmeng.setLmrs(bean.getLmrs());
            lianmeng.setLmrl(bean.getLmrl());
            result.add(lianmeng);
        }
        return result;
    }

    public Lianmeng info(String lmId) {
        Query lm = new Query(Criteria.where("lmId").is(lmId));
        return mongoTemplate.findOne(lm, Lianmeng.class);
    }

    /**
     * 捐献数量
     *
     * @param lmId
     * @param userId
     * @param jxlx   捐献类型
     * @param jxsl   数量
     */
    public Lianmeng jxfl(String lmId, String userId, String jxlx, Integer jxsl) {
        Query userQuery = new Query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(userQuery, User.class);
        Query query = new Query(Criteria.where("lmId").is(lmId));
        Lianmeng lianmeng = mongoTemplate.findOne(query, Lianmeng.class);
        //俘虏数量校验
        if (user.getFlsl() > jxsl) {
            Update update = getUpdate(lianmeng, jxlx, jxsl);
            mongoTemplate.updateFirst(query, update, Lianmeng.class);
            lianmeng = mongoTemplate.findOne(query, Lianmeng.class);
            Update userUpdate = new Update();
            userUpdate.set("flsl", user.getFlsl() - jxsl);
            userUpdate.set("lmgx", Integer.valueOf(jxsl / 10));
            mongoTemplate.updateFirst(userQuery, userUpdate, User.class);
        }
        return lianmeng;
    }

    private Update getUpdate(Lianmeng lianmeng, String jxlx, Integer jxsl) {
        Update update = new Update();
        int dqjy;//当前经验
        int dqdj;//当前等级
        int sxjy;//所需经验
        if ("资源生产".equals(jxlx)) {
            dqjy = lianmeng.getZyscjy() + jxsl;
            dqdj = lianmeng.getZyscdj();
            sxjy = lianmeng.getZyscsjsx();
            while (dqjy > sxjy) {
                dqjy = dqjy - sxjy;
                dqdj += 1;
                sxjy = 10000 + (dqdj - 1) * (dqdj - 1) * 27500;
            }
            update.set("zyscjy", dqjy);
            update.set("zyscsjsx", sxjy);
            update.set("zyscdj", dqdj);
        } else if ("行军速度".equals(jxlx)) {
            dqjy = lianmeng.getBdsdjy() + jxsl;
            dqdj = lianmeng.getBdsddj();
            sxjy = lianmeng.getBdsdsjsx();
            while (dqjy > sxjy) {
                dqjy = dqjy - sxjy;
                dqdj += 1;
                sxjy = 10000 + (dqdj - 1) * (dqdj - 1) * 27500;
            }
            update.set("bdsdjy", dqjy);
            update.set("bdsdsjsx", sxjy);
            update.set("bdsddj", dqdj);
        } else if ("招兵速度".equals(jxlx)) {
            dqjy = lianmeng.getZbsdjy() + jxsl;
            dqdj = lianmeng.getZbsddj();
            sxjy = lianmeng.getZbsdsjsx();
            while (dqjy > sxjy) {
                dqjy = dqjy - sxjy;
                dqdj += 1;
                sxjy = 10000 + (dqdj - 1) * (dqdj - 1) * 27500;
            }
            update.set("zbsdjy", dqjy);
            update.set("zbsdsjsx", sxjy);
            update.set("zbsddj", dqdj);
        } else if ("攻击加强".equals(jxlx)) {
            dqjy = lianmeng.getBdgjjy() + jxsl;
            dqdj = lianmeng.getBdgjdj();
            sxjy = lianmeng.getBdgjsjsx();
            while (dqjy > sxjy) {
                dqjy = dqjy - sxjy;
                dqdj += 1;
                sxjy = 10000 + (dqdj - 1) * (dqdj - 1) * 27500;
            }
            update.set("bdgjjy", dqjy);
            update.set("bdgjsjsx", sxjy);
            update.set("bdgjdj", dqdj);
        } else if ("防守加强".equals(jxlx)) {
            dqjy = lianmeng.getBdfyjy() + jxsl;
            dqdj = lianmeng.getBdfydj();
            sxjy = lianmeng.getBdfysjsx();
            while (dqjy > sxjy) {
                dqjy = dqjy - sxjy;
                dqdj += 1;
                sxjy = 10000 + (dqdj - 1) * (dqdj - 1) * 27500;
            }
            update.set("bdfyjy", dqjy);
            update.set("bdfysjsx", sxjy);
            update.set("bdfydj", dqdj);
        } else if ("联盟等级".equals(jxlx)) {
            dqjy = lianmeng.getLmjy() + jxsl;
            dqdj = lianmeng.getLmdj();
            sxjy = lianmeng.getLmsjsx();
            while (dqjy > sxjy) {
                dqjy = dqjy - sxjy;
                dqdj += 1;
                sxjy = 10000 + (dqdj - 1) * (dqdj - 1) * 27500;
            }
            update.set("lmjy", dqjy);
            update.set("lmsjsx", sxjy);
            update.set("lmdj", dqdj);
            update.set("lmrl", 30 + dqdj - 1);
        }
        return update;
    }

    public Lianmeng fbgg(String lmId, String userId, String lmgg) {
        Query userQuery = new Query(Criteria.where("userId").is(userId));
        Query lmQuery = new Query(Criteria.where("lmId").is(lmId));
        User user = mongoTemplate.findOne(userQuery, User.class);
        if (user.getLmgz() == 1 || user.getLmgz() == 2) {
            Update update = new Update();
            Lianmeng lianmeng = mongoTemplate.findOne(lmQuery, Lianmeng.class);
            update.set("lmgg", lmgg);
            mongoTemplate.updateFirst(lmQuery, update, Lianmeng.class);
        }
        return mongoTemplate.findOne(lmQuery, Lianmeng.class);
    }

    public void jrlm(String lmId, String userId) {
        LianmShenq lianmShenq = new LianmShenq();
        lianmShenq.setLmId(lmId);
        lianmShenq.setUserId(userId);
        Query query = new Query(Criteria.where("lmId").is(lmId).and("userId").is(userId));
        LianmShenq one = mongoTemplate.findOne(query, LianmShenq.class);
        if (one == null) {
            mongoTemplate.save(lianmShenq);
        }
    }

    public List<User> lmsq(String lmId) {
        List<User> userList = new ArrayList<>();
        Query query = new Query(Criteria.where("lmId").is(lmId));
        List<LianmShenq> lianmShenqs = mongoTemplate.find(query, LianmShenq.class);
        if (CollUtil.isNotEmpty(lianmShenqs)) {
            Query userQuery = null;
            for (LianmShenq lianmShenq : lianmShenqs) {
                userQuery = new Query(Criteria.where("userId").is(lianmShenq.getUserId()));
                userList.add(mongoTemplate.findOne(userQuery, User.class));
            }
        }
        return userList;
    }

    /**
     * 审批申请,type = 1,同意,type = -1,拒绝
     *
     * @param lmId
     * @param userId
     * @param type
     */
    public List<User> sqsp(String lmId, String userId, Integer type) {
        Query query = new Query(Criteria.where("lmId").is(lmId).and("userId").is(userId));
        LianmShenq one = mongoTemplate.findOne(query, LianmShenq.class);
        if(type == 1){
            Query userQuery = new Query(Criteria.where("userId").is(lmId));
            Update update = new Update();
            update.set("lmId", lmId);
            mongoTemplate.updateFirst(userQuery, update, User.class);
        }
        mongoTemplate.remove(query, LianmShenq.class);
        query = new Query(Criteria.where("lmId").is(lmId));
        return lmsq(lmId);
    }


}
