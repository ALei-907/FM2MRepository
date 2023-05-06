package com.alei.openapi.sensorsbi.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author LeiLiMin
 * @Description: 定制化线程工厂
 * @date: 2022/1/18
 */
public class DefaultThreadFactory implements ThreadFactory {
    // 线程名称
    private String taskName;

    // 是否守护线程
    private Boolean daemon = false;

    // 线程优先级[1,10],越高越优先
    private Integer priority = Thread.NORM_PRIORITY;

    // 线程序列号
    private AtomicInteger taskNum = new AtomicInteger();


    public DefaultThreadFactory(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread thread = new Thread(r, taskName + taskNum.incrementAndGet());
        try {
            if (thread.isDaemon() != this.daemon) {
                thread.setDaemon(daemon);
            }

            if (thread.getPriority() != this.priority) {
                thread.setPriority(this.priority);
            }
        } catch (Exception ignored) {

        }
        return thread;
    }
}
