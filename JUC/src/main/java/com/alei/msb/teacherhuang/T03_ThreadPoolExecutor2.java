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
            super(1, 4, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                        @Override
                        public void uncaughtException(Thread t, Throwable e) {
                            if (e != null) {
                                System.out.println("[MyThread02 afterExecute : ]" + e.getMessage());

                            }
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
            if (t != null) {
                System.out.println("[MyThread01 afterExecute : ]" + t.getMessage());

            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // [初识ShutDown]
        // 1.不进行ShutDown: 主线程不阻塞执行数据已经返回,线程池线程还在那阻塞的,但结果已经输出 "[]"
        // 2.进行ShutDown: 阻塞等待输出结果 "[1]"
        // 3.进行ShutDownNow: throw InterruptedException(),因为正在执行的线程被打断了
        // System.out.println(getUserIdList());

        shutDown();

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


    /**
     * [线程池异常监控01]: afterExecute可用于监控异常,但是并不会阻断线程池执行
     */
    static void executeMethod() {
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

    /**
     * ShutDown()：修改状态,打断空余线程,尝试进行tryTerminate()
     * ShutDown()：修改状态,打断所有线程,尝试进行tryTerminate()
     * {@link ThreadPoolExecutor#tryTerminate()}: 当workCount进行子类钩子函数调用{@link ThreadPoolExecutor#terminated()}
     */
    static void shutDown() throws InterruptedException {
        executorService04.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[shutDown]:" + Thread.currentThread().getName());
        });
        executorService04.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[shutDown]:" + Thread.currentThread().getName());
        });
        executorService04.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[shutDown]:" + Thread.currentThread().getName());
        });

        // 拒绝接受新任务，继续执行完workQueue内任务
        executorService04.shutdown();
        // 拒绝接受新任务，拒绝执行workQueue内任务,打断所有正在执行的任务(interrupt())
        // executorService04.shutdownNow();
        // executorService01.awaitTermination(0L,TimeUnit.SECONDS);
    }
}

