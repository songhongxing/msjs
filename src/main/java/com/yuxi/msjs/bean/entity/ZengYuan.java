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
    private String zyId;//增援id 0
    @Field
    private String userId;//用户id 1
    @Field
    private String userName;//用户id 2
    @Field
    private String cityId;//增援城池id 3
    @Field
    private String cityName;//增援城池id 4
    @Field
    private String zyUserId;//被增援用户id 5
    @Field
    private String zyCityId;//被增援城池id 6
    @Field
    private String czWj;//增援的出征武将 7
    @Field
    private Integer bb = 0;//步兵 8
    @Field
    private Integer qb = 0;//枪兵 9
    @Field
    private Integer nb = 0;//弩兵 10
    @Field
    private Integer qq = 0;//轻骑 11
    @Field
    private Integer hq = 0;//虎骑兵 12
    @Field
    private Integer zq = 0;//重骑 13
    @Field
    private Integer ch = 0;//斥候 14
    @Field
    private Integer cc = 0;//冲车 15
    @Field
    private Integer tsc = 0;//投石车 16
    @Field
    private Integer gb = 0;//工兵 17
    @Field
    private Integer xjsj = 0;//行军时间 18
    @Field
    private String zyUserName;//被增援用户id 19
    @Field
    private String zyCityName;//被增援城池id 20

}
