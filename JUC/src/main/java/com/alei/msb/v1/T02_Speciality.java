package com.alei.msb.v1;

import jdk.jfr.ContentType;

/**
 * @Author LeiLiMin
 * @Description: 线程的三大特性
 * @date: 2022/5/8
 */
public class T02_Speciality {
    public static void main(String[] args) throws InterruptedException {
        visibility();
    }

    public static boolean interruptFlag = false;

    /**
     * 线程可见性
     * t1线程不会被停止,由于interruptFlag=false已经读取到线程的内存内，对主内存interruptFlag的值无法感知到
     */
    public static void visibility() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!interruptFlag) {

            }
            System.out.println("T1 over");
        });
        t1.start();
        Thread.sleep(1000);
        interruptFlag = true;
    }

    public static class A {
        public boolean running = false;
    }

    public static volatile A a = new A();

    /**
     * 修改a.running并不会让其他线程可见
     * 因为volatile修饰的是a,而不是a.running
     */
    public static void visibility1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!a.running) {

            }
            System.out.println("T1 over");
        });
        t1.start();
        Thread.sleep(1000);
        a.running = true;
    }
    /**
     * 1.缓存行：64Byte
     * 2.缓存一致性：通过缓存一致性怎么提高效率，填充cacheLine,为的是减少保证缓存一致性而导致的额外开销(通知与同步)
     * 3.缓存一致性协议：MESI
     *
     * JDK1.8: @Contended注解,让修饰的数据独占一个cacheLine
     */

}
