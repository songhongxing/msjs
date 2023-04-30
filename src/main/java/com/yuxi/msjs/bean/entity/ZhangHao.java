package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 账号表
 *
 * @author songhongxing
 * @date 2023/04/19 4:17 下午
 */
@Data
@Document("zhanghao")
public class ZhangHao {

    @Field
    private String zh;//账号 0
    @Field
    private String mm;//密码 1
    @Field
    private Integer fwqId;//服务器id 2
    @Field
    private String fwq;//服务器 3
    @Field
    private String userId;// 4
    @Field
    private String fwqUrl;// 5
}
