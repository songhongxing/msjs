package com.yuxi.msjs.bean;

/**
 * 铜雀台升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Tqtsj {
    YIJI(1,1400,700,560,700,40),
    ERJI(2,1800,900,720,900,62),
    SANJI(3,2300,1150,920,1150,96),
    SIJI(4,2900,1450,1160,1450,372),
    WUJI(5,3800,1900,1520,1900,231),
    LIUJI(6,4800,2400,1920,2400,358),
    QIJI(7,3200,3100,2480,3100,554),
    BAJI(8,7900,3950,3160,3950,860),
    JIUJI(9,10100,5050,4040,5050,1333),
    SHIJI(10,12900,6450,5160,6450,2065),
    SHIYI(11,16500,8250,6600,8250,3201),
    SHIER(12,21200,10600,8480,10600,4960),
    SHISAN(13,27100,13550,10840,13550,7508),
    SHISI(14,285200,228160,199640,285200,11550),
    SHIWU(15,44400,22200,17760,22200,17730),
    SHILIU(16,56800,28400,22720,28400,19950),
    SHIQI(17,72700,36350,29080,36350,25542),
    SHIBA(18,93000,46500,37200,46500,33162),
    SHIJIU(19,119100,59550,47640,59550,40535),
    ERSHI(20,152400,76200,60960,76200,56788);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Tqtsj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Tqtsj e : Tqtsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Tqtsj e : Tqtsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Tqtsj e : Tqtsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Tqtsj e : Tqtsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
