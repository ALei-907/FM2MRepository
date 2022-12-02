package com.alei.chapter07_classloader;


/**
 * @author LeiLiMin
 * @date: 2022/12/1
 */
public class T01_Boot {

    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * --------------------------------------------------------
         * 从Class.forName触发理解类
         * 1.通过 {@link Reflection#getCallerClass()} 获取调用类的类加载器
         * 2.通过 {@link ClassLoader} 去加载一个类
         * --------------------------------------------------------
         */
        Class<?> T01_boot = Class.forName("com.alei.chapter07_classloader.T01_Boot");
        System.out.println(T01_boot.getName());
    }
    // 随笔:
    // HotSpot采用Oop-Klass模型来表示Java的对象和类,Oop指的是普通对象指针,Klass表示对象的具体类型,之所以这么做是不像让每个对象都包含一个虚方法表
    //   Klass: 直白点说就是通过object.getClass()所得到的数据使用一个C++实例来表示
    //          里边儿记录了父类,父接口,直接父类,子类,虚方法表等object.getClass()通用的信息
}
