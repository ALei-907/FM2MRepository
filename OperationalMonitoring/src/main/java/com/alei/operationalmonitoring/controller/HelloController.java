package com.alei.operationalmonitoring.controller;

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

    @GetMapping("/Hello")
    public String hello(@RequestParam String clientName) {
        return "Hello " + clientName;
    }
}
