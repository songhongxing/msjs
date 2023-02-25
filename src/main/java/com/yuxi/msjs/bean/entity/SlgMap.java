package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author songhongxing
 * @date 2023/02/25 3:02 下午
 */
@Data
@Document("slg_map")
public class SlgMap {

    @Id
    private Integer id;

    /**
     * 地块类型(村庄,农田,林场,石矿,铁矿,NPC城市,玩家城市)
     */
    @Field
    private String dklx;
    /**
     * 地块名称
     */
    @Field
    private String dkmc;
    /**
     * 地块等级
     */
    @Field
    private Integer dkdj;
    /**
     * 地块守军数量
     */
    @Field
    private Integer dksj;
    /**
     * 免战标志(0可攻打,1免战牌)
     */
    @Field
    private Integer mzbz;
    /**
     * 免战到期时间
     */
    @Field
    private Date mzdq;
}
