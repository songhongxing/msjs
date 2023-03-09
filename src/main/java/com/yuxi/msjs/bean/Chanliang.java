package com.yuxi.msjs.bean;

/**
 * 产量
 *
 * @author songhongxing
 * @date 2023/03/09 9:19 上午
 */
public enum Chanliang {
    YIJI(1,50),
    ERJI(2,90),
    SANJI(3,50),
    SIJI(4,50),
    WUJI(5,50),
    LIUJI(6,50),
    QIJI(7,50),
    BAJI(8,50),
    JIUJI(9,50),
    SHIJI(10,50),
    SHIYI(11,50),
    SHIER(12,50),
    SHISAN(13,50),
    SHISI(14,50),
    SHIWU(15,50),
    SHILIU(16,50),
    SHIQI(17,50),
    SHIBA(18,50),
    SHIJIU(19,50),
    ERSHI(20,50),
    ;


    private Integer dj;
    private Integer cl;

    Chanliang(Integer dj, Integer cl) {
        this.dj = dj;
        this.cl = cl;
    }

    public Integer getDj() {
        return dj;
    }

    public void setDj(Integer dj) {
        this.dj = dj;
    }

    public Integer getCl() {
        return cl;
    }

    public void setCl(Integer cl) {
        this.cl = cl;
    }
}
