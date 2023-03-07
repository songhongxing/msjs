package com.yuxi.msjs.bean.vo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * .
 *
 * @author songhongxing
 * @date 2023/03/07 11:56 上午
 */
@Data
public class WujiangVO {
    private String cityId;//0
    private String name;//武将名称//1
    private Integer dj = 1;//等级//2
    private Integer jy = 0;//经验3
    private Integer sjsx = 0;//升级所需4
    private Integer wl;//武力5
    private Integer fy;//防御6
    private Integer sd;//速度7
    private Integer zl;//智力8
}
