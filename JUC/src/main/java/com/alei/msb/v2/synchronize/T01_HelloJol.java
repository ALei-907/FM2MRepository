package com.alei.msb.v2.synchronize;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author LeiLiMin
 * @Description: 查看对象的内存布局
 * @date: 2022/5/11
 */
public class T01_HelloJol {
    public static void main(String[] args) {
        Object o = new Object();
        /**
         * java.lang.Object object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           00 10 00 00 (00000000 00010000 00000000 00000000) (4096)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
        // 8Byte的markWord + 4Byte的classPointer + 4Byte的对齐填充
        System.out.println(ClassLayout.parseInstance(o).toPrintable());


        synchronized (o){
            /**
             *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
             *       0     4        (object header)                           05 90 80 c2 (00000101 10010000 10000000 11000010) (-1031761915)
             *       4     4        (object header)                           d7 7f 00 00 (11010111 01111111 00000000 00000000) (32727)
             *       8     4        (object header)                           00 10 00 00 (00000000 00010000 00000000 00000000) (4096)
             *      12     4        (loss due to the next object alignment)
             * Instance size: 16 bytes
             * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
             */
            // 可以发现MarkWord发生了变化(Spoiler: 偏向锁的出现)
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        // 1(偏向锁): 将线程ID记录在MarkWord即可
        // 2(轻量级锁): 撤销偏向锁,通过自旋的方式进行竞争
        //             锁竞争的线程在线程栈中生产lock record,将lr的指针设置到markWord。其余的线程继续竞争
        // 3(重量级锁): 向OS申请锁
        //             1.6之前可以通过 -XX:PreBlockSpin控制,之后就不需要控制(由JVM算法自己控制)

        // Synchronized是可重入锁

        // 关闭偏向锁? 在明确知道存在多线程竞争情况下是可以取消偏向锁的设置,那就没必要徒增锁撤销的步骤
        //            -XX:BiasedLockingStartupDelay=0 (偏向锁启动时延)
    }
}
