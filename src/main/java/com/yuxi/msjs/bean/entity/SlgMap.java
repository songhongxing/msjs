package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author songhongxing
 * @date 2023/02/25 3:02 下午
 */
@Data
@Document("slg_map")
public class SlgMap {

    @Id
    private Integer id;

    @Field
    private String dklx;
}
