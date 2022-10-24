package com.alei.chapter06;

/**
 * @author LeiLiMin
 * @date: 2022/10/24
 */
public class MethodReloading {
    // public void function(){}
    // public int function(){return 1;}
    public static void main(String[] args) {
        /**
         * 相同的方法名可以根据不同顺序,不同类型的参数进行重载
         * 为什么没有通过返回值重载的方式呢?
         * 1.从技术实现上是可行的,例如 int a=function(); 可以定义为调用注释处第二个方法
         *                               function(); 可以定义为调用注释处第一个方法
         * 2.从理解上来讲是模糊的,调用函数可以仅仅是调用方法而不使用方法的返回值(副作用),
         *   如果强行通过赋值变量的类型来进行区分的话,又生硬的在模块中添加了可能使用不到的变量
         *   如果不使用变量来明确区分的话,对于 function()的界定又是模糊的,并不能明确知道所要调用方法的版本
         */
    }
}
