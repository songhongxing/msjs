package com.yuxi.msjs.controller;

import cn.hutool.json.JSONUtil;
import com.yuxi.msjs.bean.entity.UserDaoju;
import com.yuxi.msjs.service.DaojuService;
import com.yuxi.msjs.util.HjArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/03 11:31 上午
 */
@RestController
@RequestMapping("/daoju")
public class DaojuController extends BaseController {


    @Autowired
    private DaojuService daojuService;

    /**
     * 添加道具
     *
     * @param json
     * @author songhongxing
     * @date 2023/03/03 11:34 上午
     */
    @PostMapping("/create")
    public HjArray insert(String json) {
        UserDaoju userDaoju = JSONUtil.toBean(JSONUtil.parseObj(json), UserDaoju.class);
        List<UserDaoju> daojuList = daojuService.insert(userDaoju);
        return arrayUtil.toArray(daojuList, daojuList.size(), UserDaoju.class);
    }

    /**
     * 道具列表
     *
     * @param userId
     * @author songhongxing
     * @date 2023/03/03 11:45 上午
     */
    @GetMapping("/lb")
    public HjArray lb(String userId) {
        List<UserDaoju> daojuList = daojuService.lb(userId);
        return arrayUtil.toArray(daojuList, daojuList.size(), UserDaoju.class);
    }
}
