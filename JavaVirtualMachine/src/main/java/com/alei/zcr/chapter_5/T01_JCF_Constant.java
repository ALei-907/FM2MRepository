package com.alei.zcr.chapter_5;

/**
 * @author LeiLiMin
 * @date: 2022/8/21
 * Java Class文件结构
 */
public class T01_JCF_Constant {
    /**
     * Class文件中,在cafe baby 次版本 主版本 之后有2个字节的长度表示常量池的数量: 可以推断一个类可以定义的常量数至多为2^16
     *                                                                  且常量是由1开始计数的,不是0
     * Class文件中,方法、字段都需要引用CONSTANT_Utf8_info型常量(u2类型),也就是16位最大64kb的英文字符
     *
     * javap xx.class  分析该class文件字节码的工具
     */
}
