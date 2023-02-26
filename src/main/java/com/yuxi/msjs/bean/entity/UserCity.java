package com.yuxi.msjs.bean.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class UserCity {

    @Id
    @Indexed
    private String cityId;//城市id
    @Field
    private String userId;//用户id
    @Field
    private String cityName;//城市名称
    @Field
    private Integer zuobiao;//坐标
    @Field
    private Integer mucc = 5000;//木存储
    @Field
    private Integer mucl = 0;//木产量
    @Field
    private Integer shicc = 5000;//石存储
    @Field
    private Integer shicl = 0;//石产量
    @Field
    private Integer tiecc = 5000;//铁存储
    @Field
    private Integer tiecl = 0;//铁产量
    @Field
    private Integer liangcc = 5000;//粮存储
    @Field
    private Integer liangcl = 0;//粮产量
    @Field
    private Integer nzt = 0;//内政厅
    @Field
    private Integer tqt = 0;//铜雀台
    @Field
    private Integer jg = 0;//酒馆
    @Field
    private Integer js = 0;//集市
    @Field
    private Integer by = 0;//兵营
    @Field
    private Integer lc = 0;//粮仓
    @Field
    private Integer ck = 0;//仓库
    @Field
    private Integer ac = 0;//暗仓
    @Field
    private Integer cq = 0;//城墙
    @Field
    private Integer ckcc = 10000;//仓库存储
    @Field
    private Integer lccc = 10000;//粮仓存储
    @Field
    private Integer bb = 0;//步兵
    @Field
    private Integer qb = 0;//枪兵
    @Field
    private Integer qq = 0;//轻骑
    @Field
    private Integer hq = 0;//虎骑兵
    @Field
    private Integer zq = 0;//重骑
    @Field
    private Integer ch = 0;//斥候
    @Field
    private Integer cc = 0;//冲车
    @Field
    private Integer tsc = 0;//投石车
    @Field
    private Integer gg = 0;//工兵
    @Field
    private Integer zhl = 0;//总耗粮
}
