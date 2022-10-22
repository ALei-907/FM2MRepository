package com.alei.chapter06;

/**
 * @author LeiLiMin
 * @date: 2022/10/21
 */
public class MethodInvoke {
    /**
     * 方法调用:
     * 非虚方法: 在编译期可以唯一确定的方法符号引用[init,clinit,私有方法,父类方法,static]
     * invokestatic: 调用静态方法,因为该方法与类相关与对象无关,所以很好理解它的唯一性
     * invokespeical: 调用[init,clinit,私有方法,父类方法]
     *                根据汇编显示,并不能确定private修饰的方法通过该指令调用
     *                但是可以得出的结论是,[init,clinit,私有方法]是可以在编译期唯一确定的
     *                通过汇编得出的结论如果A继承自D,B继承自A,那么B所调用的父类方法是其直接父类A的方法而不是D,所以调用父类方法是可以唯一确定的
     */

    public MethodInvoke() {
    }

    public static void say() {
        System.out.println("Say");
    }

    private void privateSay() {
        System.out.println("private say");

    }

    public void publicSay() {
        System.out.println("public say");
    }

    public static void main(String[] args) {
        new B().say();
    }
}

class D {
    public void say() {
        System.out.println("D");
    }
}

class A extends D {
    public void say() {
        System.out.println("A");

    }
}

class B extends A {
}
