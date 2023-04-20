package com.yuxi.msjs.bean.conste;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/02/25 4:26 下午
 */
public enum Shoujun {

    YI(1, 500, "步兵"),
    ER(2, 1000, "枪兵"),
    SAN(3, 5000, "枪兵"),
    SI(4, 12000, "弩兵"),
    WU(5, 20000, "弩兵"),
    LIU(6, 30000, "重骑"),
    QI(7, 50000, "重骑"),
    BA(8, 100000, "重骑"),
    JIU(9, 200000, "重骑");


    private Integer dj;

    private Integer sj;

    private String sjlx;

    Shoujun(Integer dj, Integer sj, String sjlx) {
        this.dj = dj;
        this.sj = sj;
        this.sjlx = sjlx;
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
}
