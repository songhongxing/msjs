package com.yuxi.msjs.bean;

/**
 * 铁矿升级
 */
public enum Tksj {

    YIJI(1,100,600,300,600,27),
    ERJI(2,1667,1000,500,1000,50),
    SANJI(3,2750,1650,825,1650,108),
    SIJI(4,4667,2800,1400,2800,216),
    WUJI(5,7750,4650,2325,4650,432),
    LIUJI(6,13000,7800,3900,7800,864),
    QIJI(7,21667,13000,6500,13000,1726),
    BAJI(8,36250,21750,10875,21750,3475),
    JIUJI(9,60500,36300,18150,36300,6767),
    SHIJI(10,101000,60600,30600,60600,13037),
    SHIYI(11,168750,101250,503325,10250,23608),
    SHIER(12,281750,169050,84525,169050,41268),
    SHISAN(13,470500,282300,141150,282300,74971),
    SHISI(14,785833,471500,235750,471500,123691),
    SHIWU(15,1312333,787400,393700,787400,178697),
    SHILIU(16,2191500,1314900,657450,1314900,213437),
    SHIQI(17,3659833,2195900,1097950,2195900,255868),
    SHIBA(18,6111917,3667150,1833575,3667150,324664),
    SHIJIU(19,10204000,6122400,3061200,6122400,365081),
    ERSHI(20,17045667,10227400,5113700,10227400,482561);


    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Tksj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
        this.dj = dj;
        this.mu = mu;
        this.shi = shi;
        this.tie = tie;
        this.liang = liang;
        this.sj = sj;
    }

    public Integer getDj() {
        return dj;
    }

    public void setDj(Integer dj) {
        this.dj = dj;
    }

    public Integer getMu() {
        return mu;
    }

    public void setMu(Integer mu) {
        this.mu = mu;
    }

    public Integer getShi() {
        return shi;
    }

    public void setShi(Integer shi) {
        this.shi = shi;
    }

    public Integer getTie() {
        return tie;
    }

    public void setTie(Integer tie) {
        this.tie = tie;
    }

    public Integer getLiang() {
        return liang;
    }

    public void setLiang(Integer liang) {
        this.liang = liang;
    }

    public Integer getSj() {
        return sj;
    }

    public void setSj(Integer sj) {
        this.sj = sj;
    }

    public static Integer getMuz(Integer dj) {
        for (Tksj e : Tksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Tksj e : Tksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Tksj e : Tksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Tksj e : Tksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
