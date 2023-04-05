package com.yuxi.msjs.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yuxi.msjs.bean.entity.Chuzheng;
import com.yuxi.msjs.service.ZhanDouService;
import com.yuxi.msjs.util.HjArray;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 战斗逻辑
 *
 * @author songhongxing
 * @date 2023/04/05 12:30 下午
 */
@RestController
@RequestMapping("/zhandou")
public class ZhanDouController extends BaseController{

    @Autowired
    private ZhanDouService zhanDouService;


    @PostMapping("/chuzheng")
    public String chuzheng(String json){
        Chuzheng chuzheng = JSONUtil.toBean((JSONObject) JSONUtil.parse(json), Chuzheng.class);
        boolean flag = zhanDouService.chuzheng(chuzheng);
        return flag ? "出征成功" : "兵力不足";
    }

    /**
     * 查询三种军情的数量
     * @param userId
     * @return
     */
    @GetMapping("/junqing")
    public HjDict junqing(String userId){
        Map junqing = zhanDouService.junqing(userId);
        return arrayUtil.toDict(junqing);
    }

    @GetMapping("/jqlb")
    public HjArray jqlb(String userId, String lx){
        List<Chuzheng> jqlb = zhanDouService.dixi(userId, lx);
        return arrayUtil.toArray(jqlb, jqlb.size(), Chuzheng.class);
    }


}
