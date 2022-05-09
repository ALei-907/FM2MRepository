package com.alei.msb.v2.speciality;

import java.util.concurrent.CountDownLatch;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/9
 */
public class T02_Order {


    public static void main(String[] args) throws InterruptedException {
        disOrder();
    }

    /**
     * 乱序验证
     * 为什么会出现乱序?
     *  *: 提高效率,类似与IO与CPU
     * 乱序出现的条件
     *  *: 前后语句不存在依赖关系"不影响单线程的最终一致性"
     */
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void disOrder() throws InterruptedException {
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(2);

            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
                latch.countDown();
            });
            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
                latch.countDown();
            });

            t1.start();
            t2.start();
            latch.await();
            if (x == 0 && y == 0) {
                System.out.println("x=0,y=0");
            }
        }
    }
}
