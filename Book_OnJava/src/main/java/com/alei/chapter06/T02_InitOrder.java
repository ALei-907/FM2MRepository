package com.alei.chapter06;

/**
 * 类加载初始化顺序
 * 1.静态代码块,静态字段的初始化执行时机: 类第一次被加载进JVM时[调用类静态字段,类静态非法,类构造非法]
 * 或者通过其他方式[手动调用类加载器加载,反射]
 * 2.实例代码块,实例字段的初始化执行时机: 生产对象时[调用构造方法,即使是通过反射也是invoke了构造非法]
 * 3.类总体初始化执行顺序: 静态属性初始化执行 > 实例属性初始化执行 > 构造非法执行
 * 4.JVM会分别合并静态属性,和实例属性,单并不会对某一类型的属性进行内部分类排序: 比如不会对静态属性中的静态代码块与静态字段归并后执行
 * @author LeiLiMin
 * @date: 2022/10/25
 */
public class T02_InitOrder {
    InnerClass inner01 = new InnerClass(1);
    static InnerClass inner11 = new InnerClass(11);

    public T02_InitOrder() {
        System.out.println("T02_InitOrder Construct...");
    }
    InnerClass inner02 = new InnerClass(2);

    static {
        System.out.println("Invoke static block...");
        System.out.println(inner11);
        // System.out.println(inner12); // -非法的前向引用,因为代码块执行时,inner11还未被发现
    }
    static InnerClass inner12 = new InnerClass(12);

    public static void main(String[] args) {
        T02_InitOrder t02_initOrder = new T02_InitOrder();
    }
}

class InnerClass {
    private final int num;

    public InnerClass(int num) {
        this.num = num;
        System.out.println("InnerClass Construct: num = " + this.num);
    }

    @Override
    public String toString() {
        return "InnerClass.num = " + num;
    }
}
