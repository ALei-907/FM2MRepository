package com.alei.chapter07.pb;

import com.alei.chapter07.pa.PaClassA;

// import static com.alei.chapter07.pa.PaClassA.*; /* 导入不同包的静态属性和方法 */

/**
 * @author LeiLiMin
 * @date: 2022/10/26
 */
public class PbClassA extends PaClassA {
    public static void main(String[] args) {
        System.out.println(fStaticClassA);
        publicStaticMethod();
        protectedStaticMethod();  // 不同包: 被default修饰的属性和方法仍然可以被子类所使用到
        // defaultStaticMethod(); // 不同包: 被default修饰的属性和方法不能被访问到
        // privateStaticMethod(); // 除了自己谁来都不好使
        // PaClassC.defaultClzPublicMethod();  // 不同中: 类为default,方法为public,不可见,以外层修饰符为主

    }
}
