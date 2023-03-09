package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("wujiang")
public class Wujiang {

    @Field
    private String userId;//0
    @Field
    private String cityId;//1
    @Field
    private String name;//武将名称//2
    @Field
    private Integer dj = 1;//等级//3
    @Field
    private Integer jy = 0;//经验4
    @Field
    private Integer sjsx = 10000;//升级所需5
    @Field
    private Integer wl;//武力6
    @Field
    private Integer fy;//防御7
    @Field
    private Integer sd;//速度8
    @Field
    private Integer zl;//智力9
    @Field
    private Integer wljd=0;//武力加点10
    @Field
    private Integer fyjd=0;//防御加点11
    @Field
    private Integer sdjd=0;//速度加点12
    @Field
    private Integer zljd=0;//智力加点13
    @Field
    private String wq="";//武器14
    @Field
    private String fj="";//防具15
    @Field
    private String zq="";//坐骑16
    @Field
    private String sp="";//饰品17
    @Field
    private String jn1="无";//技能118
    @Field
    private String jn2="锁";//技能2 19
    @Field
    private String jn3="锁";//技能3 20
    @Field
    private Integer yjj = 0;//易筋经 21
    @Field
    private Integer czzt = 0;//出征状态 22
    @Field
    private Integer xj=0;//星级23;
    @Field
    private String wjId;//武将id 24
}
