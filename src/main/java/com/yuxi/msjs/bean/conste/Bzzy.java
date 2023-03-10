package com.yuxi.msjs.bean.conste;

/**
 * 兵种资源
 *
 * @author songhongxing
 * @date 2023/02/28 5:10 下午
 */
public enum Bzzy {

    BB("步兵",120,100,180,40),
    QB("枪兵",244,260,308,100),
    NB("弩兵",260,216,167,60),
    QQ("轻骑",500,450,350,100),
    HQ("虎骑",550,640,800,180),
    CH("斥候",140,160,20,40),
    ZQ("重骑",466,374,323,120),
    CC("冲车",900,360,500,70),
    TSC("投石",950,1350,600,90),
    GB("工兵",5800,5300,7200,5500);

    private String bz;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;

    Bzzy(String bz, Integer mu, Integer shi, Integer tie, Integer liang) {
        this.bz = bz;
        this.mu = mu;
        this.shi = shi;
        this.tie = tie;
        this.liang = liang;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public static Integer getMuz(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getMu();
            }
        }
        return null;
    }
    public static Integer getShiz(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getShi();
            }
        }
        return null;
    }
    public static Integer getTiez(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getTie();
            }
        }
        return null;
    }
    public static Integer getLiangz(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getLiang();
            }
        }
        return null;
    }
}
