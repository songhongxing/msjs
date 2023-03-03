package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户道具
 *
 * @author songhongxing
 * @date 2023/03/03 11:25 上午
 */
@Data
@Document("daoju")
public class UserDaoju {

    @Field
    private String userId;
    @Field
    private String name;//道具名
    @Field
    private String miaoshu;//描述
    @Field
    private Integer sl;//数量
    @Field
    private Integer ksy;//可在背包界面使用
}
