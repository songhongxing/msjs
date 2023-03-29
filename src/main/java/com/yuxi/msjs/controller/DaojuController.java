package com.yuxi.msjs.controller;

import cn.hutool.json.JSONUtil;
import com.yuxi.msjs.bean.entity.UserDaoju;
import com.yuxi.msjs.bean.entity.UserZb;
import com.yuxi.msjs.service.DaojuService;
import com.yuxi.msjs.service.ZhuangbServicce;
import com.yuxi.msjs.util.HjArray;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/03 11:31 上午
 */
@RestController
@RequestMapping("/daoju")
public class DaojuController extends BaseController {


    @Autowired
    private DaojuService daojuService;
    @Autowired
    private ZhuangbServicce zhuangbServicce;

    /**
     * 添加道具
     *
     * @param json
     * @author songhongxing
     * @date 2023/03/03 11:34 上午
     */
    @PostMapping("/create")
    public HjArray insert(String json) {
        UserDaoju userDaoju = JSONUtil.toBean(JSONUtil.parseObj(json), UserDaoju.class);
        List<UserDaoju> daojuList = daojuService.insert(userDaoju);
        return arrayUtil.toArray(daojuList, daojuList.size(), UserDaoju.class);
    }

    /**
     * 道具列表
     *
     * @param userId
     * @author songhongxing
     * @date 2023/03/03 11:45 上午
     */
    @GetMapping("/lb")
    public HjArray lb(String userId) {
        List<UserDaoju> daojuList = daojuService.lb(userId);
        return arrayUtil.toArray(daojuList, daojuList.size(), UserDaoju.class);
    }

    @GetMapping("/sl")
    public HjDict djsl(String userId) {
        Map<String, Object> djsl = daojuService.djsl(userId);
        return arrayUtil.toDict(djsl);
    }

    /**
     * 使用道具
     * @param userId
     * @param name
     * @return
     */
    @GetMapping("/use")
    public HjDict usedj(String userId, String name, Integer sl) {
        daojuService.djsy(userId, name, sl);
        Map<String, Object> djsl = daojuService.djsl(userId);
        return arrayUtil.toDict(djsl);
    }

    /**
     * 添加武将装备
     * @param userId
     * @param cityId
     * @param wjId
     * @param zbId
     * @return
     */
    @GetMapping("/tjwjzb")
    public HjDict tjwjzb(String userId, String cityId, String wjId, String zbId){
        Map<String, Object> tjwjzb = zhuangbServicce.tjwjzb(userId, cityId, wjId, zbId);
        return arrayUtil.toDict(tjwjzb);
    }

    @GetMapping("/wjzblb")
    public HjDict wjzblb(String userId, String cityId, String wjId){
        Map<String, Object> wjzb = zhuangbServicce.wjzb(userId, cityId, wjId);
        return arrayUtil.toDict(wjzb);
    }

    /**
     * 获取装备
     * @param userId
     * @param name
     */
    @GetMapping("/hqzb")
    public String hqzb(String userId,  String name){
        List<UserZb> userZbs = zhuangbServicce.insert(userId, name);
        return name;
    }

    /**
     * 装备列表
     * @param userId
     */
 @GetMapping("/zblb")
    public HjArray zblb(String userId){
        List<UserZb> userZbs = zhuangbServicce.find(userId);
     return arrayUtil.toArray(userZbs, userZbs.size(), UserZb.class);
    }


}
