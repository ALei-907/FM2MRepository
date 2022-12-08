package com.alei.chapter08_Executor;

import java.util.Collection;

/**
 * @author LeiLiMin
 * @date: 2022/12/5
 */
public class T01_MethodDispatch {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("I am begin ");
        Thread.sleep(10*1000);
        A a = new B();
        // 1. b.getObj() => 因为子类重写了该方法a.getObj()的方法签名在子类中得到匹配
        System.out.println("实际调用: " + a.getObj() + " getObj()");
        // 2. a.setObj() => 首先明确B.setObj()并不是重写父类方法,而是重载从父类继承下来的a.setObj()
        //                  如下语句的反编译字节码:
        //                  invokevirtual #10  // Method com/alei/Super_A.setA:(Lcom/alei/Field_Super;)V
        //                  根据方法签名去找父类继承下来的setA(),然后对实参进行向上转型
        //   invokevirtual的执行流程=> 找到栈顶的第一个元素所指向的实际类型
        //                        => 根据描述符和简单名称现在实际类型中寻找方法
        //                        => 否则向上从父类中寻找
        //
        a.setObj(1);
    }
}

class A<T> {
    public Object getObj() {
        return "A";
    }

    public void setObj(Object obj) {
        System.out.println("实际调用: A setObj()");
    }

    public void toList(Collection<T> t) {
    }
}

class B extends A {
    public Object getObj() {
        return "B";
    }

    public void setObj(Integer obj) {
        System.out.println("实际调用: B setObj()");
    }

    @Override
    public void setObj(Object obj) {
        super.setObj(obj);
    }

    // 非泛型方法可以覆盖泛型方法
    @Override
    public void toList(Collection t) {
    }
}
