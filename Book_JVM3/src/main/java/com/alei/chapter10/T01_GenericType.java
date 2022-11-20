package com.alei.chapter10;

import java.util.List;

/**
 * @author LeiLiMin
 * @date: 2022/11/20
 */
// Java 泛型

/**
 * Java的选择以类型擦除来实现泛型
 * 1.什么是裸类型: 相对于ArrayList< String >,ArrayList就是裸类型
 * 2.什么是类型擦除: 就是携带泛型的信息仅存在与程序的源码,编译之后将所有的泛型全部替换为裸类型
 *                 并且在对应的地方进行了强制类型转换
 * 3.拆箱装箱的出现: 由于存在类型擦除,并且基础数据类型无法和Object之间强壮(int)和(Object)
 *                所以出现了基础数据类型的包装类
 */
public class T01_GenericType {
    public static <T> String param(T t) {
        return t.getClass().getName();
    }


    public static void main(String[] args) {
        System.out.println(T01_GenericType.param(new T01_GenericType()));
    }
}
