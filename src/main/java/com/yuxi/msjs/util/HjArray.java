package com.yuxi.msjs.util;

import cn.hutool.json.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 唤境数组结构
 *
 * @author songhongxing
 * @date 2022/11/03 11:26 上午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HjArray {

    //是否是数组格式
    @Builder.Default
    private boolean xfarray = true;

    //[集合的数量,类的属性数量,0]
    private int[] size;

    //json数组
    private JSONArray data;
}
