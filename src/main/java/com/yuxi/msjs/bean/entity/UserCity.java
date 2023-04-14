package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("user_city")
public class UserCity {

    @Field
    @Indexed
    private String cityId;//城市id 0
    @Field
    private String userId;//用户id 1
    @Field
    private String cityName;//城市名称 2
    @Field
    private Integer zuobiao;//坐标 3
    @Field
    private Integer mucc = 50000;//木存储 4
    @Field
    private Integer mucl = 100;//木产量 5
    @Field
    private Integer shicc = 50000;//石存储 6
    @Field
    private Integer shicl = 100;//石产量 7
    @Field
    private Integer tiecc = 50000;//铁存储 8
    @Field
    private Integer tiecl = 100;//铁产量 9
    @Field
    private Integer liangcc = 50000;//粮存储 10
    @Field
    private Integer liangcl = 100;//粮产量 11
    @Field
    private Integer nzt = 0;//内政厅 12
    @Field
    private Integer tqt = 0;//铜雀台 13
    @Field
    private Integer jg = 0;//酒馆 14
    @Field
    private Integer js = 0;//集市 15
    @Field
    private Integer by = 0;//兵营 16
    @Field
    private Integer lc = 0;//粮仓 17
    @Field
    private Integer ck = 0;//仓库 18
    @Field
    private Integer ac = 0;//暗仓 19
    @Field
    private Integer cq = 0;//城墙 20
    @Field
    private Integer ckcc = 50000;//仓库存储 21
    @Field
    private Integer lccc = 50000;//粮仓存储 22
    @Field
    private Integer bb = 0;//步兵 23
    @Field
    private Integer qb = 0;//枪兵 24
    @Field
    private Integer nb = 0;//弩兵 25
    @Field
    private Integer qq = 0;//轻骑 26
    @Field
    private Integer hq = 0;//虎骑兵 27
    @Field
    private Integer zq = 0;//重骑 28
    @Field
    private Integer ch = 0;//斥候 29
    @Field
    private Integer cc = 0;//冲车 30
    @Field
    private Integer tsc = 0;//投石车 31
    @Field
    private Integer gb = 0;//工兵 32
    @Field
    private Integer zhl = 0;//总耗粮 33
    @Field
    private Integer zbl = 0;//总兵力 34
    @Field
    private Integer linchang = 0;//林场 35
    @Field
    private Integer shikuang = 0;//石矿等级 36
    @Field
    private Integer tiekuang = 0;//铁矿 37
    @Field
    private Integer nongtian = 0;//农田 38
    @Field
    private Integer zgm = 0;//总规模 39
    @Field
    private Integer acrl = 0;//暗仓容量 40
    @Field
    private Double mujc = 0.0;//木产量加成 41
    @Field
    private Double shijc = 0.0;//石产量加成 42
    @Field
    private Double tiejc = 0.0;//铁产量加成 43
    @Field
    private Double liangjc = 0.0;//粮产量加成 44
    @Field
    private Double zbjc = 0.0;//招兵加成 45
    @Field
    private Integer mujccl = 0;//木加成产量 46
    @Field
    private Integer shijccl = 0;//石加成产量 47
    @Field
    private Integer tiejccl = 0;//铁加成产量 48
    @Field
    private Integer liangjccl = 0;//粮加成产量 49
    @Field
    private String ccts = "无";//城池太守 50
    @Field
    private String cctsName = "无";//城池太守 51
}
