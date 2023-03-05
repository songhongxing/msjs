package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("meinv")
public class Meinv {


    @Field
    private String userId;
    @Field
    private String cityId;
    @Field
    private String name;
    @Field
    private Integer dj;//2星属性上限 70 3星 80 4星 90，5星 100
    @Field
    private Integer ly;//礼仪 木
    @Field
    private Integer sc;//身材 石
    @Field
    private Integer zl;//智力 铁
    @Field
    private Integer cy;//才艺 粮
    @Field
    private Integer ml;//魅力 征兵
    @Field
    private String gz = "";//官职
    @Field
    private Integer bhzt = 0;//保护状态


}
