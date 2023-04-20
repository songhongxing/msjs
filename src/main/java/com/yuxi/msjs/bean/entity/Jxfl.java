package com.yuxi.msjs.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 捐献俘虏日志
 *
 * @author songhongxing
 * @date 2023/04/20 3:26 下午
 */
@Data
@Document("jxfl")
@NoArgsConstructor
@AllArgsConstructor
public class Jxfl {

    @Field
    private String lmId;//联盟id
    @Field
    private String userName;//玩家名称
    @Field
    private Integer jxsl;//捐献数量
    @Field
    private String date;//捐献日期
    @Field
    private Integer datems;//捐献日期秒数

}
