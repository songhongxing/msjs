package com.yuxi.msjs.bean.conste;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/02/25 4:26 下午
 */
public enum Shoujun {

    YI(1, 500, "步兵", 500),
    ER(2, 1000, "枪兵", 750),
    SAN(3, 5000, "枪兵", 1500),
    SI(4, 12000, "弩兵", 3000),
    WU(5, 20000, "弩兵", 6000),
    LIU(6, 30000, "重骑", 12000),
    QI(7, 50000, "重骑", 24000),
    BA(8, 100000, "重骑", 48000),
    JIU(9, 200000, "重骑", 96000);


    private Integer dj;

    private Integer sj;

    private String sjlx;

    private Integer zjcl;

    Shoujun(Integer dj, Integer sj, String sjlx, Integer zjcl) {
        this.dj = dj;
        this.sj = sj;
        this.sjlx = sjlx;
        this.zjcl = zjcl;
    }

    public Integer getDj() {
        return dj;
    }

    public void setDj(Integer dj) {
        this.dj = dj;
    }

    public Integer getSj() {
        return sj;
    }

    public void setSj(Integer sj) {
        this.sj = sj;
    }

    public String getSjlx() {
        return sjlx;
    }

    public void setSjlx(String sjlx) {
        this.sjlx = sjlx;
    }

    public Integer getZjcl() {
        return zjcl;
    }

    public void setZjcl(Integer zjcl) {
        this.zjcl = zjcl;
    }

    public static Integer getKeyByValue(Integer dj) {
        for (Shoujun e : Shoujun.values()) {
            if (e.getDj().equals(dj)) {
                return e.getSj();
            }
        }
        return null;
    }
    public static String getSjlxz(Integer dj) {
        for (Shoujun e : Shoujun.values()) {
            if (e.getDj().equals(dj)) {
                return e.getSjlx();
            }
        }
        return null;
    }

    public static Integer getZjclz(Integer dj) {
        for (Shoujun e : Shoujun.values()) {
            if (e.getDj().equals(dj)) {
                return e.getZjcl();
            }
        }
        return null;
    }
}
