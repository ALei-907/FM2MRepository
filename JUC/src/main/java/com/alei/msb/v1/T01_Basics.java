package com.alei.msb.v1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author LeiLiMin
 * @Description: 关于线程的基础知识
 * @date: 2022/5/8
 */
public class T01_Basics {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 获取CPU的核数
        // 线程数=CPU核数 * 预计利用率 * (1 + 等待时长/计算时长)
        System.out.println("CPU Num=" + Runtime.getRuntime().availableProcessors());

        // 线程的创建方式
        /**
         * 1.继承Thread
         * 2.实现Runnable
         * 3.实现Callable
         *      : 可通过泛型返回数据
         */
        // 线程池
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Object> submit = service.submit(Object::new);
        System.out.println(submit.get());
        // FutureTask
    }

    /**
     * 线程中断的方法
     */
    public static volatile boolean running = true;

    public static void threadInterrupt() {
        // 1.粗暴的Stop,如果该线程正在持有一把锁,那么暂停之后并不会释放锁
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("go on");
            }
        });
        t1.start();
        t1.stop();
        // 2.使用volatile的方式虽然可以在逻辑上终止,但是也存在2个问题
        /**
         * 1.无法精确控制
         * 2.例如在while中存在wait()时无法退出
         */
        final AtomicInteger count = new AtomicInteger(0);
        Thread t2 = new Thread(() -> {
            while (running) {
                count.incrementAndGet();

            }
        });
        t2.start();
        t2.stop();

        // 3.优雅的方式进行优雅的结束
        t2.interrupt();
    }
}
