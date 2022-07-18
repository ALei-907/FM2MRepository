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
        a();
    }
}
