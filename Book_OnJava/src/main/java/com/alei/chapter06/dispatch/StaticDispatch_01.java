package com.alei.chapter06.dispatch;

import java.io.Serializable;

/**
 * @author LeiLiMin
 * @date: 2022/10/23
 */
public class StaticDispatch_01 {
    // public void showChar(char c){
    //     System.out.println("Is char c");
    // }
    // public void showChar(short s){
    //     System.out.println("Is short s");
    // }
    // public void showChar(int i){
    //     System.out.println("Is int i");
    // }
    // public void showChar(Character character){
    //     System.out.println("Is character ch");
    // }
    // public void showChar(Serializable serializable){
    //     System.out.println("Is serializable serial");
    // }
    // public void showChar(Comparable<Character> comparable){
    //     System.out.println("Is comparable comparable");
    // }
    public void showChar(char... chars){
        System.out.println("Is chars chars");
    }
    // public void showChar(Character... chars){
    //     System.out.println("Is Character chars");
    // }
    //
    public void showChar(int... ints){
        System.out.println("Is ints chars");
    }

    public static void main(String[] args) {
        StaticDispatch_01 staticDispatch_01 = new StaticDispatch_01();
        // char -> int -> character -> Serializable | Comparable<Character> -> char... chars -> Character... chars -> int... ints
        /**
         * 依次向下注释重载方法时的输出
         * char: 实参类型与形参类型完全匹配
         * int: 为什么不是short?
         *      > java中的数值类型均为有符号数
         *      > char大小为2字节,范围: 0～65535
         *      > byte大小为2字节,范围: -32768~32767
         *      > 结论: 虽然short与char所占大小一致,但是二者的所表达的意义确实不一样的。
         *             所以二者之间的类型切换是一种危险的行为,所以直接升至int进行输出
         * character: 对于基础数据类型,包装类型将是下一级的自动类型转换
         * Serializable | Comparable<Character>:
         *      > 因为character实现了这2个接口,特别的是当达到这个阶段,且重载方法中同时存在这2个参数的方法,就会遇到匹配方法失败的编译错误
         * char... chars
         *      > 可变参数是这几种方式中最后的选择
         * 特别的:
         *      > char... chars      与 Character... chars 不能同时存在,复杂无法判断
         *      > Character... chars 与 int... ints        不能同时存在,复杂无法判断
         */
        staticDispatch_01.showChar('a');
    }
}
