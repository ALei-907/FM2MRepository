package com.alei.zcr.chapter_3;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author LeiLiMin
 * @date: 2022/8/16
 * 对象已死？
 */
public class T02_ObjectIsDied {
    public static void main(String[] args) {
        refrenceCounter();
        gcRoot();
        objectsRefrence();
        gcInMethodArea();
    }

    /**
     * 为什么不采用简单的"引用计数"来进行判定对象是否消亡
     * : 如下所示,a和b互相持有引用,那么对应的a和b的引用计数都为1。也就不可回收了
     */
    public static void refrenceCounter() {
        A a = new A();
        A b = new A();
        a.field = b;
        b.field = a;
    }

    /**
     * HotSpot采用可达性分析算法来进行判定是否对对象进行回收
     * : 也就是从GCRoots出发,根据引用关系向下搜索,搜索的路径称为引用链
     * 如果某个对象到GCRoots之间没有任何的引用链相连,就证明该对象不可能再被使用
     * : 什么对象可以作为GCRoots
     * * Java需虚拟机栈-栈帧: 引用的对象(参数,局部变量,临时变量)
     * * 方法区: 类静态属性引用的对象
     * 常量引用的对象,例如字符串常量池里的引用
     * * 本地方法栈: 所引用的对象
     * * Java虚拟机内部的引用
     * * 被synchronized持有的对象
     * * JMXBean,JVMTI中注册的回调,本地代码缓存
     */
    public static void gcRoot() {
    }

    /**
     * 对象的引用
     */
    public static void objectsRefrence() {
        // 强引用: 如果发生GC时,该对象是根可达对象,就不进行回收,否则就进行回收
        Object o = new Object();

        // 软引用: 在系统发生OOM之前才进行回收
        SoftReference<Object> softReference = new SoftReference<>(o);

        // 弱引用: GC时,一旦发现立即回收
        WeakReference<Object> weakReference = new WeakReference<>(o);

        // 虚引用: 最弱的引用关系,不对GC有任何影响。也无法获取到对象实例
        PhantomReference<Object> phantomReference = new PhantomReference<>(o,new ReferenceQueue<>());

    }

    /**
     * 方法区的回收
     */
    public static void gcInMethodArea() {
        /**
         * 苛刻的条件
         * 1: 该类的所有实例都被回收,即java堆中不存在该类及任何派生子类的实例
         * 2: 加载该类的类加载器已经被回收
         * 3: 该类对应的java.lang.Class对象没有在任何地方被引用过,无法在任何地方通过发射访问该类的方法
         */
    }
}


class A {
    Object field;
}
