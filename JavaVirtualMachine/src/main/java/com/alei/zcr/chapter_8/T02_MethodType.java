package com.alei.zcr.chapter_8;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author LeiLiMin
 * @date: 2022/8/25
 */
public class T02_MethodType {
    public static void main(String[] args) throws Throwable {
        Object obj = new A();
        // 相对于创建了一个函数指针
        MethodType mt = MethodType.methodType(void.class, String.class);
        // 按照Java语言规则,方法的第一个参数是隐式的接受者(this),所以bindTo的参数为obj
        MethodHandle printlnMethod = MethodHandles.lookup().findVirtual(obj.getClass(), "println", mt).bindTo(obj);
        printlnMethod.invokeExact("MethodType");
    }

     static class A {
        public void println(String s) {
            System.out.println(s);
        }
    }
}


