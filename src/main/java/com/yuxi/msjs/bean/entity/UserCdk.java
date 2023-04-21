package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户已经使用过的cdk
 *
 * @author songhongxing
 * @date 2023/04/21 11:23 上午
 */
@Data
@Document("userCdk")
public class UserCdk {

    @Field
    private String cdk;
    @Field
    private String userId;
}
