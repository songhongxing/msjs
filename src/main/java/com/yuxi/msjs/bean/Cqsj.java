package com.yuxi.msjs.bean;

/**
 * 城墙升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Cqsj {
    YIJI(1,1400,1750,1225,700,60),
    ERJI(2,1800,2250,1575,900,93),
    SANJI(3,2300,2875,2013,1150,144),
    SIJI(4,2900,3625,2538,1450,223),
    WUJI(5,3800,4750,3325,1900,346),
    LIUJI(6,4800,6000,4200,2400,537),
    QIJI(7,6200,7750,5425,3100,832),
    BAJI(8,7900,9875,6913,3950,1284),
    JIUJI(9,10100,12620,8838,5050,1981),
    SHIJI(10,12900,16125,11288,6450,3014),
    SHIYI(11,16500,20625,14438,8250,4809),
    SHIER(12,21200,26500,18550,10600,6969),
    SHISAN(13,27100,33875,23713,13550,9737),
    SHISI(14,34700,43375,30363,17350,14537),
    SHIWU(15,44400,55500,38850,22200,19757),
    SHILIU(16,56800,71000,49700,28400,26079),
    SHIQI(17,27200,90875,63613,36350,36017),
    SHIBA(18,93000,116250,81375,46500,44503),
    SHIJIU(19,119100,148875,104213,59550,54888),
    ERSHI(20,152400,190500,133350,76200,69821);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Cqsj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Cqsj e : Cqsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Cqsj e : Cqsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Cqsj e : Cqsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Cqsj e : Cqsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
