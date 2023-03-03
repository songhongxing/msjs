package com.yuxi.msjs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.ZhengBing;
import com.yuxi.msjs.service.CityService;
import com.yuxi.msjs.util.HjArray;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 城市逻辑
 *
 * @author songhongxing
 * @date 2023/02/27 10:50 上午
 */
@CrossOrigin
@RestController
@RequestMapping("/city")
public class CityController extends BaseController{

    @Autowired
    private CityService cityService;

    @GetMapping("/info")
    public HjDict userCity(String cityId){
        UserCity userCity = cityService.userCity(cityId);
        return arrayUtil.toDict(BeanUtil.beanToMap(userCity));
    }

    @GetMapping("/jz/sj")
    public HjArray jianzhusj(String cityId, String jzName, Integer jzdj, Integer sjsj){
        List<HomeUp> jzshengji = cityService.jzshengji(cityId, jzName, jzdj, sjsj);
        return arrayUtil.toArray(jzshengji, jzshengji.size(), HomeUp.class);
    }

    @GetMapping("/jz/sjwc")
    public HjArray jianzhusj(String cityId, String jzName){
        List<HomeUp> sjwc = cityService.sjwc(cityId, jzName);
        return  arrayUtil.toArray(sjwc, sjwc.size(), HomeUp.class);
    }
    @GetMapping("/jzdl")
    public HjArray jianzhusj(String cityId){
        List<HomeUp> sjwc = cityService.jzdl(cityId);
        return  arrayUtil.toArray(sjwc, sjwc.size(), HomeUp.class);
    }

    @GetMapping("/zhengbing")
    public HjArray zhengbing(String cityId, String bz, Integer sl, Integer dghs){
        List<ZhengBing> zhengbing = cityService.zhengbing(cityId, bz, sl, dghs);
        return arrayUtil.toArray(zhengbing, zhengbing.size(), ZhengBing.class);
    }
    @GetMapping("/zblb")
    public HjArray zhengbingduilie(String cityId){
        List<ZhengBing> zhengbing = cityService.zbdl(cityId);
        return arrayUtil.toArray(zhengbing, zhengbing.size(), ZhengBing.class);
    }
}
