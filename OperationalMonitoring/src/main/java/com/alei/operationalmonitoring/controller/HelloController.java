package com.alei.operationalmonitoring.controller;

import io.prometheus.client.Counter;
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
    static final Counter requests = Counter.build()
            .name("requests_total").help("Total requests.").register();


    @GetMapping("/Hello")
    public String hello(@RequestParam String clientName) {
        requests.inc();
        return "Hello " + clientName;
    }
}
