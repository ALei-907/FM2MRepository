package com.alei;

import java.util.Optional;

/**
 * @author LeiLiMin
 */
public class Init {
    static void a() {
        b();
    }

    static void b() {
        c();
    }

    static void c() {
        Optional<String> stackTop = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(s -> s.map(StackWalker.StackFrame::getMethodName).findFirst());
        System.out.println("方法栈顶: " + stackTop.get());
    }

    public static void main(String[] args) {
        // b 8位,w 16位,l 32 位,q 64位
        /**
         * eg: 0000 00001
         * 大端字节序(0000 00001)：高字节存于内存低地址，低字节存于内存高地址
         * 小端字节序(0001 00000)：高字节存于内存高地址，低字节存于内存低地址
         */
        a();
    }
}
