package com.alei.chapter07.pa;

/**
 * @author LeiLiMin
 * @date: 2022/10/26
 */
public class PaClassA {
    public static int fStaticClassA;

    public static void publicStaticMethod() {
        System.out.println("invoke PaClassA.publicStaticMethod");
    }

    static void defaultStaticMethod() {
        System.out.println("invoke PaClassA.defaultStaticMethod");
    }

    protected static void protectedStaticMethod() {
        System.out.println("invoke PaClassA.protectedStaticMethod");
    }

    private static void privateStaticMethod() {
        System.out.println("invoke PaClassA.privateStaticMethod");
    }
}
