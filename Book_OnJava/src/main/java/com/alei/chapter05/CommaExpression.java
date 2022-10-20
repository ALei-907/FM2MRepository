package com.alei.chapter05;

/**
 * @author LeiLiMin
 * @date: 2022/10/20
 */
public class CommaExpression {
    public static void main(String[] args) {
        /**
         * java中for是运行使用逗号表达式的唯一地方
         */
        for (int i = 0, j = i + 1; i < 5; i++, j++) {
            System.out.println(i + " : " + j);
        }

        /**
         * 类goto的标签
         * 标签之后必须紧跟循环,与break与contonue配合使用
         */
        outter:
        while (true) {
            inner:
            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    System.out.println("continue outter");
                    continue outter;
                }
            }
        }
    }
}
