package com.yuxi.msjs.bean.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author songhongxing
 * @date 2023/02/25 3:02 下午
 */
@Data
@Document("slg_map")
public class SlgMap {

    @Field
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
     * 所属玩家id
     */
    @Field
    private String sswjId = "";
    /**
     * 所属玩家姓名
     */
    @Field
    private String sswjName = "";
    /**
     * 免战到期
     */
    @Field
    private Long mzdq = 0L;
}
