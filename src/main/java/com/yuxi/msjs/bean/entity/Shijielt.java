package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 世界聊天
 *
 * @author songhongxing
 * @date 2023/03/09 10:07 上午
 */
@Data
@Document("shijie")
public class Shijielt {

    @Field
    private String userId;//玩家id 0
    @Field
    private String name;//玩家名称 1
    @Field
    private String nr;//内容 2
    @Field
    private String sj;//时间 3
    @Field
    private Integer sjc;//秒 4
}
