package com.yuxi.msjs.bean.conste;

/**
 * 仓库容量
 *
 * @author songhongxing
 * @date 2023/03/09 9:19 上午
 */
public enum Rongliang {
    LING(0,50000),
    YIJI(1,75000),
    ERJI(2,150000),
    SANJI(3,275000),
    SIJI(4,450000),
    WUJI(5,675000),
    LIUJI(6,950000),
    QIJI(7,1275000),
    BAJI(8,1650000),
    JIUJI(9,2075000),
    SHIJI(10,2550000),
    SHIYI(11,3075000),
    SHIER(12,3650000),
    SHISAN(13,4275000),
    SHISI(14,4950000),
    SHIWU(15,5675000),
    SHILIU(16,6450000),
    SHIQI(17,7275000),
    SHIBA(18,8150000),
    SHIJIU(19,9075000),
    ERSHI(20,10000000);


    private Integer dj;
    private Integer rl;

    Rongliang(Integer dj, Integer rl) {
        this.dj = dj;
        this.rl = rl;
    }

    public Integer getDj() {
        return dj;
    }

    public void setDj(Integer dj) {
        this.dj = dj;
    }

    public Integer getRl() {
        return rl;
    }

    public void setRl(Integer rl) {
        this.rl = rl;
    }

    public static Integer getRongliang(Integer dj) {
        for (Rongliang e : Rongliang.values()) {
            if(e.getDj().equals(dj)){
                return e.getRl();
            }
        }
        return null;
    }
}
