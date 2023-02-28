package com.yuxi.msjs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.service.CityService;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String jianzhusj(String cityId, String jzName, Integer jzdj, Integer sjsj){
        cityService.jzshengji(cityId, jzName, jzdj, sjsj);
        return "添加成功";
    }

    @GetMapping("/jz/sjwc")
    public String jianzhusj(String cityId, String jzName){
        cityService.sjwc(cityId, jzName);
        return "升级完成";
    }
}
