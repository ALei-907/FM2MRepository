package com.alei.openapi.sensorsbi.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @Author LeiLiMin
 * @Description: TraceId 工具类
 * @date: 2022/1/19
 */
public class TraceUtil {
    /**
     * 通过ThreadLocal来传递traceId
     */
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String readTraceId() {

        return threadLocal.get();
    }

    public static void writeTraceId(String traceId) {
        if (StringUtils.isBlank(traceId)) {
            throw new RuntimeException("traceId is blank");
        }
        threadLocal.set(traceId);
    }

    public static void delTraceId() {
        threadLocal.remove();
    }

    public static String createTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
