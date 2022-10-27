package com.alei.chapter07.pa;

/**
 * @author LeiLiMin
 * @date: 2022/10/26
 */
public class PaClassB {
    public static void main(String[] args) {
        PaClassA.defaultStaticMethod();     // 同包中: 可以调用被"default"修饰的属性和方法
        // PaClassA.privateStaticMethod();  // 除了自己,谁来都不好使
        PaClassC.defaultClzPublicMethod();  // 同包中: 类为default,方法为public,仍然可见
    }
}
