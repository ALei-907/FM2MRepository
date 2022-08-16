package com.alei.zcr.chapter_3;

/**
 * @author LeiLiMin
 * @date: 2022/8/16
 * GC算法
 */
public class T03_GCArithmetic {
    /**
     * MinorGC/YonugGC: 年轻代GC
     * MajorGC/OldGC: 老年代GC
     * MixedGC: 混合GC,所有新生代+部分老年代
     * FullGC: 整堆GC
     */
    public static void main(String[] args) {
        /**
         * 标记-清除算法: 优点就是简单,
         *              缺点就是碎片化,效率不高,至少便利2次,一次标记,一次清除
         *
         * 标记-复制算法: 优点就是也比较简单
         *              缺点就是浪费了一半的空间,
         *                 还有就是如果存在大量的存活对象,那么对象的复制移动也是一个不小的开销
         *
         * 标记-整理算法: 边标记边整理,将无用对象清除,有用对象整齐的排布在内存区域的一端
         *              这是一个折中的方案,建立在不浪费空间,不碎片化空间的基础上
         */
    }
}
