package com.yuxi.msjs.bean.conste;

/**
 * 兵种资源
 *
 * @author songhongxing
 * @date 2023/02/28 5:10 下午
 */
public enum Daoju {

    JYJZ("经验卷轴","用于提升武将等级",0,50, 500),
    XSD("洗髓丹","重置武将的属性加点",0, 100,3000),
    YJJ("易筋经","提高武将的某个属性等级上限",0, 100,1000),
    HJ("号角","盟战时使用，提高部队攻击力",0, 100,1500),
    JN("锦囊","获取一个计谋",0, 100,1000),
    ZBX("装备箱","获取一件随机普通装备",1, 200,1000),
    SBX("神兵箱","有概率获取一件史诗装备",1, 100,1000),
    YNXJ("玉女心经","提高美女的某个属性",0, 100,1000),
    XDHJ("小袋黄金","打开后获取50两黄金",1, 100,1000),
    ZDHJ("中袋黄金","打开后获取100两黄金",1, 100,1000),
    DDHJ("大袋黄金","打开后获取200两黄金",1, 100,1000),
    SJF("瞬建符","建筑直接升级",0, 300,1000),
    GJL("神行攻坚令","提高20%的部队攻击力,缩短50%的行军时间",0, 100,1000),
    MZP("免战牌","使用后可让城市8小时内不受攻击，只能在城市没有军情的时候使用",0, 100,1000),
    SXS("四星石","可以让三星美女提高到四星",0, 500,10000),
    WXS("五星石","可以让四星美女提高到五星",0, 100,7000),
    LXS("六星石","可以让五星美女提高到六星",0, 100,5000),
    XXZY("小箱资源","木、石、铁、粮各增加50000",1, 100,1000),
    DXZY("大箱资源","木、石、铁、粮各增加200000",1, 100,1000),
    GMK("改名卡","可以修改城池、武将、美女的名字",0, 100,1000);

    private String name;
    private String miaoshu;
    private Integer ksy;
    private Integer hj;
    private Integer sw;

    Daoju(String name, String miaoshu, Integer ksy, Integer hj, Integer sw) {
        this.name = name;
        this.miaoshu = miaoshu;
        this.ksy = ksy;
        this.hj = hj;
        this.sw = sw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    public Integer getKsy() {
        return ksy;
    }

    public void setKsy(Integer ksy) {
        this.ksy = ksy;
    }

    public Integer getHj() {
        return hj;
    }

    public void setHj(Integer hj) {
        this.hj = hj;
    }

    public Integer getSw() {
        return sw;
    }

    public void setSw(Integer sw) {
        this.sw = sw;
    }

    public static String getMiaoshuz(String name) {
        for (Daoju e : Daoju.values()) {
            if(e.getName().equals(name)){
                return e.getMiaoshu();
            }
        }
        return null;
    }
    public static Integer getKsyz(String name) {
        for (Daoju e : Daoju.values()) {
            if(e.getName().equals(name)){
                return e.getKsy();
            }
        }
        return null;
    }
    public static Integer getHjz(String name) {
        for (Daoju e : Daoju.values()) {
            if(e.getName().equals(name)){
                return e.getHj();
            }
        }
        return null;
    }

    public static Integer getSwz(String name) {
        for (Daoju e : Daoju.values()) {
            if(e.getName().equals(name)){
                return e.getSw();
            }
        }
        return null;
    }


}
