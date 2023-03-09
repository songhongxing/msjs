package com.yuxi.msjs.bean;

/**
 * 兵营升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Bysj {
    YIJI(1,2100,1200,3000,1200,50),
    ERJI(2,2713,1550,3875,1550,79),
    SANJI(3,3413,1950,4875,1950,120),
    SIJI(4,4375,2500,6250,2500,188),
    WUJI(5,5600,3200,8000,3200,291),
    LIUJI(6,7175,4100,10250,4100,450),
    QIJI(7,9275,5300,13250,5300,639),
    BAJI(8,11813,6750,16875,6750,1080),
    JIUJI(9,15138,8650,21625,8650,1635),
    SHIJI(10,19338,11050,27625,11050,2299),
    SHIYI(11,24763,14150,35735,14150,3618),
    SHIER(12,31763,18150,45375,14150,5095),
    SHISAN(13,40600,23200,58000,23200,8083),
    SHISI(14,51975,29700,74250,29700,11104),
    SHIWU(15,66588,38050,95125,38050,16666),
    SHILIU(16,85225,48700,121750,48700,22428),
    SHIQI(17,109025,62300,155750,62300,29862),
    SHIBA(18,139563,79500,199375,79500,39898),
    SHIJIU(19,178675,102100,255250,102100,54238),
    ERSHI(20,228638,130650,326625,130650,74627);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Bysj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Bysj e : Bysj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Bysj e : Bysj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Bysj e : Bysj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Bysj e : Bysj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
