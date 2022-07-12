package com.alei.S010_stackworker;

/**
 * @author LeiLiMin
 */
public class Test_StackWalker_DoMain {
    public static void main() throws ClassNotFoundException {
        Class<?> main = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk((s) -> s.filter(e -> e.getMethodName().equals("main")).findFirst().map(
                        StackWalker.StackFrame::getDeclaringClass))
                .orElseThrow(ClassNotFoundException::new);
        System.out.println(main);
    }
}

