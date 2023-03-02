package com.yuxi.msjs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.vo.LianmengList;
import com.yuxi.msjs.bean.vo.Lmcy;
import com.yuxi.msjs.service.CityService;
import com.yuxi.msjs.service.LianmengService;
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
@RequestMapping("/lianmeng")
public class LianmengController extends BaseController{

    @Autowired
    private LianmengService lianmengService;

    /**
     * 创建联盟后  返回联盟成员列表
     * @param userId
     * @param lmmc
     * @author songhongxing
     * @date 2023/03/01 5:58 下午
     */
    @GetMapping("/create")
    public HjArray userCity(String userId, String lmmc){
        List<Lmcy> lmcyList = lianmengService.cjlm(userId, lmmc);
        return arrayUtil.toArray(lmcyList, lmcyList.size(), Lmcy.class);
    }

    /**
     * 联盟成员列表
     * @param lmId
     * @return
     */
    @GetMapping("/cylb")
    public HjArray lb(String lmId){
        List<Lmcy> lmcyList = lianmengService.lmcyList(lmId);
        return arrayUtil.toArray(lmcyList, lmcyList.size(), Lmcy.class);
    }

    /**
     * 联盟列表
     * @author songhongxing
     * @date 2023/03/02 10:25 上午
     */
    @GetMapping("/lb")
    public HjArray lmlb(){
        List<LianmengList> lmlb = lianmengService.lmlb();
        return arrayUtil.toArray(lmlb, lmlb.size(), LianmengList.class);
    }

}
