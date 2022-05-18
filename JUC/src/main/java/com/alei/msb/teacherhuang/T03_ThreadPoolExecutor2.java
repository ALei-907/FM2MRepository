package com.alei.msb.teacherhuang;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/16
 */
public class T03_ThreadPoolExecutor2 {
    public static ExecutorService executorService01 = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            return thread;
        }
    });
    public static ExecutorService executorService02 = Executors.newFixedThreadPool(1);

    public static ExecutorService executorService03 = new MyThread01();

    public static ExecutorService executorService04 = new MyThread02();

    static class MyThread01 extends ThreadPoolExecutor {
        public MyThread01() {
            super(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        }

        /**
         * 异常监控
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (Objects.nonNull(t))
                System.out.println("[MyThread01 afterExecute : ]" + t.getMessage());
        }
    }

    static class MyThread02 extends ThreadPoolExecutor {
        public MyThread02() {
            super(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                        @Override
                        public void uncaughtException(Thread t, Throwable e) {
                            System.out.println("[MyThread02 afterExecute : ]" + e.getMessage());
                        }
                    });
                    return t;
                }
            });
        }

        /**
         * 异常监控
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("[MyThread01 afterExecute : ]" + t.getMessage());
        }
    }

    public static void main(String[] args) {
        // [初识ShutDown]
        // 1.不进行ShutDown: 主线程不阻塞执行数据已经返回,线程池线程还在那阻塞的,但结果已经输出 "[]"
        // 2.进行ShutDown: 阻塞等待输出结果 "[1]"
        // 3.进行ShutDownNow: throw InterruptedException(),因为正在执行的线程被打断了
        // System.out.println(getUserIdList());


        // enterSources();

        // [线程池异常监控01]: afterExecute可用于监控异常,但是并不会阻断线程池执行
        executorService03.execute(() -> {
            int i = 1 / 0;
        });
        executorService03.execute(() -> {
            int i = 1 / 0;
        });
        executorService03.execute(() -> {
            int i = 1 / 0;
        });

        executorService04.execute(() -> {
            int i = 1 / 0;
        });
        executorService04.execute(() -> {
            int i = 1 / 0;
        });
        executorService04.execute(() -> {
            int i = 1 / 0;
        });

    }

    static List<String> getUserIdList() throws InterruptedException {
        ArrayList<String> uidList = new ArrayList<>();
        // 2.模仿一个io操作

        executorService01.submit(() -> {
            System.out.println("Hello");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            uidList.add("1");
            System.out.println("Exit");
        });
        // 3.对比 不执行任务操作,执行ShutDown(),执行ShutDownNow()
        executorService01.shutdown();

        // 只是执行#awaitTermination(),那直接不返回了
        executorService01.awaitTermination(1, TimeUnit.MINUTES);
        return uidList;
    }

    static void enterSources() throws InterruptedException {
        // 源码分析
        executorService02.execute(() -> {
            System.out.println("Hello World!");
        });
        executorService02.shutdown();
        executorService02.awaitTermination(1, TimeUnit.SECONDS);

    }
}
