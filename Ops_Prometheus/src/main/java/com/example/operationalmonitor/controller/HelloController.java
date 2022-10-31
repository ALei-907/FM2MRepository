package com.example.operationalmonitor.controller;

import com.example.operationalmonitor.service.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LeiLiMin
 * @date: 2022/5/7
 */
@RestController
@RequestMapping("/v1")
public class HelloController {
    @Autowired
    @Qualifier("helloServiceImpl")
    private HelloServiceImpl helloService;

    @GetMapping("/gaugeIncr")
    public String gaugeIncr() {
        helloService.gaugeIncr();
        return "gaugeIncr";
    }

    @GetMapping("/gaugeDecr")
    public String gaugeDecr() {
        helloService.gaugeDecr();
        return "gaugeDecr";
    }
}
