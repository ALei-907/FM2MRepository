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
        /**
         * eg: 0000 00001
         * 大端字节序(0000 00001)：高字节存于内存低地址，低字节存于内存高地址
         * 小端字节序(0001 00000)：高字节存于内存高地址，低字节存于内存低地址
         */

        // b 8位,w 16位,l 32 位,q 64位
        // ebp: 32位机器的base指针 rbp: 64位机器的base指针
        /**
         * sub l $1,-4(%rbp) : 语义就是将64位机器的base指针减去4byte的偏移量
         * bp: base point(栈底)
         * sp: stack point(栈顶)
         * l $1: 1个32位
         * -4: 内存的存储单位为1byte
         */
        a();
    }
}
