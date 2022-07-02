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
        /**
         * (http_server_requests_seconds_count{job="dobby-dev",uri="/open/v1/auth/login"} offset -1h)- (http_server_requests_seconds_count{job="dobby-dev",uri="/open/v1/auth/login"} )
         * Grafana与prometheus的语义是不同的
         * Grafana: s是将整体进行前移
         * Prometheus是获取偏移后的数据
         */
        meterRegistry.counter("Lewis", Tags.of("tagKey","tagValue")).increment();
        System.out.println("invoke Counter#inc");
    }
}
