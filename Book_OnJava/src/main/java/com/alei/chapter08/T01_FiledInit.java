package com.alei.chapter08;

import java.util.Objects;

/**
 * @author LeiLiMin
 * @date: 2022/10/28
 */

/**
 * 属性的初始化方式
 *
 * 定义对象初始化: {@link FiledInit#a}         在构造器之前调用
 * 构造器中初始化: {@link FiledInit#FiledInit()}
 * 使用时初始化:   {@link FiledInit#getC()}   延迟初始化
 * 实例初始化:     {@link FiledInit#d}
 */
class FiledInit {

    private int a = 1;

    private String b;

    private String c;

    private String d;


    public FiledInit() {
        System.out.println("Outer Create");
        b = "Outer.b";
    }

    public String getC() {
        if (Objects.isNull(c)) {
            c = "Outer.c";
        }
        return c;
    }

    {
        d = "Outer.d";
    }
}

public class T01_FiledInit {
    public static void main(String[] args) {
    }
}
