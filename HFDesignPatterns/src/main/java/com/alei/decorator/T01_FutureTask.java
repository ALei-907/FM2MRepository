package com.alei.decorator;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author LeiLiMin
 * @Description: {@link java.util.concurrent.AbstractExecutorService#newTaskFor(Callable)} w}
 * @date: 2022/5/20
 */
public class T01_FutureTask {
    public static void main(String[] args) {
        /**
         * 运用了装饰者模式
         *
         * 1.FutureTask 实现自 RunnableFuture, RunnableFuture 继承自 Runnable
         * 2.FutureTask的构造参数为Runnable
         * 3.采用组合的方式将"入参Runnable"通过"FutureTask"进行修饰。还是调用#run(),但是却封装了许多属于ThreadPool的操作
         */
        FutureTask<Integer> ft = new FutureTask<>(() -> {
            System.out.println("My is FutureTask!");
        }, null);


    }
}
