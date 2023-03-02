package com.yuxi.msjs.bean;

/**
 * 建筑对应关系
 *
 * @author songhongxing
 * @date 2023/02/28 5:10 下午
 */
public enum Bingzhong {

    BB("步兵","bb"),
    QB("枪兵","qb"),
    NB("弩兵","nb"),
    QQ("轻骑","qq"),
    HQ("虎骑","hq"),
    CH("斥候","ch"),
    ZQ("重骑","zq"),
    CC("冲车","cc"),
    TSC("投石","tsc"),
    GB("工兵","gb");

    private String bz;
    private String bzjc;

    Bingzhong(String bz, String bzjc) {
        this.bz = bz;
        this.bzjc = bzjc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getBzjc() {
        return bzjc;
    }

    public void setBzjc(String bzjc) {
        this.bzjc = bzjc;
    }

    public static String getKeyByValue(String bz) {
        for (Bingzhong e : Bingzhong.values()) {
            if(e.getBz().equals(bz)){
                return e.getBzjc();
            }
        }
        return null;
    }
}
