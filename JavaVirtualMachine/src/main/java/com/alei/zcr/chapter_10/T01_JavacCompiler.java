package com.alei.zcr.chapter_10;

import java.util.List;

/**
 * @author LeiLiMin
 * @date: 2022/8/29
 */
public class T01_JavacCompiler {
    // Javac是一种前端编译器(将.java文件转换为.class文件)

    /**
     * JavaC执行流程
     * 1)初始化插入式注解处理器
     * 2)解析与填充符号表
     * : 词法,语法分析.构造抽象语法树(AST)
     * : 填充符号表=> 产生符号地址与符号信息
     * 3)插入式注解处理器(eg: Lombok)
     * : 在注解处理的过程中产生新的符号信息就回到上一步,继续填充符号信息
     * 4)分析与字节码生成过程
     * : 标注检查。对语法的静态信息进行检查(变量使用之前是否申明,变量与福建之间的数据类型是否匹配)
     * : 数据流与控制流检查,对程序的上下文逻辑进行进一步验证(局部变量使用前是否赋值等)
     * 局部变量中无论是否被final修饰对于最后生成的字节码都是一样的.在这里final起到的作用是让前端编译器确认该局部变量不可变
     * : 解语法糖
     * : 生成字节码
     */

    public static void main(String[] args) {

    }
    // private void m1(List<Integer> list) {
    // }
    //
    // private int m1(List<String> list) {
    //     return  1;
    // }

}
