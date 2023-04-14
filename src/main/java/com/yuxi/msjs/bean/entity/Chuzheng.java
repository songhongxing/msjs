package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 出征列表
 *
 * @author songhongxing
 * @date 2023/04/04 1:57 下午
 */
@Data
@Document("chuzheng")
public class Chuzheng {


    @Field
    private String czId; //出征id 0
    @Field
    private String czUserId; //出征用户id 1
    @Field
    private String czUserName; //出征用户名称 2
    @Field
    private String czCityId; //出征城市id 3
    @Field
    private Integer czZb;//出征坐标4
    @Field
    private String gdUserId=""; //被攻打用户id 5
    @Field
    private String gdUserName; //被攻打用户名称 6
    @Field
    private String gdCityId=""; //被攻打城市id 7
    @Field
    private Integer gdZb;//攻打坐标8
    @Field
    private String czlx; //出征类型,歼灭、劫掠、增援、建城、侦查,返回 9
    @Field
    private Integer czBuff;//0普通出征,1神行出征10
    @Field
    private String czWjId;//出征武将id 11
    @Field
    private String czWjName;//出征武将名称 12
    @Field
    private Integer sd = 0;//部队速度 13
    @Field
    private Integer ddsj = 0;//到达时间 14
    @Field
    private Integer bb = 0;//步兵 15
    @Field
    private Integer qb = 0;//枪兵 16
    @Field
    private Integer nb = 0;//弩兵 17
    @Field
    private Integer qq = 0;//轻骑 18
    @Field
    private Integer hq = 0;//虎骑兵 19
    @Field
    private Integer zq = 0;//重骑 20
    @Field
    private Integer ch = 0;//斥候 21
    @Field
    private Integer cc = 0;//冲车 22
    @Field
    private Integer tsc = 0;//投石车 23
    @Field
    private Integer gb = 0;//工兵 24
    @Field
    private String gdCityName=""; //被攻打城市名称25
    @Field
    private String czCityName=""; //出征城市名称26
    @Field
    private Integer xjsj = 0;//行军时间 27
}
