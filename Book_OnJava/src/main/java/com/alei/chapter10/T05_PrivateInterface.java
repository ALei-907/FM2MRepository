package com.alei.chapter10;

/**
 * @author LeiLiMin
 * @date: 2022/11/10
 */
public class T05_PrivateInterface {
    public static void main(String[] args) {
        Outer outer = new Outer();

        // Outer.A a = outer.getA(); // Error 返回私有接口类型
        Outer.ImplA1 implA1 = (Outer.ImplA1) outer.getA(); // 使用具备访问权限的类型是可行的

        outer.invokeA(outer.getA()); // 要想使用该私有接口类型那只能由具备该接口访问权限的Outer的方法来调用
    }
}

class Outer {
    /**
     * 私有接口
     */
    private interface A {
    }

    /**
     * 内部类实现私有接口
     */
    class ImplA1 implements A {

    }

    public A getA() {
        return new ImplA1();
    }

    public void invokeA(A a) {
        System.out.println("invokeA");
    }
}
