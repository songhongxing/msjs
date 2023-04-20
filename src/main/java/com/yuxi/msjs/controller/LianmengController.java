package com.yuxi.msjs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.Jxfl;
import com.yuxi.msjs.bean.entity.Lianmeng;
import com.yuxi.msjs.bean.entity.User;
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

    @GetMapping("/info")
    public HjDict info(String lmId){
        Lianmeng info = lianmengService.info(lmId);
        return arrayUtil.toDict(BeanUtil.beanToMap(info));
    }

    /**
     * 捐献俘虏,返回联盟信息
     * @param lmId
     * @param userId
     * @param jxlx
     * @param jxsl
     * @return
     */
    @GetMapping("/jxfl")
    public HjDict jxfl(String lmId, String userId, String jxlx, Integer jxsl){
        Lianmeng jxfl = lianmengService.jxfl(lmId, userId, jxlx, jxsl);
        return arrayUtil.toDict(BeanUtil.beanToMap(jxfl));
    }

    /**
     * 捐献俘虏日志
     * @param lmId
     * @return
     */
    @GetMapping("/jxflrz")
    public HjArray jxflrz(String lmId){
        List<Jxfl> jxflrz = lianmengService.jxflrz(lmId);
        return arrayUtil.toArray(jxflrz, jxflrz.size(), Jxfl.class);
    }

    /**
     * 联盟公告发布
     * @param lmId
     * @param userId
     * @param lmgg
     * @return
     */
    @GetMapping("/lmgg")
    public HjDict fbgg(String lmId, String userId, String lmgg){
        Lianmeng lianmeng = lianmengService.fbgg(lmId, userId, lmgg);
        return arrayUtil.toDict(BeanUtil.beanToMap(lianmeng));
    }

    /**
     * 加入联盟申请
     * @param lmId
     * @param userId
     */
    @GetMapping("/jrlm")
    public void jrlm(String lmId, String userId){
        lianmengService.jrlm(lmId, userId);
    }

    /**
     * 联盟申请列表
     * @param lmId
     * @return
     */
    @GetMapping("/lmsq")
    public HjArray lmsq(String lmId){
        List<User> lmsq = lianmengService.lmsq(lmId);
        return arrayUtil.toArray(lmsq, lmsq.size(), User.class);
    }

    /**
     * 审批入盟申请
     * @param lmId
     * @param userId
     * @param type
     * @return
     */
    @GetMapping("/spsq")
    public HjArray spsq(String lmId,String userId, Integer type){
        List<User> sqsp = lianmengService.sqsp(lmId, userId, type);
        return arrayUtil.toArray(sqsp, sqsp.size(), User.class);
    }

    /**
     * 退出联盟
     * @param lmId
     * @param userId
     * @return
     */
    @GetMapping("/tclm")
    public HjDict tclm(String lmId, String userId){
        lianmengService.tclm(lmId, userId);
        return null;
    }

}
