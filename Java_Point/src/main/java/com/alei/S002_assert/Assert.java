package com.alei.S002_assert;

/**
 * @Author LeiLiMin
 * @Description: Java断言机制
 * @date: 2022/4/7
 */
public class Assert {
    public static void main(String[] args) {
        /**
         * try-catch: 是可以在预知错误的情况下给出一种处理方式来让程序在遇到非正确结果时也能执行对于的逻辑
         * assert   : 轻量级的代码控制
         *            1.代码简介明了
         *            2.可以用在出现非预期结果时终止程序运行的逻辑
         */


        // 1.启用断言,需要添加VM Options: -ea
        // 2.assert Boolean: false则断言判断失败
        //                   true 则断言判断成功,程序继续执行
        assert false;
        System.out.println("asser success!");
    }
}
