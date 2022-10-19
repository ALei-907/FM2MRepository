package com.alei.S014_package;

/**
 * @author LeiLiMin
 * @date: 2022/10/19
 */
public class Package {
    public static void main(String[] args) {
        /**
         * 使用new Integer()创建的对象必不相等
         */
        Integer a = new Integer(1);
        Integer b = new Integer(1);
        System.out.println(a == b);

        /**
         *
         * 使用Integer.valueOf(128)创建的对象:-128~127内相等,其他的不想等
         * 这种方式使用到了享元模式
         *  > 相当于: 工厂模式+缓存 > 注册式单例模式
         *           由工厂生产出的对象缓存起来,下次进行创建相同的对象就不进行创建,而是取出缓存对象
         *           降低对内存的消耗
         *
         */
        Integer c = Integer.valueOf(128);
        Integer d = Integer.valueOf(128);
        System.out.println(c == d);
    }
}
