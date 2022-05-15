package com.alei.msb.teacherhuang;

import java.util.concurrent.*;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/15
 */
public class T03_ThreadPoolExecutor {
    public static void main(String[] args) {
        int cps = 1;
        int mps = 2;
        int c = 5;
        int size = mps + c;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(cps, mps,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(c),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // new ThreadPoolExecutor.CallerRunsPolicy()    // 调用者执行Task
        // new ThreadPoolExecutor.AbortPolicy()         // 直接抛出异常
        // new ThreadPoolExecutor.DiscardPolicy()       // 默默丢弃任务
        // new ThreadPoolExecutor.DiscardOldestPolicy() // 丢弃队列最前边的任务,然后将task加入到队列中

        // 测试拒绝策略：使用 QueueSize + MaxThreadSize + 1 的Thread进行测试
        for (int i = 0; i < size; i++) {
            threadPoolExecutor.execute(()->{
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolExecutor.execute(()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("wait");

        // 源码相关
        /**
         *   {@link threadPoolExecutor.ctl}
         *    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
         *    private static final int COUNT_BITS = Integer.SIZE - 3;
         *    private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;
         *
         *     // runState is stored in the high-order bits
         *     // 整形变量的高位来表示线程池状态
         *     private static final int RUNNING    = -1 << COUNT_BITS; // 32~29: 111 00000000..... --> 只有它是负数 --> if(ctl<0): 表示线程正在运行
         *     private static final int SHUTDOWN   =  0 << COUNT_BITS; // 32~29: 000 00000000.....
         *     private static final int STOP       =  1 << COUNT_BITS; // 32~29: 001 00000000.....
         *     private static final int TIDYING    =  2 << COUNT_BITS; // 32~29: 010 00000000.....
         *     private static final int TERMINATED =  3 << COUNT_BITS; // 32~29: 011 00000000.....
         */
    }
}
