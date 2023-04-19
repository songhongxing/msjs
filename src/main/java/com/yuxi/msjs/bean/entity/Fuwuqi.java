package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 服务器
 *
 * @author songhongxing
 * @date 2023/04/19 11:32 上午
 */
@Data
@Document("fuwuqi")
public class Fuwuqi {

    @Field
    private Integer index;
    @Field
    private String name;//服务器名称
    @Field
    private String url;//服务器url
    @Field
    private Integer wjsl;//玩家数量
}
