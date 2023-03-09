package com.yuxi.msjs.bean;
public enum Sksj {

    YIJI(1,417,333,833,500,20),
    ERJI(2,708,567,1417,850,40),
    SANJI(3,1167,933,2333,1400,80),
    SIJI(4,1958,1567,3917,2350,160),
    WUJI(5,3250,2600,6500,3900,320),
    LIUJI(6,5417,4333,10833,6500,640),
    QIJI(7,9042,7233,18083,10850,1291),
    BAJI(8,15083,12067,30167,18100,2560),
    JIUJI(9,25208,20167,50417,30250,5140),
    SHIJI(10,42083,33667,84167,50500,9755),
    SHIYI(11,70292,53233,140583,84350,16335),
    SHIER(12,117417,93933,234833,140900,26127),
    SHISAN(13,196042,156833,392083,235250,36747),
    SHISI(14,327417,261933,654833,392900,63997),
    SHIWU(15,546792,437433,1093583,656150,91962),
    SHILIU(16,913125,730500,1826250,1095750,123527),
    SHIQI(17,1524958,1219967,3049917,1829950,153287),
    SHIBA(18,2546667,2037333,5093333,305600,193703),
    SHIJIU(19,4252917,3402333,8505833,5103500,244343),
    ERSHI(20,7102333,5681867,14204667,8522800,301440);


    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Sksj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Sksj e : Sksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Sksj e : Sksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Sksj e : Sksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Sksj e : Sksj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
}
