package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("lmsq")
public class Lmsq {

    @Field
    private String userId;
    @Field
    private String lmId;
    @Field
    private String userName;


}
