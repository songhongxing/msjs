package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("meinv")
public class Meinv {


    @Field
    private String userId; //0
    @Field
    private String cityId;//1
    @Field
    private String name;//2
    @Field
    private Integer dj;//2星属性上限 70 3星 80 4星 90，5星 100 3
    @Field
    private Integer ly;//礼仪 木4
    @Field
    private Integer sc;//身材 石5
    @Field
    private Integer zl;//智力 铁6
    @Field
    private Integer cy;//才艺 粮7
    @Field
    private Integer ml;//魅力 征兵8
    @Field
    private String gz = "";//官职9
    @Field
    private Integer bhzt = 0;//保护状态10
    @Field
    private String userName; //11玩家名


}
