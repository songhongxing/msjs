package com.yuxi.msjs.bean;

/**
 * 建筑对应关系
 *
 * @author songhongxing
 * @date 2023/02/28 5:10 下午
 */
public enum Jianzhu {

    NZT("内政厅","nzt"),
    TQT("铜雀台","tqt"),
    JG("酒馆","jg"),
    JS("集市","js"),
    BY("兵营","by"),
    CK("仓库","ck"),
    LC("粮仓","lc"),
    AC("暗仓","ac"),
    CQ("城墙","cq"),
    LINCHANG("林场","linchang"),
    SHIKUANG("石矿","shikuang"),
    TIEKUANG("铁矿","tiekuang"),
    NONGTIAN("农田","nongtian");

    private String jzName;
    private String jzzd;

    Jianzhu(String jzName, String jzzd) {
        this.jzName = jzName;
        this.jzzd = jzzd;
    }

    public String getJzName() {
        return jzName;
    }

    public void setJzName(String jzName) {
        this.jzName = jzName;
    }

    public String getJzzd() {
        return jzzd;
    }

    public void setJzzd(String jzzd) {
        this.jzzd = jzzd;
    }

    public static String getKeyByValue(String jzName) {
        for (Jianzhu e : Jianzhu.values()) {
            if(e.getJzName().equals(jzName)){
                return e.getJzzd();
            }
        }
        return null;
    }
}
