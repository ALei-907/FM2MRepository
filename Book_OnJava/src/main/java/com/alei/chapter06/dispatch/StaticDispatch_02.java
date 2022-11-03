package com.alei.chapter06.dispatch;

/**
 * @author LeiLiMin
 * @date: 2022/10/23
 */
public class StaticDispatch_02 {
    static class Father {
    }

    static class Son extends Father {
    }

    static class Executor {
        public void execute(Father father) {
            System.out.println("execute father");
        }

        public void execute(Son son) {
            System.out.println("execute son");
        }
    }

    /**
     * 静态分派:
     * 变量son:   实际类型为Son,静态类型为Father
     * 变量Father:实际类型为Father,静态类型为Father
     * 静态类型为编译期间虚拟机就可以知道的信息,所以案例中方法的接受者是确定的(Executor),方法的参数类型也是确定的(Father)
     * 所以虚拟机在编译期间是根据参数的静态类型来选择重载版本的
     */
    public static void main(String[] args) {
        Father son = new Son();
        Father father = new Father();
        Executor executor = new Executor();
        // execute father
        // execute father
        executor.execute(son);
        executor.execute(father);

    }
}
