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
    private String zbId;//战报id 0
    @Field
    private String userId;//用户id 1
    @Field
    private String date;//日期 2
    @Field
    private String sf;//胜负 3
    @Field
    private String zdfs;//战斗方式 4
    @Field
    private String xxbt;//消息标题 5
    @Field
    private String xxnr;//消息内容 6
    @Field
    private Integer yd = 0;//已读 7



}
