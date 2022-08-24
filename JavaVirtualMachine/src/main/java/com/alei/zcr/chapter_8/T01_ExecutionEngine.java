package com.alei.zcr.chapter_8;

/**
 * @author LeiLiMin
 * @date: 2022/8/24
 */
public class T01_ExecutionEngine {
    public static void main(String[] args) {
        /**
         * 非虚方法: 静态方法,私有方法,被final修饰的方法,实例构造器,父类方法
         * 虚方法: 即非私有非final的"对象方法"。会有"分派"选择
         */
    }

    public void test() {
        byte[] bytes = new byte[64 * 1024 * 1024];
        // bytes并不会被回收,因为bytes仍然在局部变量表中占有slot,仍然作为GC Roots
        System.gc();

        {
            byte[] bytes2 = new byte[64 * 1024 * 1024];
        }
        // bytes2回收成功,因为当前已经离开bytes2的作用域,且a=1的行为发生局部变量表-槽位复用
        int a=1;
        System.gc();

    }
}
