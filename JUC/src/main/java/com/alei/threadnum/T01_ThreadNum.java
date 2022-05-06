package com.alei.threadnum;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/5
 */
public class T01_ThreadNum {
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
}
