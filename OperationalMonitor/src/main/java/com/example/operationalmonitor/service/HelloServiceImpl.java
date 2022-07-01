package com.example.operationalmonitor.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/6/2
 */
@Service
public class HelloServiceImpl {

    @Autowired
    MeterRegistry meterRegistry;

    public void counterIncr() {
        // 自定义metric
        meterRegistry.counter("Lewis", Tags.of("tagKey","tagValue")).increment();
        System.out.println("invoke Counter#inc");
    }
}
