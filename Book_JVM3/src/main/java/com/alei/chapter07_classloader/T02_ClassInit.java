package com.alei.chapter07_classloader;

/**
 * @author LeiLiMin
 * @date: 2022/12/4
 */
public class T02_ClassInit {
    public static void main(String[] args) {
        // Super被初始化了,但Sub并没有被初始化 => 只有直接定义该静态字段的类才会被初始化,所以Sub并不会被初始化
        System.out.println("------- 被动引用一 -------");
        System.out.println(T02_ClassInit_Sub.anInt);

        // Sub仍然没有被初始化,在此被初始化的是[Lcom.alei.chapter07_classloader],而不是T02_ClassInit_Sub
        System.out.println("------- 被动引用二 -------");
        T02_ClassInit_Sub[] subArr = new T02_ClassInit_Sub[3];

        // Sub仍然没有被初始化,因为常量在编译阶段就会存入调用类的常量池中,本质上没有直接调用到定义类
        System.out.println("------- 被动引用三 -------");
        System.out.println(T02_ClassInit_Sub.anFinalInt);
    }
}

class T02_ClassInit_Super {
    static {
        System.out.println("T02_ClassInit_Super init");
    }

    static int anInt = 1;

}

class T02_ClassInit_Sub extends T02_ClassInit_Super {
    public static final int anFinalInt = 2;

    static {
        System.out.println("T02_ClassInit_Sub init");
    }
}
