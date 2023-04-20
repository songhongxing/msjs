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
    private Integer id;//0

    /**
     * 地块类型(村庄,农田,林场,石矿,铁矿,NPC城市,玩家城市)
     */
    @Field
    private String dklx;//1
    /**
     * 地块名称
     */
    @Field
    private String dkmc;//2
    /**
     * 地块等级
     */
    @Field
    private Integer dkdj;//3
    /**
     * 地块守军数量
     */
    @Field
    private Integer dksj;//4
    /**
     * 免战标志(0可攻打,1免战牌)
     */
    @Field
    private Integer mzbz;//5
    /**
     * 所属玩家id
     */
    @Field
    private String sswjId = "";//6
    /**
     * 所属玩家姓名
     */
    @Field
    private String sswjName = "";//7
    /**
     * 免战到期
     */
    @Field
    private Long mzdq = 0L;//8
    @Field
    private String lmId="";//联盟id9
    @Field
    private String lmmc="无";//联盟名称10
    @Field
    private String cityId="";//城市id11
    @Field
    private String sjlx="";//守军类型 12
    @Field
    private Integer hfsj=0;//恢复时间13
}
