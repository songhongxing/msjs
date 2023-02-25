package com.yuxi.msjs.util;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 唤境辞典结构
 *
 * @author songhongxing
 * @date 2022/11/03 11:26 上午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HjDict {


    //{"xfdictionary":true,"data":{"角色名":"阿瑟发啊师傅","完成教学":1,"同意隐私":1,"瓶盖数量":200050,"印记数量":1000,"当前地图":"沙漠","沙漠最高分":0,"草原最高分":1590,"改名次数":1,"已通过关卡":1,"炮塔武器":"机炮","范围武器":"重型飞弹","挂载武器":"小型机炮","超级武器":"导弹支援"}}
    //是否是辞典格式
    @Builder.Default
    private boolean xfdictionary = true;

    //json数组
    private JSONObject data;
}
