package com.alei.S006_optional;

import java.util.Optional;

/**
 * @Author LeiLiMin
 * @Description: Optional实战
 * @date: 2022/4/8
 */
public class T01_Optional {
    public static void main(String[] args) {
        /**
         * 一种校验空值的方式
         * 1.当出现空值时,希望抛出异常时的操作
         */
    }
    /**
     * 会进行非空校验
     */
    private static void of() {
        Optional.of(null);
    }

    /**
     * 取值操作--
     * 如果optional.value=null，就会抛出异常
     */
    private static void get() {
        Long numA = 1L;
        Long getNum = Optional.of(numA).get();
        System.out.println(getNum);

    }

    /**
     * 如果optional.value=null
     * 返回orElse中的值
     */
    private static void orElse() {
        Long num = null;
        Optional.ofNullable(num).orElse(1L);
    }
}
