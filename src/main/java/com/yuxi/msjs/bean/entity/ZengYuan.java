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
@Document("zengyuan")
public class ZengYuan {

    @Field
    private String zyId;//增援id
    @Field
    private String userId;//用户id
    @Field
    private String userName;//用户id
    @Field
    private String cityId;//增援城池id
    @Field
    private String cityName;//增援城池id
    @Field
    private String zyUserId;//被增援用户id
    @Field
    private String zyCityId;//被增援城池id
    @Field
    private String czWj;//增援的出征武将
    @Field
    private Integer bb = 0;//步兵 23
    @Field
    private Integer qb = 0;//枪兵 24
    @Field
    private Integer nb = 0;//弩兵 25
    @Field
    private Integer qq = 0;//轻骑 26
    @Field
    private Integer hq = 0;//虎骑兵 27
    @Field
    private Integer zq = 0;//重骑 28
    @Field
    private Integer ch = 0;//斥候 29
    @Field
    private Integer cc = 0;//冲车 30
    @Field
    private Integer tsc = 0;//投石车 31
    @Field
    private Integer gb = 0;//工兵 32

}
