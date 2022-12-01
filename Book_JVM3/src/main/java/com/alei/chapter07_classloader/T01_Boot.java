package com.alei.chapter07_classloader;


/**
 * @author LeiLiMin
 * @date: 2022/12/1
 */
public class T01_Boot {

    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * --------------------------------------------------------
         * 从Class.forName触发理解类加载过程
         * 1.通过 {@link Reflection#getCallerClass()} 获取调用类的类加载器
         * 2.通过 {@link ClassLoader} 去加载一个类
         * --------------------------------------------------------
         */
        Class<?> T01_boot = Class.forName("com.alei.chapter07_classloader.T01_Boot");
        System.out.println(T01_boot.getName());
    }




}
