package com.yuxi.msjs.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yuxi.msjs.bean.entity.Chuzheng;
import com.yuxi.msjs.bean.entity.Xtxx;
import com.yuxi.msjs.bean.entity.ZengYuan;
import com.yuxi.msjs.bean.entity.ZhanBao;
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

    @GetMapping("/insert")
    public void insert(){
        zhanDouService.insert();
    }

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

    /**
     * 战报列表
     * @param userId
     * @return
     */
    @GetMapping("/zblb")
    public HjArray zblb(String userId){
        List<ZhanBao> zhanBaos = zhanDouService.zblb(userId);
        return arrayUtil.toArray(zhanBaos, zhanBaos.size(), ZhanBao.class);
    }

    /**
     * 读取战报
     * @param zbId
     */
    @GetMapping("/zbyd")
    public void zbyd(String zbId){
        zhanDouService.zbyd(zbId);
    }

    /**
     * 警情列表
     * @param userId
     * @param lx
     * @return
     */
    @GetMapping("/jqlb")
    public HjArray jqlb(String userId, String lx){
        List<Chuzheng> jqlb = zhanDouService.dixi(userId, lx);
        return arrayUtil.toArray(jqlb, jqlb.size(), Chuzheng.class);
    }

    /**
     * 增援我的
     * @param userId
     * @return
     */
    @GetMapping("/zywd")
    public HjArray zywd(String userId){
        List<ZengYuan> zywd = zhanDouService.zywd(userId);
        return arrayUtil.toArray(zywd, zywd.size(), ZengYuan.class);
    }

    @GetMapping("/wzyd")
    public HjArray wzyd(String userId){
        List<ZengYuan> zywd = zhanDouService.wzyd(userId);
        return arrayUtil.toArray(zywd, zywd.size(), ZengYuan.class);
    }

    /**
     * 增援召回
     * @param zyId
     * @return
     */
    @GetMapping("/bdzh")
    public void bdzh(String zyId){
        zhanDouService.bdzh(zyId);
    }



}
