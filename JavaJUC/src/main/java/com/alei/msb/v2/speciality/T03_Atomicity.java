package com.alei.msb.v2.speciality;

import java.util.concurrent.CountDownLatch;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/10
 */
public class T03_Atomicity {


    public static void main(String[] args) throws InterruptedException {
        disAtomicity();
    }

    /**
     * 非原子性实验
     * 核心：num++ -> 取值,++,存值
     *
     * 原子类进行CAS操作时本质上还是加锁了，所执行的汇编指令还是加了Lock指令(多核情况下)
     *
     * CAS还是加锁呢？ 那就要考虑是否过度消耗了CPU
     */
    public static long num = 0;

    public static void disAtomicity() throws InterruptedException {
        Thread[] threads = new Thread[5];
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            Thread t1 = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    num++;
                }
                latch.countDown();
            });
            threads[i] = t1;
        }

        for (Thread thread : threads) {
            thread.start();
        }

        latch.await();
        System.out.println(num);
    }
}
