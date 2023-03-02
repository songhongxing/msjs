package com.yuxi.msjs.bean.vo;

import lombok.Data;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/02 10:00 上午
 */
@Data
public class LianmengList {

    private String lmId;
    private String lmmc;//联盟名称
    private String mz;//盟主
    private Integer lmdj;//联盟等级
    private Integer lmrs;//联盟人数
    private Integer lmrl;//联盟容量

}
