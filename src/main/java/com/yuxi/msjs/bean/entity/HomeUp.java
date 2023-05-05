package com.yuxi.msjs.bean.entity;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 建筑升级
 *
 * @author songhongxing
 * @date 2023/02/28 4:48 下午
 */
@Data
@Document("home_up")
public class HomeUp {

    @Field
    private String cityId;//0
    @Field
    private String jzName;//建筑名称1
    @Field
    private Integer jzdj;//建筑等级2
    @Field
    private Integer dqsj;//完成时间3
    private Integer sysj;//剩余时间4

}
