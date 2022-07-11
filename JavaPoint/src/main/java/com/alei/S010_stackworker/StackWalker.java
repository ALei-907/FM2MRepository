package com.alei.S010_stackworker;

/**
 * @author LeiLiMin
 */
public class StackWalker {
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
            if("main".equals(stackTraceElement.getMethodName())){
                System.out.println(stackTraceElement.getClassName());
                System.out.println(Class.forName(stackTraceElement.getClassName()));
                System.out.println(stackTraceElement.getFileName());
            }
        }
    }
}
