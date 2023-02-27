package com.yuxi.msjs.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.service.CityService;
import com.yuxi.msjs.service.UserService;
import com.yuxi.msjs.util.HjArray;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;

    /**
     * 创建一个新的账号
     * @param name
     * @return
     */
    @GetMapping("/create")
    public String createUser(String name){
        User user = userService.insertUser(name);
        return user.getUserId();
    }

    /**
     * 用户登录
     * @param userId
     * @return
     */
    @GetMapping("/login")
    public HjDict userInfo(String userId){
        User user = userService.findById(userId);
        user.setDlsj(DateUtil.now());
        user.setSjhms(System.currentTimeMillis());
        Map<String, Object> userMap = BeanUtil.beanToMap(user);
        return arrayUtil.toDict(userMap);
    }

    /**
     * 用户城池列表
     * @param userId
     * @return
     */
    @GetMapping("/citys")
    public HjArray userCitys(String userId){
        List<UserCity> userCities = cityService.userCitys(userId);
        return arrayUtil.toArray(userCities, userCities.size(),UserCity.class);
    }

}
