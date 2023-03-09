package com.yuxi.msjs.bean;

public enum Lcsj {

    YIJI(1,400,1000,400,600,21),
    ERJI(2,667,1667,667,1000,46),
    SANJI(3,1100,2750,1100,1650,92),
    SIJI(4,1867,4667,1867,2800,192),
    WUJI(5,3100,7750,3100,4650,383),
    LIUJI(6,5200,13000,5200,7800,768),
    QIJI(7,8667,21667,8667,13000,1536),
    BAJI(8,14500,36250,14500,21750,3072),
    JIUJI(9,24200,60500,24200,36300,6126),
    SHIJI(10,40400,101000,40400,60600,11046),
    SHIYI(11,67500,168750,67500,101250,19902),
    SHIER(12,112700,281750,112700,169050,33692),
    SHISAN(13,188200,470500,188200,282300,47203),
    SHISI(14,314333,785833, 314333,471500,72875),
    SHIWU(15,524933,131233, 524933,787400,113138),
    SHILIU(16,876600,2191500,876600,1314900,153346),
    SHIQI(17,1463933,3659833,1463933,2195900,205481),
    SHIBA(18,2444767,6111917,2444767,3667150,224284),
    SHIJIU(19,4081600,10204000,4081600,6122400,271864),
    ERSHI(20,6818027,17045067,6818027,10007040,321571);


    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Lcsj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Lcsj e : Lcsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Lcsj e : Lcsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Lcsj e : Lcsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Lcsj e : Lcsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
