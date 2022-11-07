package com.alei.chapter10.packageb;

import com.alei.chapter10.T03_InterfaceA;
import com.alei.chapter10.T03_InterfaceB;

/**
 * @author LeiLiMin
 * @date: 2022/11/7
 */
public class T01_AbstractClassPb implements T03_InterfaceA, T03_InterfaceB {
    @Override
    public void defaultM() {
    }

    @Override
    public void defaultDefaultM() {
        T03_InterfaceA.super.defaultDefaultM();
    }
}
