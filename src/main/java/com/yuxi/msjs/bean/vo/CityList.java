package com.yuxi.msjs.bean.vo;

import lombok.Data;

/**
 * 城池列表
 *
 * @author songhongxing
 * @date 2023/03/01 2:48 下午
 */
@Data
public class CityList {

    private String cityId;

    private String cityName;

    private Integer zuobiao;

    private Integer zgm;//总规模

    private Integer zbl;//总兵力
}
