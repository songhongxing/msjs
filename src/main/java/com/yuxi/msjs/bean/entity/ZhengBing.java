package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/02 3:38 下午
 */
@Data
@Document("zhengbing")
public class ZhengBing {

    @Field
    private String cityId;
    @Field
    private String bz;//兵种
    @Field
    private Integer sl;//招募数量
    @Field
    private Integer yzm;//已招募数量
    @Field
    private Double dghs;//单个耗时
    @Field
    private Integer zhs;//总耗时
    @Field
    private Integer kssj;//开始时间
    @Field
    private Integer jssj;//结束时间
}
