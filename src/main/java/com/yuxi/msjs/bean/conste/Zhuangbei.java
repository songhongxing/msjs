package com.yuxi.msjs.bean.conste;
public enum Zhuangbei {

    //白 0-60
    //绿 61-80
    //蓝 81-90
    //紫 91-96
    //橙 97-99
    //红 100

    QL("青龙偃月刀","武器","红", 7,0,0,0),
    FTHJ("方天画戟","武器","红",7,0,0,0),
    YT("倚天剑","武器","橙", 6,0,0,0),
    ZB("丈八蛇矛","武器","紫",6,0,0,0),
    CX("雌雄双股剑","武器","蓝",5,0,0,0),
    QLG("麒麟弓","武器","蓝",5,0,0,0),
    YS("羽扇","武器","绿",4,0,0,0),
    DD("大刀","武器","白",2,0,0,0),
    CQ("长枪","武器","白",2,0,0,0),
    ZJSNJ("紫金狻猊甲","防具","红",0,7,0,0),
    MGK("明光铠","防具","橙",0,6,0,0),
    HXJ("护心镜","防具","紫",0,5,0,0),
    XTJ("玄铁甲","防具","蓝",0,4,0,0),
    TJ("藤甲","防具","绿",0,3,0,0),
    SZJ("锁子甲","防具","白",0,2,0,0),
    CT("赤兔","坐骑","红",0,0,7,0),
    DL("的卢","坐骑","橙",0,0,6,0),
    ZHFD("爪黄飞电","坐骑","紫",0,0,5,0),
    JY("绝影","坐骑","蓝",0,0,4,0),
    ZX("紫骍","坐骑","绿",0,0,3,0),
    HBM("黄骠马","坐骑","白",0,0,2,0),
    DJTS("遁甲天书","饰品","红",0,0,0,7),
    BF("孙子兵法","饰品","橙",0,0,0,6),
    BZT("八阵图","饰品","紫",0,0,0,5),
    QXD("七星灯","饰品","蓝",0,0,0,4),
    CHQ("春秋","饰品","绿",0,0,0,3),
    JHL("酒葫芦","饰品","白",0,0,0,2);


    private String zbmc;
    private String zblx;
    private String pz;
    private Integer wy;
    private Integer fy;
    private Integer sd;
    private Integer zl;

    Zhuangbei(String zbmc, String zblx,String pz, Integer wy, Integer fy, Integer sd, Integer zl) {
        this.zbmc = zbmc;
        this.zblx = zblx;
        this.pz = pz;
        this.wy = wy;
        this.fy = fy;
        this.sd = sd;
        this.zl = zl;
    }

    public String getZbmc() {
        return zbmc;
    }

    public void setZbmc(String zbmc) {
        this.zbmc = zbmc;
    }

    public String getZblx() {
        return zblx;
    }

    public void setZblx(String zblx) {
        this.zblx = zblx;
    }

    public Integer getWy() {
        return wy;
    }

    public void setWy(Integer wy) {
        this.wy = wy;
    }

    public Integer getFy() {
        return fy;
    }

    public void setFy(Integer fy) {
        this.fy = fy;
    }

    public Integer getSd() {
        return sd;
    }

    public void setSd(Integer sd) {
        this.sd = sd;
    }

    public Integer getZl() {
        return zl;
    }

    public void setZl(Integer zl) {
        this.zl = zl;
    }

    public String getPz() {
        return pz;
    }

    public void setPz(String pz) {
        this.pz = pz;
    }

    public static String getLx(String zbmc) {
        for (Zhuangbei e : Zhuangbei.values()) {
            if(e.getZbmc().equals(zbmc)){
                return e.getZblx();
            }
        }
        return null;
    }
    public static Integer getWyz(String zbmc) {
        for (Zhuangbei e : Zhuangbei.values()) {
            if(e.getZbmc().equals(zbmc)){
                return e.getWy();
            }
        }
        return null;
    }
    public static Integer getFyz(String zbmc) {
        for (Zhuangbei e : Zhuangbei.values()) {
            if(e.getZbmc().equals(zbmc)){
                return e.getFy();
            }
        }
        return null;
    }
    public static Integer getSdz(String zbmc) {
        for (Zhuangbei e : Zhuangbei.values()) {
            if(e.getZbmc().equals(zbmc)){
                return e.getSd();
            }
        }
        return null;
    }

    public static Integer getZlz(String zbmc) {
        for (Zhuangbei e : Zhuangbei.values()) {
            if(e.getZbmc().equals(zbmc)){
                return e.getZl();
            }
        }
        return null;
    }
    public static String getZbpz(String zbmc) {
        for (Zhuangbei e : Zhuangbei.values()) {
            if(e.getZbmc().equals(zbmc)){
                return e.getPz();
            }
        }
        return null;
    }
}
