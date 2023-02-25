package com.yuxi.msjs.controller;

import com.yuxi.msjs.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 建筑相关
 *
 * @author songhongxing
 * @date 2023/02/25 2:21 下午
 */
@RestController
@RequestMapping("/jianzhu")
public class JianzhuController {

    @Autowired
    private MapService mapService;

    @GetMapping("/a")
    public String test(){
        return "aaaaa";
    }
    @GetMapping("/b")
    public void add(){
        mapService.insert();
    }
}
