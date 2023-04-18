package com.yuxi.msjs.controller;

import com.yuxi.msjs.bean.entity.Meinv;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.Wujiang;
import com.yuxi.msjs.service.PaihangService;
import com.yuxi.msjs.util.HjArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 排行榜
 *
 * @author songhongxing
 * @date 2023/02/27 10:50 上午
 */
@CrossOrigin
@RestController
@RequestMapping("/paihang")
public class PaihangController extends BaseController{

    @Autowired
    private PaihangService paihangService;

    /**
     * 规模排行
     * @return
     */
    @GetMapping("guimo")
    public HjArray guimo(){
        List<User> guimo = paihangService.guimo(100);
        return arrayUtil.toArray(guimo, guimo.size(), User.class);
    }

    @GetMapping("wujiang")
    public HjArray wujiang(String lx){
        List<Wujiang> wjph = paihangService.wjph(lx);
        return arrayUtil.toArray(wjph, wjph.size(), Wujiang.class);
    }
    @GetMapping("meinv")
    public HjArray meinv(String lx){
        List<Meinv> mvph = paihangService.mvph(lx);
        return arrayUtil.toArray(mvph, mvph.size(), Meinv.class);
    }


}
