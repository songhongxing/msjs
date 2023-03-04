package com.yuxi.msjs.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 玩家已读消息
 *
 * @author songhongxing
 * @date 2023/03/04 11:55 上午
 */
@Data
@Document("ydxx")
public class Ydxx {

    @Field
    private String userId;

    @Field
    private String xxId;

}
