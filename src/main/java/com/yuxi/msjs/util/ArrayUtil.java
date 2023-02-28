package com.yuxi.msjs.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 唤境返回值转换
 *
 * @author songhongxing
 * @date 2022/11/03 11:25 上午
 */
public class ArrayUtil<T> {
    

    public HjArray toArray(List<T> list, Integer width, Class clazz) {
        HjArray hjArray = new HjArray();
        StringBuffer data = new StringBuffer("[");
        T t;
        try {
            //获取对象的属性数量(唤境数组的y轴长度)
            Field[] fields = clazz.getDeclaredFields();
            //唤境数组的height
            int height = fields.length;
            hjArray.setSize(new int[]{width, height, 1});
            //唤境数组结构体{"xfarray":true,"size":[5,2,1],"data":[[[0],["111"]],[[1],["222"]],[[],[]],[[],[]],[[],[]]]}
            //根据结果填充数组,数量不够的直接空数组
            StringBuffer sb = new StringBuffer();
            StringBuffer listSb = new StringBuffer();
            //如果是空集合,返回一个空数组
            if (CollUtil.isEmpty(list)) {
                for (int i = 0; i < width; i++) {
                    listSb.append("[");
                    sb = new StringBuffer();
                    for (int j = 0; j < fields.length; j++) {
                        sb.append("[],");
                    }
                    listSb.append(sb.substring(0, sb.length() - 1)).append("],");
                }
            } else {
                for (int i = 0; i < width; i++) {
                    sb = new StringBuffer();
                    //遍历list结果集
                    listSb.append("[");
                    if (i < list.size()) {
                        t = list.get(i);
                        //每一列一层
                        for (Field field : fields) {
                            sb.append("[");
                            field.setAccessible(true);
                            Object fieldValue = field.get(t);
                            if (fieldValue instanceof Integer) {
                                sb.append(fieldValue).append("],");
                            } else if (fieldValue instanceof String) {
                                sb.append("\"").append(fieldValue).append("\"],");
                            } else {
                                sb.append("],");
                            }

                        }
                    } else {
                        for (int j = 0; j < fields.length; j++) {
                            sb.append("[],");
                        }
                    }
                    listSb.append(sb.substring(0, sb.length() - 1)).append("],");
                }
            }
            //填充空集合
            if(listSb.length() > 0){
                data.append(listSb.substring(0, listSb.length() - 1));
            }
            data.append("]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //[[["货币|绿钞|2000"],["货币|钻石|200"],[][][]]
        JSONArray jsonArray = JSONUtil.parseArray(data.toString());
        hjArray.setData(jsonArray);
        return hjArray;
    }

    public HjDict toDict(Map<String, Object> map) {
        HjDict hiDict = new HjDict();
        hiDict.setData(JSONUtil.parseObj(map));
        return hiDict;
    }
}
