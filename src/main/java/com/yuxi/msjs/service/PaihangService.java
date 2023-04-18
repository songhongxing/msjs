package com.yuxi.msjs.service;

import com.yuxi.msjs.bean.entity.Meinv;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.Wujiang;
import com.yuxi.msjs.bean.vo.PhGuimo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜查询
 *
 * @author songhongxing
 * @date 2023/04/18 10:06 上午
 */
@Service
public class PaihangService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 规模排行榜,查询前100名
     *
     * @return
     */
    public List<User> guimo(Integer myts) {
        Query query = new Query();
        query.limit(myts);
        query.skip(0);
        query.with(Sort.by(Sort.Direction.DESC, "zgm"));
        return mongoTemplate.find(query, User.class);
    }

    /**
     * 统计规模
     */
    public void tjgm() {
        Query query;
        List<UserCity> userCities;
        Update update;
        List<User> userList = mongoTemplate.findAll(User.class);
        int zgm = 0;//总规模
        for (User user : userList) {
            zgm = 0;
            query = new Query(Criteria.where("userId").is(user.getUserId()));
            userCities = mongoTemplate.find(query, UserCity.class);
            for (UserCity userCity : userCities) {
                zgm += userCity.getZgm();
            }
            update = new Update();
            update.set("ccsl", userCities.size());
            update.set("zgm", zgm);
            mongoTemplate.updateFirst(query, update, User.class);
        }
    }

    /**
     * 统计前一天的前1000名到排行表
     */
    public void guimoph() {
        mongoTemplate.dropCollection(PhGuimo.class);
        List<User> users = guimo(1000);
        List<PhGuimo> phGuimos = new ArrayList<>();
        for (int i = 1; i <= users.size(); i++) {
            phGuimos.add(new PhGuimo(i,
                    users.get(i).getUserId(),
                    users.get(i).getName(),
                    users.get(i).getCcsl(),
                    users.get(i).getZgm()));
        }
        mongoTemplate.insertAll(phGuimos);
    }

    /**
     * 根据类型查询排行,wl,fy,sd,zl
     * @param lx
     * @return
     */
    public List<Wujiang> wjph(String lx){
        Query query = new Query();
        query.limit(100);
        query.skip(0);
        query.with(Sort.by(Sort.Direction.DESC, lx));
        return mongoTemplate.find(query, Wujiang.class);
    }

    /**
     * 根据类型查询排行,ly,sc,zl,cy,ml
     * @param lx
     * @return
     */
    public List<Meinv> mvph(String lx){
        Query query = new Query();
        query.limit(100);
        query.skip(0);
        query.with(Sort.by(Sort.Direction.DESC, lx));
        return mongoTemplate.find(query, Meinv.class);
    }


}
