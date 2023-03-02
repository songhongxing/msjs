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
    private String lmId;
    @Field
    private String lmmc;
    @Field
    private Integer lmdj = 1;//联盟等级
    @Field
    private String lmgg = "";//联盟公告
    @Field
    private Integer lmrs = 0;//联盟人数
    @Field
    private Integer lmrl = 0;//联盟容量
    @Field
    private Integer lmjy = 0;//联盟经验
    @Field
    private Integer lmsjsx = 0;//联盟升级所需
    @Field
    private Integer mcldj = 0;//木产量等级
    @Field
    private Integer mcljy = 0;//木产量经验
    @Field
    private Integer mclsjsx = 0;//木产量升级所需
    @Field
    private Integer scldj = 0;//石产量等级
    @Field
    private Integer scljy = 0;//石产量经验
    @Field
    private Integer sclsjsx = 0;//石产量升级所需
    @Field
    private Integer tcldj = 0;//铁产量等级
    @Field
    private Integer tcljy = 0;//铁产量经验
    @Field
    private Integer tclsjsx = 0;//铁石产量升级所需
    @Field
    private Integer lcldj = 0;//粮产量等级
    @Field
    private Integer lcljy = 0;//粮产量经验
    @Field
    private Integer lclsjsx = 0;//粮产量升级所需
    @Field
    private Integer bdgjdj = 0;//部队攻击等级
    @Field
    private Integer bdgjjy = 0;//部队攻击经验
    @Field
    private Integer bdgjsjsx = 0;//部队攻击升级所需
    @Field
    private Integer bdfydj = 0;//部队防御等级
    @Field
    private Integer bdfyjy = 0;//部队防御经验
    @Field
    private Integer bdfysjsx = 0;//部队防御升级所需
    @Field
    private Integer bdsddj = 0;//部队速度等级
    @Field
    private Integer bdsdjy = 0;//部队速度经验
    @Field
    private Integer bdsdsjsx = 0;//部队速度升级所需


}
