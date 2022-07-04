package com.example.operationalmonitor.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

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
        meterRegistry.counter("Lewis", Tags.of("tagKey", "tagValue")).increment();
        System.out.println("invoke Counter#inc");
    }

    private static String GaugeName = "Lewis-Gauge";

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public void gaugeIncr() {
        int i = atomicInteger.incrementAndGet();
        // 传引用的方式来持续修改值
        // meterRegistry.gauge(GaugeName, 1); // 这种方式就只能设置一次，一旦设置,后续就无法浮动修改
        meterRegistry.gauge(GaugeName, atomicInteger,AtomicInteger::get);
        System.out.println("Lewis-Gauge: " + i);

    }

    static final Gauge inprogressRequests = Gauge.build()
            .name("inprogress_requests").help("Inprogress requests.").register();

    public void gaugeDecr() {
        int i = atomicInteger.decrementAndGet();

        meterRegistry.gauge(GaugeName, atomicInteger,AtomicInteger::get);
        System.out.println("Lewis-Gauge: " + i);

    }
}
