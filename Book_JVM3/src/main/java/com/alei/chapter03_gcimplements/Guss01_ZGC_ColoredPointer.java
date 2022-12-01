package com.alei.chapter03_gcimplements;

/**
 * @author LeiLiMin
 * @date: 2022/10/24
 */
public class Guss01_ZGC_ColoredPointer {
    /**
     * resource: https://tech.meituan.com/2020/08/06/new-zgc-practice-in-meituan.html
     */
    /**
     * 关于ZGC染色指针的猜想:
     * <p>
     * 对于64位操作系统,内存地址的使用并未完全的使用掉64位
     * 而表示使用48位表示虚拟内存地址,52位表示物理内存地址
     * <p>
     * 对于ZGC而言,将48位的虚拟内存地址仅使用低42位来表示虚拟内存地址,而剩余的高位将进程虚拟内存空间划分成几个不同的区域[java heap,M0,M1,Remapped]
     * 根据表述: [M0,M1,Remapped]唯一对应一个物理地址
     * 猜想: 1.这样设计的好处是CPU所使用到的都是可执行目标文件中的虚拟地址,这样就可以让不同的进程使用到同一种分区设计
     * 2.Java Heap中的虚拟内存地址可以通过MMU获取到物理内存上对应的数据
     * 3.[M0,M1,Remapped]的低42位的虚拟地址数据与javaHeap中的保持一致
     * 4.在ZGC的GC过程中(标记,转移)对这3个分区[M0,M1,Remapped]的虚拟地址前几位设置标识,表示对象是否活跃
     */

}


