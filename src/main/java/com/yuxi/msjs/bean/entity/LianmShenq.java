package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 联盟申请
 *
 * @author songhongxing
 * @date 2023/03/17 1:50 下午
 */
@Data
@Document("lmsq")
public class LianmShenq {

    @Field
    private String lmId;//联盟id
    @Field
    private String userId;//用户id
    @Field
    private String userName;//用户名称

}
