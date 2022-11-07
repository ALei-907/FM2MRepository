package com.alei.chapter10;

public interface T03_InterfaceB {
    /**
     * 接口的抽象方法,默认为public => 也就是即使不定义也为public
     */
    void defaultM();

    default void defaultDefaultM() {
        System.out.println("T03_InterfaceB.defaultDefaultM");
    }
}
