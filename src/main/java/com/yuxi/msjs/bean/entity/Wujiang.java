package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("wujiang")
public class Wujiang {

    @Field
    private String userId;
    @Field
    private String cityId;
    @Field
    private String name;//武将名称
    @Field
    private Integer dj = 1;//等级
    @Field
    private Integer jy = 0;//经验
    @Field
    private Integer wl;//武力
    @Field
    private Integer fy;//防御
    @Field
    private Integer sd;//速度
    @Field
    private Integer zl;//智力
    @Field
    private Integer wljd=0;//武力加点
    @Field
    private Integer fyjd=0;//防御加点
    @Field
    private Integer sdjd=0;//速度加点
    @Field
    private Integer zljd=0;//智力加点
    @Field
    private String wq="";//武器
    @Field
    private String fj="";//防具
    @Field
    private String zq="";//坐骑
    @Field
    private String sp="";//饰品
    @Field
    private String jn1="无";//技能1
    @Field
    private String jn2="锁";//技能2
    @Field
    private String jn3="锁";//技能3
    @Field
    private Integer yjj = 0;//易筋经
}
