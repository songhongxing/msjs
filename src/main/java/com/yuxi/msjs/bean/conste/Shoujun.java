package com.yuxi.msjs.bean.conste;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/02/25 4:26 下午
 */
public enum Shoujun {

    YI(1, 500),
    ER(2, 1000),
    SAN(3, 5000),
    SI(4, 12000),
    WU(5, 20000),
    LIU(6, 30000),
    QI(7, 50000),
    BA(8, 80000),
    JIU(9, 100000);


    private Integer dj;

    private Integer sj;

    Shoujun(Integer dj, Integer sj) {
        this.dj = dj;
        this.sj = sj;
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

    public static Integer getKeyByValue(Integer dj) {
        for (Shoujun e : Shoujun.values()) {
            if (e.getDj().equals(dj)) {
                return e.getSj();
            }
        }
        return null;
    }
}
