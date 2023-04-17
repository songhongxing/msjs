package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 联盟聊天消息
 * @author songhongxing
 * @date 2023/03/09 11:43 上午
 */
@Data
@Document("lmxx")
public class Lmxx {

    @Field
    private String lmId;//联盟id 0
    @Field
    private String userId;//玩家id 1
    @Field
    private String name;//玩家名称 2
    @Field
    private String nr;//内容 3
    @Field
    private String sj;//时间 4
    @Field
    private Integer sjc;//秒 5
}
