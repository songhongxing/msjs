package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 联盟
 *
 * @author songhongxing
 * @date 2023/03/01 5:07 下午
 */
@Data
@Document("lianmeng")
public class Lianmeng {

    @Field
    private String lmId;//联盟id
    @Field
    private String lmmc;//联盟名称
    @Field
    private Integer lmdj = 0;//联盟等级
    @Field
    private String lmgg = "";//联盟公告
    @Field
    private Integer lmrs = 0;//联盟人数
    @Field
    private Integer lmrl = 30;//联盟容量
    @Field
    private Integer lmjy = 0;//联盟经验
    @Field
    private Integer lmsjsx = 10000;//联盟升级所需
    @Field
    private Integer zyscdj = 0;//资源生产等级 每一级增加2%
    @Field
    private Integer zyscjy = 0;//资源生产经验
    @Field
    private Integer zyscsjsx = 10000;//资源生产升级所需
    @Field
    private Integer bdgjdj = 0;//部队攻击等级
    @Field
    private Integer bdgjjy = 0;//部队攻击经验
    @Field
    private Integer bdgjsjsx = 10000;//部队攻击升级所需
    @Field
    private Integer bdfydj = 0;//部队防御等级
    @Field
    private Integer bdfyjy = 0;//部队防御经验
    @Field
    private Integer bdfysjsx = 10000;//部队防御升级所需
    @Field
    private Integer bdsddj = 0;//部队速度等级
    @Field
    private Integer bdsdjy = 0;//部队速度经验
    @Field
    private Integer bdsdsjsx = 10000;//部队速度升级所需
    @Field
    private Integer zbsddj = 0;//招兵速度等级
    @Field
    private Integer zbsdjy = 0;//招兵速度经验
    @Field
    private Integer zbsdsjsx = 10000;//招兵速度升级所需

}
