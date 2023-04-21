package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 福利码
 *
 * @author songhongxing
 * @date 2023/04/21 11:12 上午
 */
@Data
@Document("cdk")
public class CDK {

    @Field
    private String cdk;
    @Field
    private Integer lx;//类型  0 通用,1专属
    @Field
    private String userId;//用户id
    @Field
    private Integer gqsj;//过期时间
    @Field
    private String jl1;
    @Field
    private String jl2;
    @Field
    private String jl3;
    @Field
    private String jl4;
    @Field
    private String jl5;
}
