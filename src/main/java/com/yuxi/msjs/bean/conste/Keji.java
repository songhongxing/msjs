package com.yuxi.msjs.bean.conste;

/**
 * 联盟科技加成
 *
 * @author songhongxing
 * @date 2023/03/09 9:19 上午
 */
public enum Keji {
    LING(0,0),
    YIJI(1,11500),
    ERJI(2,22000),
    SANJI(3,50500),
    SIJI(4,106000),
    WUJI(5,197500),
    LIUJI(6,197500),
    QIJI(7,334000),
    BAJI(8,524500),
    JIUJI(9,778000),
    SHIJI(10,1103500),
    SHIYI(11,1510000),
    SHIER(12,2006500),
    SHISAN(13,2602000),
    SHISI(14,3305500),
    SHIWU(15,4126000),
    SHILIU(16,5072500),
    SHIQI(17,6154000),
    SHIBA(18,7379500),
    SHIJIU(19,8758000),
    ERSHI(20,10000000);


    private Integer dj;
    private Integer rl;

    Keji(Integer dj, Integer rl) {
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
        for (Keji e : Keji.values()) {
            if(e.getDj().equals(dj)){
                return e.getRl();
            }
        }
        return null;
    }
}
