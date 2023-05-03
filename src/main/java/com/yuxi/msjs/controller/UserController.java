package com.yuxi.msjs.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.yuxi.msjs.bean.entity.Fuwuqi;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.ZhangHao;
import com.yuxi.msjs.bean.vo.CityList;
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

    @GetMapping("/fwq")
    public HjArray fwqlb(){
        List<Fuwuqi> fuwuqis = userService.fwqLb();
        return arrayUtil.toArray(fuwuqis, fuwuqis.size(), Fuwuqi.class);
    }

    @GetMapping("/zhcheck")
    public Long zhCheck(String zh){
        return userService.zhcheck(zh);
    }
    /**
     * 注册
     * @param zh
     * @param mm
     * @param fwqId
     * @param fwq
     * @return
     */
    @GetMapping("/zhuce")
    public HjDict zhuce(String zh, String mm, Integer fwqId, String fwq){
        ZhangHao zhuce = userService.zhuce(zh, mm, fwqId, fwq);
        return arrayUtil.toDict(BeanUtil.beanToMap(zhuce));
    }

    /**
     * 登陆账号
     * @param zh
     * @param mm
     * @return
     */
    @GetMapping("/denglu")
    public HjDict denglu(String zh, String mm){
        ZhangHao zhuce = userService.login(zh, mm);
        return arrayUtil.toDict(BeanUtil.beanToMap(zhuce));
    }


    /**
     * 创建一个新的账号
     * @param name
     * @return
     */
    @GetMapping("/create")
    public String createUser(String name, String userId){
        User user = userService.insertUser(name, userId);
        return user.getUserId();
    }

    /**
     * 用户登录
     * @param userId
     * @return
     */
    @GetMapping("/login")
    public HjDict userInfo(String userId){
        User user = userService.findById(userId, true);
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
        List<UserCity> cityLists = cityService.userCitys(userId);
        return arrayUtil.toArray(cityLists, cityLists.size(),UserCity.class);
    }

    /**
     * 改名
     * @param userId
     * @param name
     * @return
     */
   @GetMapping("/gaiming")
    public HjDict gaiming(String userId, String name){
        User user = userService.gaiming(userId, name);
        return arrayUtil.toDict( BeanUtil.beanToMap(user));
    }


}
