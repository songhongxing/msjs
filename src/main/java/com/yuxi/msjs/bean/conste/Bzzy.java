package com.yuxi.msjs.bean.conste;

/**
 * 兵种资源
 *
 * @author songhongxing
 * @date 2023/02/28 5:10 下午
 */
public enum Bzzy {

    BB("步兵",120,100,180,40,1,40,12,40,20,60),
    QB("枪兵",244,260,308,100,2,60,30,75,78,75),
    NB("弩兵",260,216,167,60,2,20,28,26,85,66),
    QQ("轻骑",500,450,350,100,3,120,41,120,48,120),
    HQ("虎骑",550,640,800,180,4,64,68,200,80,102),
    CH("斥候",140,160,20,40,1,0,21,0,20,150),
    ZQ("重骑",466,374,323,120,3,35,60,45,160,115),
    CC("冲车",900,360,500,70,3,0,68,60,30,40),
    TSC("投石",950,1350,600,90,6,0,117,75,60,30),
    GB("工兵",5800,5300,7200,5500,1,0,1200,0,40,250);

    private String bz;
    private Integer mu;
    private Integer shi;
    private Integer tie;
    private Integer liang;
    private Integer hl;
    private Integer yz;
    private Integer sj;
    private Integer gj;
    private Integer fy;
    private Integer sd;

    Bzzy(String bz, Integer mu, Integer shi, Integer tie, Integer liang, Integer hl, Integer yz, Integer sj, Integer gj, Integer fy, Integer sd) {
        this.bz = bz;
        this.mu = mu;
        this.shi = shi;
        this.tie = tie;
        this.liang = liang;
        this.hl = hl;
        this.yz = yz;
        this.sj = sj;
        this.gj = gj;
        this.fy = fy;
        this.sd = sd;
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

    public Integer getYz() {
        return yz;
    }

    public void setYz(Integer yz) {
        this.yz = yz;
    }

    public Integer getSj() {
        return sj;
    }

    public void setSj(Integer sj) {
        this.sj = sj;
    }

    public Integer getGj() {
        return gj;
    }

    public void setGj(Integer gj) {
        this.gj = gj;
    }

    public Integer getFy() {
        return fy;
    }

    public void setFy(Integer fy) {
        this.fy = fy;
    }

    public Integer getSd() {
        return sd;
    }

    public void setSd(Integer sd) {
        this.sd = sd;
    }

    public Integer getHl() {
        return hl;
    }

    public void setHl(Integer hl) {
        this.hl = hl;
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
    public static Integer getHaoliang(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getHl();
            }
        }
        return null;
    }
    public static Integer getGongji(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getGj();
            }
        }
        return null;
    }
    public static Integer getFangyu(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getFy();
            }
        }
        return null;
    }
    public static Integer getSudu(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getSd();
            }
        }
        return null;
    }
    public static Integer getYunzai(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getYz();
            }
        }
        return null;
    }
    public static Integer getShijian(String bz) {
        for (Bzzy e : Bzzy.values()) {
            if(e.getBz().equals(bz)){
                return e.getSj();
            }
        }
        return null;
    }
}
