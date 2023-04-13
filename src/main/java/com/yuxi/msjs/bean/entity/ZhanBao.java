package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 系统消息
 *
 * @author songhongxing
 * @date 2023/03/04 10:22 上午
 */
@Data
@Document("zhanbao")
public class ZhanBao {

    @Field
    private String zbId;//战报id
    @Field
    private String userId;//用户id
    @Field
    private String date;//日期
    @Field
    private String sf;//胜负
    @Field
    private String zdfs;//战斗方式
    @Field
    private String xxbt;//消息标题
    @Field
    private String xxnr;//消息内容
    @Field
    private Integer yd = 0;//已读
    @Field
    private Integer mu = 0;//木
    @Field
    private Integer shi = 0;//石
    @Field
    private Integer tie = 0;//铁
    @Field
    private Integer liang = 0;//粮
    @Field
    private Integer jungong = 0;//军功
    @Field
    private Integer fulu = 0;//俘虏

}
