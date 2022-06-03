package com.example.operationalmonitor.service;

import io.prometheus.client.Counter;
import org.springframework.stereotype.Service;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/6/2
 */
@Service
public class HelloServiceImpl {
    private Counter requests = Counter.build()
            .name("Lewis_TEST").help("Total requests.").register();


    public void counterIncr(){
        requests.inc();
        System.out.println("invoke Counter#inc");
    }
}
