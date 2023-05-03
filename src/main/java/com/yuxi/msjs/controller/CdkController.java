package com.yuxi.msjs.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import com.yuxi.msjs.bean.entity.CDK;
import com.yuxi.msjs.bean.entity.UserDaoju;
import com.yuxi.msjs.bean.entity.UserZb;
import com.yuxi.msjs.service.CdkService;
import com.yuxi.msjs.service.DaojuService;
import com.yuxi.msjs.service.ZhuangbServicce;
import com.yuxi.msjs.util.HjArray;
import com.yuxi.msjs.util.HjDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * cdk处理
 *
 * @author songhongxing
 * @date 2023/03/03 11:31 上午
 */
@RestController
@RequestMapping("/cdk")
public class CdkController extends BaseController {


    @Autowired
    private CdkService cdkService;


    /**
     * 使用cdk
     *
     * @param cdk
     * @param userId
     * @author songhongxing
     * @date 2023/03/03 11:34 上午
     */
    @GetMapping("/use")
    public HjArray use(String cdk,String userId) {
        List<UserDaoju> sycdk = cdkService.sycdk(cdk, userId);
        return arrayUtil.toArray(sycdk, sycdk.size(), UserDaoju.class);
    }

    @GetMapping("/check")
    public Integer check(String cdk,String userId){
       return cdkService.ckeckcdk(cdk, userId);
    }

    @PostMapping("/insert")
    public void insert(CDK cdk){
        cdkService.insert(cdk);
    }

}
