package com.yuxi.msjs.bean.vo;

import lombok.Data;

/**
 * 联盟成员
 *
 * @author songhongxing
 * @date 2023/03/01 5:58 下午
 */
@Data
public class Lmcy {

    private String userId;

    private String name;

    private String lmgzZw;//联盟官职

    private Integer lmgx;//联盟贡献
}
