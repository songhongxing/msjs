package com.yuxi.msjs.bean;

/**
 * 粮库升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Lksj {
    YIJI(1,800,1000,700,200,20),
    ERJI(2,1000,1250,875,250,70),
    SANJI(3,1400,1750,1225,350, 108),
    SIJI(4,1600,2000,1400,400,168),
    WUJI(5,2200,2750,1925,550,260),
    LIUJI(6,2800,3500,2450,700,283),
    QIJI(7,3600,4500,3150,900,624),
    BAJI(8,4600,5750,4025,1150,994),
    JIUJI(9,5800,1250,5075,1450,1456),
    SHIJI(10,7400,9250,6475,1850,2294),
    SHIYI(11,9400,11750,8225,2350,3602),
    SHIER(12,12000,15000,10500,3000,5599),
    SHISAN(13,15400,19250,13475,3850,8659),
    SHISI(14,19800,24750,17325,4950,13288),
    SHIWU(15,25400,31750,22225,6350,20269),
    SHILIU(16,32400,40500,28350,8100,31394),
    SHIQI(17,41600,52000,36400,10400,48942),
    SHIBA(18,53200,66500,46550,13300,75865),
    SHIJIU(19,68000,85000,59500,17000,120025),
    ERSHI(20,87200,109000,76300,21800,180865);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Lksj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Lksj e : Lksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Lksj e : Lksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Lksj e : Lksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Lksj e : Lksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
