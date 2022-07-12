package com.alei.msb.teacherhuang;

import java.util.concurrent.*;

/**
 * @author LeiLiMin
 * @escription: Java异步编程的核心类:  {@link java.util.concurrent.FutureTask}
 * @date: 2022/7/12
 */
public class T06_FutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        /**
         * Possible state transitions:
         * NEW -> COMPLETING -> NORMAL
         * NEW -> COMPLETING -> EXCEPTIONAL
         * NEW -> CANCELLED :被取消的任务是否继续留存在队列中,存在，通过状态位来停止执行。取消的任务过多怎么办，保留线程池的引用对任务队列进行自定义
         * NEW -> INTERRUPTING -> INTERRUPTED
         */
        FutureTask<Integer> futureTask = new FutureTask(()->1);
        Future<?> submit = service.submit(futureTask);
        // 获取执行结果,无限期等待
        System.out.println(futureTask.get());
        // 判断是否完成
        if (!submit.isDone()) {
            // 如果任务执行了,中断就行
            submit.cancel(true);
        }
    }
}
