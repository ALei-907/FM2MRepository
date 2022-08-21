package com.alei.zcr.chapter_4;

/**
 * @author LeiLiMin
 * @date: 2022/8/19
 */
public class T02_Tools {
    public static void main(String[] args) {
        /**
         * jps [options] [hostid]: 列出正在进行中的虚拟机进程
         * jstat [options vmid interval s[ms] count]: jstat -gc 2764 250 20
         *                                            查询2764进程的垃圾收集情况 以250msd的查询频率 查询20次
         * jstack [options] vmid: 生成虚拟机当前时刻的线程快照
         */
        /**
         * debug程序时候的注意点:
         * safePoint: 有时候从GC的角度上观察，发现程序并没有问题
         *            但是会发现有些线程在其他线程在安全点上自旋等待GC时迟迟不进入safePoint
         *            这可能是使用了int类型的可计数循环(JVM不会再次放置安全点-认为int较短,参考T04_HotSpotGCArithmetic 关于安全点的描述)
         *            ,并且循环中有长时间的IO操作,导致该线程迟迟没有进入安全点
         *            可以设置-XX:+UseCountedLoopSafePoints参数强制在可数循环中也放置一个安全点
         *
         */
    }
}
