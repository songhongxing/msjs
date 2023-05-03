package com.yuxi.msjs.util;

import com.yuxi.msjs.bean.conste.Bzzy;
import com.yuxi.msjs.bean.entity.Chuzheng;
import com.yuxi.msjs.bean.entity.UserCity;
import com.yuxi.msjs.bean.entity.ZengYuan;

import java.util.UUID;

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
        if(userCity == null){
            return 0;
        }
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
     * 计算出征总耗粮
     * @param chuzheng
     * @return
     */
    public static Integer czzhl(Chuzheng chuzheng){
        Integer zhl = 0;
        zhl += chuzheng.getBb() * Bzzy.getHaoliang("步兵");
        zhl += chuzheng.getQb() * Bzzy.getHaoliang("枪兵");
        zhl += chuzheng.getNb() * Bzzy.getHaoliang("弩兵");
        zhl += chuzheng.getQq() * Bzzy.getHaoliang("轻骑");
        zhl += chuzheng.getHq() * Bzzy.getHaoliang("虎骑");
        zhl += chuzheng.getCh() * Bzzy.getHaoliang("斥候");
        zhl += chuzheng.getZq() * Bzzy.getHaoliang("重骑");
        zhl += chuzheng.getCc() * Bzzy.getHaoliang("冲车");
        zhl += chuzheng.getTsc() * Bzzy.getHaoliang("投石");
        zhl += chuzheng.getGb() * Bzzy.getHaoliang("工兵");
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

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",

            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",

            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",

            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",

            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",

            "W", "X", "Y", "Z" };

    public static String uuid() {
        StringBuffer shortBuffer = new StringBuffer();

        String uuid = UUID.randomUUID().toString().replace("-", "");

        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);

            int x = Integer.parseInt(str, 16);

            shortBuffer.append(chars[x % 0x3E]);

        }

        return shortBuffer.toString();

    }

}
