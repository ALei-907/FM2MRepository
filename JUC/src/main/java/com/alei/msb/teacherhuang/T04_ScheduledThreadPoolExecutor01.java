package com.alei.msb.teacherhuang;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/20
 */
public class T04_ScheduledThreadPoolExecutor01 {
    public static void main(String[] args) {
        // 1.构造器相关
        /**
         * 1.新增了默认Worker的销毁时间
         * 2.内部设置的Queue-{@link DelayedWorkQueue},是一个无界队列
         *   所以内部设置的最大线程数为Integer.MAX，其实是不生效的
         */
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);


        scheduledExecutorService.schedule(() -> System.out.println("scheduled"), 10, TimeUnit.SECONDS);
        scheduledExecutorService.execute(() -> System.out.println("scheduled"));


        // 任务执行时开始计时
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(new Date());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);

        // 任务结束时开始计时
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println(new Date());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);

    }
}
