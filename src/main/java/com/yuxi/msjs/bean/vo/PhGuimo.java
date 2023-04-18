package com.yuxi.msjs.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 个人规模排行
 *
 * @author songhongxing
 * @date 2023/04/18 10:09 上午
 */
@Data
@Document("ph_guimo")
@NoArgsConstructor
@AllArgsConstructor
public class PhGuimo {

    @Field
    private Integer index;
    @Field
    private String userId;
    @Field
    private String userName;
    @Field
    private Integer ccsl;//城池数量 19
    @Field
    private Integer zgm;//城池总规模 20
}
