package com.alei.observer;

import com.alei.observer.subject.DefineClass0;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/15
 */
public class Demo {
    /**
     * 观察者模式的3个要点
     * 被观察者: 要具备管理观察者的能力
     * 被观察者: 可以适度暴露所持有的数据
     *          * 可以用Dto包裹想要暴露的数据,这样以后Invoke Observer时就只需要改变Dto。但问题是,每个Observer想要的数据是不一样的
     *          * 通过暴露get方法将数据暴露数据,这样只需要告知Observer,数据发生变化就行,由Observer自己拉取数据
     *            可以增强的地方再去，可以通过一定的逻辑将保证数据多线程安全,例如将Set方法的返回结果改为一个深拷贝对象
     * Observer: 持有被观察者的引用，这样方便调用数据
     */
    public static void main(String[] args) {
        DefineClass0 subject = new DefineClass0("test");
        subject.registerObserver(new DefineClass(subject));
        subject.notifyObserver();
    }
}
