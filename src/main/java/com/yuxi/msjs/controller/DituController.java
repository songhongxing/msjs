package com.yuxi.msjs.controller;

import com.yuxi.msjs.bean.entity.SlgMap;
import com.yuxi.msjs.service.MapService;
import com.yuxi.msjs.util.HjArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songhongxing
 * @date 2023/02/25 5:27 下午
 */
@CrossOrigin
@RestController
@RequestMapping("/ditu")
public class DituController extends BaseController{

    @Autowired
    private MapService mapService;


    /**
     * 查询地图列表
     * @author songhongxing
     * @date 2023/02/25 5:28 下午
     */
    @GetMapping("/lb")
    public HjArray dtlb(Integer ym){
        List<SlgMap> ditu = mapService.ditu(ym);
        return arrayUtil.toArray(ditu, ditu.size(), SlgMap.class);
    }

}
