package com.yuxi.msjs.bean.entity;

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
    private String cityId;
    @Field
    private String jzName;//建筑名称
    @Field
    private Integer jzdj;//建筑等级
    @Field
    private Long dqsj;//完成时间

}
