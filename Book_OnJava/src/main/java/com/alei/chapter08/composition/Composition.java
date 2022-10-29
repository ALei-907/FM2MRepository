package com.alei.chapter08.composition;

/**
 * @author LeiLiMin
 * @date: 2022/10/29
 */

/**
 * 组合: 组合的多个对象不能独立存在
 * 例子: ClassA是ClassB的属性之一,对于ClassB而言,它是不能够独立与ClassA而存在的.
 *      如果没有ClassA,那么ClassB就不能如它一开始所定义的那样而存在了
 */
public class Composition {
    public static void main(String[] args) {
        ClassB classB = new ClassB();
    }
}

class ClassA {

}

class ClassB {
    int a;
    ClassA classA;
}
