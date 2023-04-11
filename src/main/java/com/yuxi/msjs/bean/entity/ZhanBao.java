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
@Document("zhanbao")
public class ZhanBao {

    @Field
    private String zbId;//战报id
    @Field
    private String userId;//用户id
    @Field
    private String xxbt;//消息标题
    @Field
    private String xxnr;//消息内容
    private Integer yd = 0;//已读

}
