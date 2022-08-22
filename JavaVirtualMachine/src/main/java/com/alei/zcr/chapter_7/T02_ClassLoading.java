package com.alei.zcr.chapter_7;

/**
 * @author LeiLiMin
 * @date: 2022/8/22
 * 类加载时机
 */
public class T02_ClassLoading {
    /**
     * Java-Class的生命周期
     * 加载:
     * 链接:
     * 验证
     * 准备
     * 解析
     * 初始化:
     * 使用:
     * 卸载:
     */
    public static void main(String[] args) {
        // eg1();
        // eg2();
        eg3();
    }

    public static void eg3() {
        /**
         * 无任何输出: 初始化的是[LSuperClass类型,并不是SuperClass
         */
        SuperClass[] superClasses = new SuperClass[10];
    }
    /**
     * 被动引用2: 被final修饰的常量在编译阶段,通过常量传播已经将常量NAME的值直接存储在T02_ClassLoading的常量池中了,
     * 也就是在T02_ClassLoading中并没有SuperClass的引用
     * print: ALei
     */
    public static void eg2() {
        System.out.println(SubClass.NAME);

    }

    /**
     * 被动引用1: 只有直接定义该静态变量的类才会被初始化
     * Print: Super Class Init
     * 1
     */
    public static void eg1() {

        System.out.println(SubClass.value);
    }
}

class SuperClass {
    static {
        System.out.println("Super Class Init");
    }

    public static Integer value = 1;
    public static final String NAME = "ALei";
}

class SubClass extends SuperClass {
    static {
        System.out.println("Sub Class Init");
    }
}
