package com.yuxi.msjs.controller;

import cn.hutool.core.date.DateUtil;
import com.yuxi.msjs.bean.entity.Lmxx;
import com.yuxi.msjs.bean.entity.Shijielt;
import com.yuxi.msjs.service.LiaotianService;
import com.yuxi.msjs.util.HjArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/09 10:14 上午
 */
@RestController
@RequestMapping("/liaotian")
public class LiaotianController extends BaseController{

    @Autowired
    private LiaotianService liaotianService;


    @GetMapping("/sjlb")
    private HjArray ltlb(){
        List<Shijielt> shijielts = liaotianService.shijieltList();
        return arrayUtil.toArray(shijielts, shijielts.size(), Shijielt.class);
    }

    @GetMapping("/tj")
    private HjArray tj(String userId, String name, String nr){
//        Shijielt shijielt = JSONUtil.toBean(JSONUtil.parseObj(json), Shijielt.class);
        Shijielt shijielt = new Shijielt();
        shijielt.setUserId(userId);
        shijielt.setName(name);
        shijielt.setNr(nr);
        shijielt.setSj(DateUtil.now());
        shijielt.setSjc((int)DateUtil.currentSeconds());
        List<Shijielt> shijielts = liaotianService.tianjia(shijielt);
        return arrayUtil.toArray(shijielts, shijielts.size(), Shijielt.class);
    }

    @GetMapping("/lmlb")
    private HjArray lmlb(String lmId){
        List<Lmxx> lmxxs = liaotianService.lmxx(lmId);
        return arrayUtil.toArray(lmxxs, lmxxs.size(), Lmxx.class);
    }

    @GetMapping("/tjlm")
    private HjArray tj(String lmId, String userId, String name, String nr){
        Lmxx lmxx = new Lmxx();
        lmxx.setLmId(lmId);
        lmxx.setUserId(userId);
        lmxx.setName(name);
        lmxx.setNr(nr);
        lmxx.setSj(DateUtil.now());
        lmxx.setSjc((int)DateUtil.currentSeconds());
        List<Lmxx> tjlm = liaotianService.tjlm(lmxx);
        return arrayUtil.toArray(tjlm, tjlm.size(), Lmxx.class);
    }
}
