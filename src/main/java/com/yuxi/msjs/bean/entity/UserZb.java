package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户装备
 *
 * @author songhongxing
 * @date 2023/03/28 3:13 下午
 */
@Data
@Document("user_zb")
public class UserZb {

    @Field
    private String userId;//0
    @Field
    private String cityId;//1
    @Field
    private String zbId;//装备id 2
    @Field
    private String zblx;//装备类型 3 武器、防具、坐骑、饰品
    @Field
    private String zbmc;//装备名称 4
    @Field
    private Integer wy;//武艺加成 5
    @Field
    private Integer fy;//防御加成 6
    @Field
    private Integer sd;//速度加成 7
    @Field
    private Integer zl;//智谋加成 8
    @Field
    private String wjId = "无";//武将id 94
    @Field
    private String wjName = "无";//武将名称 10
    @Field
    private String pz;//装备品质 11



}
