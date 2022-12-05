package com.alei.chapter08_Executor;

/**
 * @author LeiLiMin
 * @date: 2022/12/5
 */
public class T01_MethodDispatch {
    public static void main(String[] args) {
        A a = new B();
        // 1. b.getObj() => 因为子类重写了该方法a.getObj()的方法签名在子类中得到匹配
        System.out.println("实际调用: " + a.getObj()+" getObj()");
        // 2. a.setObj() => 首先明确B.setObj()并不是重写父类方法,而是重载从父类继承下来的a.setObj()
        //                  如下语句的反编译字节码:
        //                  invokevirtual #10  // Method com/alei/Super_A.setA:(Lcom/alei/Field_Super;)V
        //                  根据方法签名去找父类继承下来的setA(),然后对实参进行向上转型
        //   invokevirtual的执行流程
        a.setObj(1);
    }
}

class A {
    public Object getObj() {
        return "A";
    }

    public void setObj(Object obj) {
        System.out.println("实际调用: A setObj()");
    }
}

class B extends A {
    public Object getObj() {
        return "B";
    }

    public void setObj(Integer obj) {
        System.out.println("实际调用: B setObj()");
    }
}
