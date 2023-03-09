package com.yuxi.msjs.bean;

/**
 * 暗仓升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Acsj {
    YIJI(1,400,500,300,100,20),
    ERJI(2,500,650,400,150,50),
    SANJI(3,650,800,500,150,100),
    SIJI(4,850,1050,650,200,160),
    WUJI(5,1050,1350,800,250,260),
    LIUJI(6,1350,1700,1050,350,400),
    QIJI(7,1750,2200,1300,450,600),
    BAJI(8,2250,3600,2150,700,1001),
    JIUJI(9,2900,3600,2150,700,1450),
    SHIJI(10,3700,4600,2750,900,2300),
    SHIYI(11,4550,5700,3400,1150,3600),
    SHIER(12,4550,5700,3400,1450,5480),
    SHISAN(13,6400,8200,4850,1800,8599),
    SHISI(14,7400,9600,5650,2200,13400),
    SHIWU(15,8450,11100,6500,2650,20749),
    SHILIU(16,9550,12700,7400,3150,32209),
    SHIQI(17,10700,14400,8350,3700,49876),
    SHIBA(18,11900,16200,9350,4300,77416),
    SHIJIU(19,13150,18100,10450,4950,120000),
    ERSHI(20,14450,20100,11650,5650,172861);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Acsj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Acsj e : Acsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Acsj e : Acsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Acsj e : Acsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Acsj e : Acsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
