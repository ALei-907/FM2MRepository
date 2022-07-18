package com.alei;

/**
 * @author LeiLiMin
 */
public class Init {
    static void a(){b();}
    static void b(){c();}
    static void c(){
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        System.out.println("方法栈顶:"+stackTrace[0].getMethodName());
    }
    public static void main(String[] args) {
        a();
    }
}
