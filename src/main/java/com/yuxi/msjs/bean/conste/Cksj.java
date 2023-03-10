package com.yuxi.msjs.bean.conste;

/**
 * 仓库升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Cksj {
    YIJI(1,1600,2000,1000,400,20),
    ERJI(2,2000,2500,1250,500,93),
    SANJI(3,2600,3250,1625,650,124),
    SIJI(4,3400,4250,2125,850,223),
    WUJI(5,4200,5250,2625,1050,346),
    LIUJI(6,5400,6750,3375,1350,537),
    QIJI(7,7000,8750,4375,187500,894),
    BAJI(8,9000,11250,5620,2250,1307),
    JIUJI(9,11600,14500,7250,2900,2028),
    SHIJI(10,14800,18500,9250,3700,2958),
    SHIYI(11,18800,23500,11750,4700,3679),
    SHIER(12,24200,30250,15125,6050,7459),
    SHISAN(13,31000,38750,19375,7750,11566),
    SHISI(14,39600,49500,24750,9900,17196),
    SHIWU(15,50800,63500,31750,12700,36097),
    SHILIU(16,65000,81250,40625,16250,40773),
    SHIQI(17,83000,103750,51875,20750,66101),
    SHIBA(18,106400,133000,66500,26600,100861),
    SHIJIU(19,136200,17250,85125,34050,123708),
    ERSHI(20,174200,217750,108875,43550,151594);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Cksj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Cksj e : Cksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Cksj e : Cksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Cksj e : Cksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Cksj e : Cksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
    public static Integer getShijian(Integer dj) {
        for (Cksj e : Cksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getSj();
            }
        }
        return null;
    }
}
