package com.yuxi.msjs.controller;

import cn.hutool.core.collection.ListUtil;
import com.yuxi.msjs.bean.entity.Xtxx;
import com.yuxi.msjs.service.XtxxService;
import com.yuxi.msjs.util.HjArray;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/04 10:32 上午
 */
@RestController
@RequestMapping("/xtxx")
public class XxController extends BaseController {

    @Autowired
    private XtxxService xtxxService;

    @PostMapping("/create")
    public void insert(@RequestBody Xtxx xtxx, String keyName) {
        if (keyName.equals("shxzfy")) {
            xtxxService.create(xtxx);
        }
    }

    @GetMapping("/xxlb")
    public HjArray xxlb(String userId) {
        List<Xtxx> xxlb = xtxxService.xxlb(userId);
        xxlb = ListUtil.reverse(xxlb);
        return arrayUtil.toArray(xxlb, xxlb.size(), Xtxx.class);
    }

    @GetMapping("/ydxx")
    public HjArray ydxx(String userId, String xxId) {
        List<Xtxx> xxlb = xtxxService.dqxx(userId, xxId);
        xxlb = ListUtil.reverse(xxlb);
        return arrayUtil.toArray(xxlb, xxlb.size(), Xtxx.class);
    }

    @GetMapping("/wdxx")
    public HjDict wdxx(String userId){
        Map<String, Long> wdxx = xtxxService.wdxx(userId);
        return arrayUtil.toDict(wdxx);
    }


}
