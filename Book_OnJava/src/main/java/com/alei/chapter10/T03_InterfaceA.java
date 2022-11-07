package com.alei.chapter10;

/**
 * @author LeiLiMin
 * @date: 2022/11/7
 */
public interface T03_InterfaceA {
    /**
     * 接口的抽象方法,默认为public => 也就是即使不定义也为public
     */
    void defaultM();

    /**
     * 接口的default()
     * 如果一个类实现了2个接口,且这2个接口的default()的方法签名一致 => 导致方法冲突
     * 实现类应当重写该方法(要么重新定义方法体,要么调用父接口之一的实现)
     */
    default void defaultDefaultM() {
        System.out.println("T03_InterfaceA.defaultDefaultM");
    }
}
