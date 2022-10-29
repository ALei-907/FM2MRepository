package com.alei.chapter08.aggregation;

/**
 * @author LeiLiMin
 * @date: 2022/10/29
 */

/**
 * 聚合: 组合的多个对象,可以脱离对方独立存在
 * 举例: 类型与类的继承
 *      子类可以不继承父类,有自己的独立属性和行为
 *      父类也理所当然的独立与子类而存在
 */
public class Aggregation {
    public static void main(String[] args) {
        Sub sub = new Sub();
        System.out.println(sub.a);
        System.out.println(sub.b);
    }
}
class Super{
    int a;
}
class Sub extends Super{
    int b;
}
