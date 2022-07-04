package com.example.operationalmonitor.controller;

import com.example.operationalmonitor.service.HelloServiceImpl;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/7
 */
@RestController
@RequestMapping("/v1")
public class HelloController {
    @Autowired
    private HelloServiceImpl helloService;

    /**
     * Grafana: https://grafana.com/docs/grafana/latest/installation/mac/
     *          安装配置grafana
     * @param clientName
     * @return
     */
    @GetMapping("/Hello")
    public String hello(@RequestParam String clientName) {
        helloService.counterIncr();
        return "Hello " + clientName;
    }

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
