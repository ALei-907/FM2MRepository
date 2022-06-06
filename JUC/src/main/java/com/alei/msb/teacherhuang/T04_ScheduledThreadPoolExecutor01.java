package com.alei.msb.teacherhuang;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
         * 2.内部设置的Queue-{@link ScheduledThreadPoolExecutor.DelayedWorkQueue},是一个无界队列
         *   所以内部设置的最大线程数为Integer.MAX，其实是不生效的
         */
        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

        // 2.延迟执行任务
        /**
         * Task实现类: {@link ScheduledThreadPoolExecutor.ScheduledFutureTask}
         *            {@link ScheduledThreadPoolExecutor.ScheduledFutureTask#period}: 周期性标识
         *            {@link ScheduledThreadPoolExecutor.ScheduledFutureTask#time}: 执行时间
         *
         * 怎么保证延迟执行？保证周期性调度？{@link ThreadPoolExecutor#runWorker(java.util.concurrent.ThreadPoolExecutor.Worker)}
         *            无论何种类型的Worker去执行任务都得去TaskQueue获取任务，所以核心就是{@link ScheduledThreadPoolExecutor.DelayedWorkQueue} 如何获取任务
         *                 final ReentrantLock lock = this.lock;
         *                 lock.lock() 和 lock.lockInterruptibly() 的差别就是 或者可响应中断
         *            1.取出数组首元素，为空则阻塞等待
         *            2.到达目标时间，执行
         *            3.未到达目标时间
         */
        /**
         * TODO: 1.delay为负数的情况？
         *       2.ReentrantLock reentrantLock = new ReentrantLock();
         *         Condition condition = reentrantLock.newCondition();
         *       3.周期性调度的原理?
         *
         */


        scheduledExecutorService.schedule(() -> System.out.println("scheduled"), 10, TimeUnit.SECONDS);

        // 直接执行,调用链：schedule(command, 0, NANOSECONDS);
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
