package com.yuxi.msjs.bean.conste;

/**
 * 仓库容量
 *
 * @author songhongxing
 * @date 2023/03/09 9:19 上午
 */
public enum Ancang {
    LING(0,0),
    YIJI(1,10000),
    ERJI(2,12000),
    SANJI(3,18000),
    SIJI(4,28000),
    WUJI(5,42000),
    LIUJI(6,60000),
    QIJI(7,82000),
    BAJI(8,108000),
    JIUJI(9,138000),
    SHIJI(10,172000),
    SHIYI(11,210000),
    SHIER(12,252000),
    SHISAN(13,298000),
    SHISI(14,348000),
    SHIWU(15,402000),
    SHILIU(16,460000),
    SHIQI(17,522000),
    SHIBA(18,588000),
    SHIJIU(19,658000),
    ERSHI(20,732000);


    private Integer dj;
    private Integer rl;

    Ancang(Integer dj, Integer rl) {
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
        for (Ancang e : Ancang.values()) {
            if(e.getDj().equals(dj)){
                return e.getRl();
            }
        }
        return null;
    }
}
