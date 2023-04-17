package com.yuxi.msjs.util;

import com.yuxi.msjs.bean.conste.Bzzy;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.ZengYuan;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/04/13 10:04 上午
 */
public class GameUtil {


    /**
     * 计算城市总耗粮
     * @param userCity
     * @return
     */
    public static Integer zhl(UserCity userCity){
        Integer zhl = 0;
        zhl += userCity.getBb() * Bzzy.getHaoliang("步兵");
        zhl += userCity.getQb() * Bzzy.getHaoliang("枪兵");
        zhl += userCity.getNb() * Bzzy.getHaoliang("弩兵");
        zhl += userCity.getQq() * Bzzy.getHaoliang("轻骑");
        zhl += userCity.getHq() * Bzzy.getHaoliang("虎骑");
        zhl += userCity.getCh() * Bzzy.getHaoliang("斥候");
        zhl += userCity.getZq() * Bzzy.getHaoliang("重骑");
        zhl += userCity.getCc() * Bzzy.getHaoliang("冲车");
        zhl += userCity.getTsc() * Bzzy.getHaoliang("投石");
        zhl += userCity.getGb() * Bzzy.getHaoliang("工兵");
        return zhl;
    }

    /**
     * 计算增援总耗粮
     * @param zengyuan
     * @return
     */
    public static Integer zyzhl(ZengYuan zengyuan){
        Integer zhl = 0;
        zhl += zengyuan.getBb() * Bzzy.getHaoliang("步兵");
        zhl += zengyuan.getQb() * Bzzy.getHaoliang("枪兵");
        zhl += zengyuan.getNb() * Bzzy.getHaoliang("弩兵");
        zhl += zengyuan.getQq() * Bzzy.getHaoliang("轻骑");
        zhl += zengyuan.getHq() * Bzzy.getHaoliang("虎骑");
        zhl += zengyuan.getCh() * Bzzy.getHaoliang("斥候");
        zhl += zengyuan.getZq() * Bzzy.getHaoliang("重骑");
        zhl += zengyuan.getCc() * Bzzy.getHaoliang("冲车");
        zhl += zengyuan.getTsc() * Bzzy.getHaoliang("投石车");
        zhl += zengyuan.getGb() * Bzzy.getHaoliang("工兵");
        return zhl * 2;
    }

    /**
     * 计算总兵力
     * @param city
     * @return
     */
    public static Integer zbl(UserCity city){
        int zbl = city.getBb()+city.getQb()+city.getNb()+city.getQq()+city.getHq()+city.getZq()+city.getCh()+city.getGb()+city.getCc()+city.getTsc();
        return zbl;
    }

    /**
     * 总规模
     * @param city
     * @return
     */
    public static Integer zgm(UserCity city){
        int zgm = city.getNzt()+city.getTqt()+city.getJg()+city.getJs()+city.getBy()+city.getCk()+city.getLc()+city.getAc()+city.getCq()+city.getLc()+city.getShikuang()+city.getTiekuang()+city.getNongtian();
        return zgm;
    }
}
