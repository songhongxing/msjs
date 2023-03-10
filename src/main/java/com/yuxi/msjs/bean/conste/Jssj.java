package com.yuxi.msjs.bean.conste;

/**
 * 集市升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Jssj {
    YIJI(1,1167,700,700,700,45),
    ERJI(2,1500,900,900,900,73),
    SANJI(3,1917,1150,1150,1150,108),
    SIJI(4,2417,1450,1450,1450,168),
    WUJI(5,3167,1900,1900,1900,260),
    LIUJI(6,4000,2400,2400,2400,403),
    QIJI(7,5167,3100,3100,3100,624),
    BAJI(8,6580,3950,3950,3950,978),
    JIUJI(9,8417,5050,5050,5050,1338),
    SHIJI(10,10750,6450,6450,6450,1603),
    SHIYI(11,13750,8250,8250,8250,2904),
    SHIER(12,17667,10600,10600,10600,3384),
    SHISAN(13,22583,13550,13550,13550,4007),
    SHISI(14,28917,17350,17350,17350,7124),
    SHIWU(15,37000,22200,22200,22200,9936),
    SHILIU(16,47333,28400,28400,28400,14668),
    SHIQI(17,60583,36350,36350,36350,23308),
    SHIBA(18,77500,46500,46500,46500,31897),
    SHIJIU(19,99250,59550,59550,59550,38797),
    ERSHI(20,127000, 76200,76200,76200, 51698);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Jssj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Jssj e : Jssj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Jssj e : Jssj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Jssj e : Jssj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Jssj e : Jssj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }

    public static Integer getShijian(Integer dj) {
        for (Jssj e : Jssj.values()) {
            if(e.getDj().equals(dj)){
                return e.getSj();
            }
        }
        return null;
    }
}
