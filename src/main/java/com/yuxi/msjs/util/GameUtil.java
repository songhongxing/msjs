package com.yuxi.msjs.util;

import com.yuxi.msjs.bean.conste.Bzzy;
import com.yuxi.msjs.bean.entity.UserCity;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/04/13 10:04 上午
 */
public class GameUtil {


    /**
     * 计算总耗粮
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
        zhl += userCity.getTsc() * Bzzy.getHaoliang("投石车");
        zhl += userCity.getGb() * Bzzy.getHaoliang("工兵");
        return zhl;
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
