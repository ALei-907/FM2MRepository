package com.alei.S010_stackworker;

/**
 * @author LeiLiMin
 */
public class Test_StackWalker_DoMain {
    public static void main()   {
        // 获取到了当前堆栈信息
        // 调用链: Test_StackWalker_DoMain  -> 栈顶 0号元素
        //        Test_StackWalker         -> 栈底 1号元素
        Object main = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                        .walk((s) -> s.map(StackWalker.StackFrame::getDeclaringClass).skip(1).findFirst());

        System.out.println(main);
    }
}

