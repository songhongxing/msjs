package com.yuxi.msjs.bean.conste;

/**
 * 内政厅升级消耗
 *
 * @author songhongxing
 * @date 2023/03/09 1:37 下午
 */
public enum Nztsj {
    YIJI(1,7000,5600,4900,7000,100),
    ERJI(2,9300,7440,6510,9300,165),
    SANJI(3,12400,9920,8680,12400,240),
    SIJI(4,16450,13160,11515,16450,372),
    WUJI(5,21900,17520,15330,21900,577),
    LIUJI(6,29150,23320,20405,29150,895),
    QIJI(7,38750,31000,27125,38750,1387),
    BAJI(8,51550,41240,36085,51550,2149),
    JIUJI(9,68550,54840,47985,68550,3332),
    SHIJI(10,91150,72920,63805,91150,5164),
    SHIYI(11,121250,97000,84875,121250,8004),
    SHIER(12,161250,129000,112875,161250,12406),
    SHISAN(13,214450,171560,150115,214450,19230),
    SHISI(14,285200,228160,199640,285200,29807),
    SHIWU(15,379350,303480,265545,379350,46200),
    SHILIU(16,504500,403600,353150,504500,71610),
    SHIQI(17,671000,536800,469700,671000,110996),
    SHIBA(18,892450,713960,624715,892450,170244),
    SHIJIU(19,1186950,949560,830865,1186950,266668),
    ERSHI(20,1578650,1262920,1105055,1578650,413335);

    private Integer dj;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer sj;

    Nztsj(Integer dj, Integer mu, Integer shi, Integer tie, Integer liang, Integer sj) {
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
        for (Nztsj e : Nztsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(Integer dj) {
        for (Nztsj e : Nztsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(Integer dj) {
        for (Nztsj e : Nztsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(Integer dj) {
        for (Nztsj e : Nztsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getLiang();
            }
        }
        return null;
    }
    public static Integer getShijian(Integer dj) {
        for (Nztsj e : Nztsj.values()) {
            if(e.getDj().equals(dj)){
                return e.getSj();
            }
        }
        return null;
    }
}
