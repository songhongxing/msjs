package com.yuxi.msjs.controller;


import cn.hutool.core.bean.BeanUtil;
import com.yuxi.msjs.bean.entity.User;
import com.yuxi.msjs.service.UserService;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userId
     * @return
     */
    public HjDict login(String userId){
        return null;
    }

    /**
     * 创建一个新的账号
     * @param name
     * @return
     */
    public HjDict createUser(String name){
        User user = userService.insertUser(name);
        return arrayUtil.toDict(BeanUtil.beanToMap(user));
    }


}
