package com.yuxi.msjs.bean;

/**
 * 兵种资源
 *
 * @author songhongxing
 * @date 2023/02/28 5:10 下午
 */
public enum Daoju {

    JYJZ("经验卷轴","用于提升武将等级",0),
    XSD("洗髓丹","重置武将的属性加点",0),
    YJJ("易筋经","提高武将的某个属性等级上限",0),
    HJ("号角","盟战时使用，提高部队攻击力",0),
    JN("锦囊","获取一个计谋",0),
    ZBX("装备箱","获取一件随机普通装备",0),
    SBX("神兵箱","有概率获取一件史诗装备",0),
    YNXJ("玉女心经","提高美女的某个属性",0),
    XDHJ("小袋黄金","打开后获取50两黄金",1),
    ZDHJ("中袋黄金","打开后获取100两黄金",1),
    DDHJ("大袋黄金","打开后获取200两黄金",1),
    SJF("瞬建符","建筑直接升级",0),
    SXL("神行令","缩短50%出征的时间",0),
    GJL("攻坚令","提高20%的部队攻击力",0),
    MZP("免战牌","使用后可让城市8小时内不受攻击，只能在城市没有军情的时候使用",1),
    SXS("四星石","可以让三星美女提高到四星",0),
    WXS("五星石","可以让四星美女提高到五星",0),
    LXS("六星石","可以让五星美女提高到六星",0),
    XXZY("小箱资源","木、石、铁、粮各增加10000",1),
    DXZY("大箱资源","木、石、铁、粮各增加50000",1);

    private String name;
    private String miaoshu;
    private Integer ksy;

    Daoju(String name, String miaoshu, Integer ksy) {
        this.name = name;
        this.miaoshu = miaoshu;
        this.ksy = ksy;
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

}
