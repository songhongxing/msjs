package com.yuxi.msjs.bean.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class UserDaoju {

    @Field
    private String userId; //0
    @Field
    private String name;//道具名 1
    @Field
    private String miaoshu;//描述 2
    @Field
    private Integer sl;//数量 3
    @Field
    private Integer ksy;//可在背包界面使用 4

    public UserDaoju(String userId, String name, Integer sl) {
        this.userId = userId;
        this.name = name;
        this.sl = sl;
    }
}
