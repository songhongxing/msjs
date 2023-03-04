package com.yuxi.msjs.bean;

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
@Document("xtxx")
public class Xtxx {


    @Field
    private String xxId;
    @Field
    private String xxbt;//消息标题
    @Field
    private String xxnr;//消息内容
    @Field
    private String jl1;//奖励1
    @Field
    private String jl2;//奖励2
    @Field
    private String jl3;//奖励3
    @Field
    private String jl4;//奖励4
    @Field
    private String jl5;//奖励5
    private Integer yd = 0;//已读

}
