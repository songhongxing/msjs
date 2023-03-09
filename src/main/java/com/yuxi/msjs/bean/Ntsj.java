package com.yuxi.msjs.bean;

/**
 * 农田升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Ntsj {
    YIJI(1,1000,700,500,200,15),
    ERJI(2,1750,1225,875,350,30),
    SANJI(3,2750,1925,1375,550,60),
    SIJI(4,4750,3325,2375,950,120),
    WUJI(5,7750,5425,3875,1550,240),
    LIUJI(6,13000,9100,6500,2600,480),
    QIJI(7,21750,15225,10875,4350,960),
    BAJI(8,36250,25375,18125,7250,1920),
    JIUJI(9,60500,42350,30250,12100,3840),
    SHIJI(10,101000,70700,50500,20200,7680),
    SHIYI(11,168750,118125,84375,33750,13800),
    SHIER(12,281750,197225,140875,56350,24883),
    SHISAN(13,470500,329350,235250,94100,44790),
    SHISI(14,785750,550025,392875,157150,76170),
    SHIWU(15,1312250,918575,656125,262450,129423),
    SHILIU(16,2191500,1534050,1095750,438300,220023),
    SHIQI(17,3659750,2561825,1829875,731950,294063),
    SHIBA(18,6112000,4278400,3056000,122400,350303),
    SHIJIU(19,1020700,7144900,5103500,2041400,445343),
    ERSHI(20,7045750,11932025,8522875,3409150,5202836);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Ntsj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Ntsj e : Ntsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Ntsj e : Ntsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Ntsj e : Ntsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Ntsj e : Ntsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
