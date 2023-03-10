package com.yuxi.msjs.bean.conste;

/**
 * 产量
 *
 * @author songhongxing
 * @date 2023/03/09 9:19 上午
 */
public enum Chanliang {
    LING(0,100),
    YIJI(1,400),
    ERJI(2,1800),
    SANJI(3,5600),
    SIJI(4,13000),
    WUJI(5,25200),
    LIUJI(6,43400),
    QIJI(7,68800),
    BAJI(8,102600),
    JIUJI(9,146000),
    SHIJI(10,2000200),
    SHIYI(11,266400),
    SHIER(12,345800),
    SHISAN(13,439600),
    SHISI(14,549000),
    SHIWU(15,675200),
    SHILIU(16,819400),
    SHIQI(17,982800),
    SHIBA(18,1166600),
    SHIJIU(19,1372000),
    ERSHI(20,1600200);


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

    public static Integer getChanliang(Integer dj) {
        for (Chanliang e : Chanliang.values()) {
            if(e.getDj().equals(dj)){
                return e.getCl();
            }
        }
        return null;
    }
}
