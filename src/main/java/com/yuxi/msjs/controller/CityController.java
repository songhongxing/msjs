package com.yuxi.msjs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yuxi.msjs.bean.entity.HomeUp;
import com.yuxi.msjs.bean.entity.Meinv;
import com.yuxi.msjs.bean.entity.SlgMap;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.Wujiang;
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

    /**
     * 城市信息
     * @param cityId
     * @return
     */
    @GetMapping("/info")
    public HjDict userCity(String cityId){
        UserCity userCity = cityService.userCity(cityId);
        return arrayUtil.toDict(BeanUtil.beanToMap(userCity));
    }

    /**
     * 建筑升级
     * @param cityId
     * @param jzName
     * @param jzdj
     * @param sjsj
     * @return
     */
    @GetMapping("/jz/sj")
    public HjArray jianzhusj(String cityId, String jzName){
        List<HomeUp> jzshengji = cityService.jzshengji(cityId, jzName);
        return arrayUtil.toArray(jzshengji, jzshengji.size(), HomeUp.class);
    }

    /**
     * 建筑升级完成
     * @param cityId
     * @param jzName
     * @return
     */
    @GetMapping("/jz/sjwc")
    public HjArray sjwc(String cityId, String jzName){
        List<HomeUp> sjwc = cityService.sjwc(cityId, jzName);
        return  arrayUtil.toArray(sjwc, sjwc.size(), HomeUp.class);
    }

    /**
     * 建筑队列
     */
    @GetMapping("/jzdl")
    public HjArray jianzhusj(String cityId){
        List<HomeUp> sjwc = cityService.jzdl(cityId);
        if(CollUtil.isNotEmpty(sjwc)){
            for (HomeUp homeUp : sjwc){
                homeUp.setSysj((int) (homeUp.getDqsj() - DateUtil.currentSeconds()));
            }
        }
        return  arrayUtil.toArray(sjwc, sjwc.size(), HomeUp.class);
    }

    /**
     * 征兵请求
     */
    @GetMapping("/zhengbing")
    public HjArray zhengbing(String cityId, String bz, Integer sl, Double dghs){
        List<ZhengBing> zhengbing = cityService.zhengbing(cityId, bz, sl, dghs);
        return arrayUtil.toArray(zhengbing, zhengbing.size(), ZhengBing.class);
    }

    /**
     * 征兵列表
     */
    @GetMapping("/zblb")
    public HjArray zhengbingduilie(String cityId){
        List<ZhengBing> zhengbing = cityService.zbdl(cityId);
        return arrayUtil.toArray(zhengbing, zhengbing.size(), ZhengBing.class);
    }

    /**
     * 新增武将
     */
    @GetMapping("/xzwj")
    public HjArray xzwj(String userId, String userName, String cityId, String name, Integer wl, Integer fy, Integer sd, Integer zl){
        List<Wujiang> xzwj = cityService.xzwj(userId, userName, cityId, name, wl, fy, sd, zl);
        return arrayUtil.toArray(xzwj, xzwj.size(), Wujiang.class);
    }

    /**
     * 武将列表
     *
     * @param cityId
     * @return
     */
    @GetMapping("/wjlb")
    public HjArray wjlb(String cityId, Integer czzt) {
        List<Wujiang> wjlb = cityService.wjlb(cityId, czzt);
        return arrayUtil.toArray(wjlb, wjlb.size(), Wujiang.class);
    }

    /**
     * 新增美女
     */
    @GetMapping("/xzmv")
    public HjArray xzmv(String userId,String userName, String cityId, String name, Integer ly, Integer sc, Integer zl, Integer cy, Integer ml, Integer dj){
        List<Meinv> mvlb = cityService.xzmv(userId, userName, cityId, name, ly, sc, zl, cy, ml, dj);
        return arrayUtil.toArray(mvlb, mvlb.size(), Meinv.class);
    }

    /**
     * 美女列表
     */
    @GetMapping("/mvlb")
    public HjArray xzwj(String cityId){
        List<Meinv> mvlb = cityService.mvlb(cityId);
        return arrayUtil.toArray(mvlb, mvlb.size(), Meinv.class);
    }

    /**
     * 添加美女属性
     */
    @GetMapping("/tjmvsx")
    public HjArray tjmvsx(String userId, String cityId,String name, String sx, Integer sxd){
        List<Meinv> tjmvsx = cityService.tjmvsx(userId, cityId, name, sx, sxd);
        return arrayUtil.toArray(tjmvsx, tjmvsx.size(), Meinv.class);
    }

    /**
     * 道具数量
     */
    @GetMapping("/djsl")
    public Integer ynxjsl(String userId, String name){
        return cityService.ynxjsl(userId, name);
    }

    /**
     * 休妻
     * @param userId
     * @param cityId
     * @param name
     * @return
     */
    @GetMapping("/xq")
    public HjArray tjmvsx(String userId, String cityId,String name){
        List<Meinv> tjmvsx = cityService.xq(userId, cityId, name);
        return arrayUtil.toArray(tjmvsx, tjmvsx.size(), Meinv.class);
    }

    /**
     * 任命美女官职
     */
    @GetMapping("/rmmv")
    public HjArray rmmv(String cityId, String name, String gz){
        List<Meinv> rmmv = cityService.rmmv(cityId, name, gz);
        return arrayUtil.toArray(rmmv, rmmv.size(), Meinv.class);
    }
    /**
     * 解除美女官职
     */
    @GetMapping("/jcmv")
    public HjArray jcmv(String cityId, String name){
        List<Meinv> jiechumv = cityService.jiechumv(cityId, name);
        return arrayUtil.toArray(jiechumv, jiechumv.size(), Meinv.class);
    }

    /**
     * 添加武将
     */
    @GetMapping("/tjwj")
    public HjArray tjwj(String userId, String cityId, String name,Integer xj, Integer wl, Integer fy, Integer sd, Integer zl){
        List<Wujiang> tjwj = cityService.tjwj(userId, cityId, name, xj, wl, fy, sd, zl);
        return arrayUtil.toArray(tjwj, tjwj.size(), Wujiang.class);
    }

    /**
     * 武将经验
     */
    @GetMapping("/wjjy")
    public HjArray zjjyz(String wjId, Integer sl){
        List<Wujiang> wjjy = cityService.wjjy(wjId, sl);
        return arrayUtil.toArray(wjjy, wjjy.size(), Wujiang.class);
    }

    /**
     * 武将放逐
     */
    @GetMapping("/wjfz")
    public HjArray zjjyz(String cityId, String wjId) {
        List<Wujiang> wjjy = cityService.wjfz(cityId, wjId);
        return arrayUtil.toArray(wjjy, wjjy.size(), Wujiang.class);
    }

    /**
     * 资源转换
     */
    @GetMapping("/zyzh")
    public HjDict zyzh(String cityId, String lyzy, Integer lysl, String mbzy, Integer mbsl) {
        UserCity zyzh = cityService.zyzh(cityId, lyzy, lysl, mbzy, mbsl);
        return arrayUtil.toDict(BeanUtil.beanToMap(zyzh));
    }

    /**
     * 资源运输
     */
    @GetMapping("/zyys")
    public HjDict zyzh(String lyCityId, String lyzy, Integer lysl, String mbCityId, String mbzy, Integer mbsl) {
        UserCity zyzh = cityService.zyys(lyCityId, lyzy, lysl, mbCityId, mbzy, mbsl);
        return arrayUtil.toDict(BeanUtil.beanToMap(zyzh));
    }

    /**
     * 城市资源田列表
     * @param cityId
     * @return
     */
    @GetMapping("/zytlb")
    public HjArray zytlb(String cityId){
        List<SlgMap> slgMaps = cityService.cityZytes(cityId);
        return arrayUtil.toArray(slgMaps, slgMaps.size(), SlgMap.class);
    }

    /**
     * 放弃资源田
     * @param cityId
     * @param zb
     * @return
     */
    @GetMapping("/fqzyt")
    public HjArray fqzyt(String cityId, Integer zb){
        List<SlgMap> slgMaps = cityService.fqzyt(cityId, zb);
        return arrayUtil.toArray(slgMaps, slgMaps.size(), SlgMap.class);
    }

    /**
     * 修改城市名
     * @param cityId
     * @param name
     */
    @GetMapping("/xgcsm")
    public HjDict xgcsm(String cityId, String name){
        UserCity xgcsm = cityService.xgcsm(cityId, name);
        return arrayUtil.toDict(BeanUtil.beanToMap(xgcsm));
    }

    /**
     * 修改武将名
     * @param wjId
     * @param name
     * @return
     */
    @GetMapping("/xgwjm")
    public HjDict xgwjm(String wjId, String name){
        Wujiang xgwjm = cityService.xgwjm(wjId, name);
        return arrayUtil.toDict(BeanUtil.beanToMap(xgwjm));
    }

    /**
     * 修改美女名
     * @param mvId
     * @param name
     * @return
     */
    @GetMapping("/xgmvm")
    public HjDict xgmvm(String mvId, String name){
        Meinv xgmvm = cityService.xgmvm(mvId, name);
        return arrayUtil.toDict(BeanUtil.beanToMap(xgmvm));
    }

    /**
     * 改名
     * @param cityId
     * @param name
     */
    @GetMapping("/gaiming")
    public void gaiming(String cityId, String name){
        cityService.gaiming(cityId, name);
    }
}
