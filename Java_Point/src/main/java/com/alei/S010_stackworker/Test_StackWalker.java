package com.alei.S010_stackworker;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author LeiLiMin
 */
public class Test_StackWalker {
    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * 1.想要在业务处理中获取堆栈信息,需借助RunTimeException来进行获取
         * : 这样在业务代码中就显得很不雅观,因为这个RunTimeException与业务无关
         * : 下面的Demo是来自Spring的deduceMainApplicationClass()方法
         * 2.假设想要针对堆栈信息进行业务告警等需求,所以获取堆栈还是有所作用的
         * : JDK17新特性(StackWalker) -> https://openjdk.org/jeps/259
         *   通过简洁的方式来进行获取堆栈信息,TODO：得空的时候学习下
         */
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                System.out.println(stackTraceElement.getClassName());
                System.out.println(Class.forName(stackTraceElement.getClassName()));
                System.out.println(stackTraceElement.getFileName());
            }
        }
        System.out.println("=========================[StackWalker,since JDK 9]=========================");
        /**
         * API概述: https://openjdk.org/jeps/259
         * 构造参数解析: {@link StackWalker.Option.RETAIN_CLASS_REFERENCE} 该参数使得流中元素可直接获取说明类的Class对象,旧版本的操作还需一步Class.forName()
         *             {@link StackWalker.Option.SHOW_REFLECT_FRAMES}    获取到反射的隐藏栈帧
         *             {@link StackWalker.Option.SHOW_HIDDEN_FRAMES}     获取到所有的隐藏栈帧
         */
        Class<?> main = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk((s) -> s.filter(e -> e.getMethodName().equals("main")).findFirst().map(
                        StackWalker.StackFrame::getDeclaringClass))
                .orElseThrow(ClassNotFoundException::new);
        System.out.println(main);
        System.out.println("=========================[doMain]=========================");
        Test_StackWalker_DoMain.main();

    }
}
