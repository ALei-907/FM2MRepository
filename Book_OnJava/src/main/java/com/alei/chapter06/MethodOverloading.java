package com.alei.chapter06;

/**
 * @author LeiLiMin
 * @date: 2022/10/21
 */
public class MethodOverloading {
    public static void f1(char a) {
        System.out.println("char");
    }

    public static void f1(byte a) {
        System.out.println("byte");
    }

    public static void f1(short a) {
        System.out.println("short");
    }

    public static void f1(int a) {
        System.out.println("int");
    }

    public static void main(String[] args) {
        /**
         * 方法重载: 当传入参数不能与方法列表匹配的话,会将参数尝试进行向上转型
         */
        // char的特殊在于,char在方法重载的调用中向上转型为int
        byte a = 'a';
        f1(a);
    }

}

