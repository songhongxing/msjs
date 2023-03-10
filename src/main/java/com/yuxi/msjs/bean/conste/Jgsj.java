package com.yuxi.msjs.bean.conste;

/**
 * 酒馆升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Jgsj {
    YIJI(1,1440,960,1920,2440,80),
    ERJI(2,1920,1280,2560,3200,104),
    SANJI(3,2550,1770,3400,4250,192),
    SIJI(4,3390,2260,4520,5650,298),
    WUJI(5,4500,3000,6000,7500,662),
    LIUJI(6,6000,4000,8000,10000,988),
    QIJI(7,7980,5320,10640,13330,1080),
    BAJI(8,10590,7060,14120,17650,1606),
    JIUJI(9,14100,9400,18800,23500,2526),
    SHIJI(10,18750,12500,25000,31250,3694),
    SHIYI(11,24936,16650,33245,41500,5038),
    SHIER(12,33180,22120,44240,55300,7696),
    SHISAN(13,44100,29400,58800,73500,15053),
    SHISI(14,58680,39120,78240,97800,22696),
    SHIWU(15,78030,52020,104040,130050,29846),
    SHILIU(16,103800,69200,138400,17300,36014),
    SHIQI(17,138030,92020,184040,235005,41143),
    SHIBA(18,183600,122400,244800,30600,52723),
    SHIJIU(19,244170,162780,325560,406950,62676),
    ERSHI(20,324750,216500,433000,541250,73158);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Jgsj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Jgsj e : Jgsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Jgsj e : Jgsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Jgsj e : Jgsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Jgsj e : Jgsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
    public static Integer getShijian(Integer dj) {
        for (Jssj e : Jssj.values()) {
            if(e.getDj().equals(dj)){
                return e.getSj();
            }
        }
        return null;
    }
}
