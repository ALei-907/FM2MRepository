package com.alei.msb.teacherhuang;

import java.util.ArrayList;
import java.util.List;
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

    public static void main(String[] args) throws InterruptedException {
        // 1.不进行ShutDown: 主线程不阻塞执行数据已经返回,线程池线程还在那阻塞的,但结果已经输出 "[]"
        // 2.进行ShutDown: 阻塞等待输出结果 "[1]"
        // 3.进行ShutDownNow: throw InterruptedException(),因为正在执行的线程被打断了
        System.out.println(getUserIdList());

        // 源码分析
        executorService02.execute(()->{System.out.println("Hello World!");});
        executorService02.shutdown();
        executorService02.awaitTermination(1,TimeUnit.SECONDS);
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
}
