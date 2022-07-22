package com.alei.S011_DoubleBrace;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/7/22
 */
@Data
class InnerClass {
    private Integer a;
}

@Data
class Outer{
    private ThreadLocal<Map<String, Object>> tl = new ThreadLocal<>();

    public Integer OuterValue = 2;

    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("random", new Random());
        map.put("this", this);
        tl.set(map);
    }
}


public class DoubleBrace {
    private ThreadLocal<Map<String, Object>> tl = new ThreadLocal<>();

    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("random", new Random());
        map.put("this", this);
        tl.set(map);
    }

    public Integer OuterValue = 1;


    /**
     * 匿名内部类 + 初始非静态代码块。
     * 第一个大括号是创建一个继承当前对象的匿名内部类。
     * 第二个大括号是在这个匿 名内部类中创建一个非静态初始化代码块，最后new 的操作是得到当前对象的子类 （匿名内部类）然后向上转型为当前对象的引用。
     * 缺陷：
     * <p>
     * 类中每一处双大括号的引用都会产生一个.class文件，导致堆内存会有这些文件的引用，增加类加载器负担。
     * 使用双大括号初始化所创建的匿名内部类会持有当前对象的引用，会把当前对象的实例暴露出去，造出内存泄漏
     */

    public static void main(String[] args) {
        DoubleBrace DoubleBrace = new DoubleBrace();
        Outer outerClass = new Outer();
        InnerClass innerClass = new InnerClass() {{
            /**
             * 1.如果内部代码块引用到了其他的对象的值,就会将那个对象作为最后生成的匿名内部类的属性填充
             * 2.如果未引用其他对象的值,就会生成原生的匿名内部类
             */
            setA(((DoubleBrace) DoubleBrace.tl.get().get("this")).OuterValue);
            // setA(((Outer) outerClass.getTl().get().get("this")).OuterValue);
            // setA(1)
        }};

        System.out.println(innerClass);
    }
}
