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
    private String userId;//用户id0
    @Field
    private String name;//名称 1
    @Field
    private String zcId;//主城id 2
    @Field
    private Integer cssl = 1;//城市数量 3
    @Field
    private Integer hj = 0;//黄金 4
    @Field
    private Integer tq = 0;//铜钱 5
    @Field
    private String lmId = "无";//联盟id 6
    @Field
    private Integer lmgz = 0;//联盟官职 0成员,1副盟主,2盟主 7
    @Field
    private String lmgzZw = "成员";//联盟官职 0成员,1副盟主,2盟主 8
    @Field
    private Integer lmgx = 0;//联盟贡献 9
    @Field
    private String dlsj;//登录时间 10

    private Integer sjms;//时间秒数 11
    @Field
    private Integer ggcs = 0;//广告经验 12
    @Field
    private Integer flsl = 0;//俘虏数量 13
    @Field
    private Double zyjc = 0D;//资源加成 14
    @Field
    private Integer mgg = 0;//是否免广告 15
    @Field
    private String junxian = "五等校尉"; //16
    @Field
    private Integer jungong = 0;//军工 17
    @Field
    private Integer lmmc = 0;//联盟名称 18
    @Field
    private Integer ccsl = 1;//城池数量 19
    @Field
    private Integer zgm = 0;//城池总规模 20


}
