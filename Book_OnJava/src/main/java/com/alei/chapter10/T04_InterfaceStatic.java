package com.alei.chapter10;

/**
 * @author LeiLiMin
 * @date: 2022/11/7
 */
public class T04_InterfaceStatic {
    public static void main(String[] args) {
        T04_InnerInterfaceSubClass subClass = new T04_InnerInterfaceSubClass();
        T04_InnerInterface twice = new T04_InnerInterface() {
            @Override
            public void execute() {
                System.out.println("匿名内部类1.execute()");
            }
        };

        // 创建接口的四种方式
        // 1.匿名内部类
        // 2.实现子类
        // 3.lambda: 前提得是一个函数式接口
        // 4.方法引用
        T04_InnerInterface.opsRun(subClass, new T04_InnerInterface() {
            @Override
            public void execute() {
                System.out.println("匿名内部类2.execute()");
            }
        }, twice, () -> System.out.println("lambda"));
    }
}

interface T04_InnerInterface {
    void execute();

    static void opsRun(T04_InnerInterface... arg) {
        for (T04_InnerInterface t04_innerInterface : arg) {
            t04_innerInterface.execute();
        }
    }

    static void show() {
        System.out.println("T04_InnerInterface.show()");
    }
}

class T04_InnerInterfaceSubClass implements T04_InnerInterface {

    @Override
    public void execute() {
        System.out.println("T04_InnerInterfaceSubClass.execute()");
    }
}