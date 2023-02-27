package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("user")
public class User {

    @Field
    @Indexed
    private String userId;//用户id
    @Field
    private String name;//名称
    @Field
    private String zcId;//主城id
    @Field
    private Integer hj = 0;//黄金
    @Field
    private Integer tq = 0;//铜钱
    @Field
    private String lmId = "0";//联盟id
    @Field
    private String dlsj;//登录时间

    private Long sjhms;//时间毫秒数
    @Field
    private Integer ggcs = 0;//广告经验
    @Field
    private Integer flsl = 0;//俘虏数量
    @Field
    private Double zyjc = 0D;//资源加成
    @Field
    private Integer mgg = 0;//是否免广告


}
