package com.alei.observer;

import com.alei.observer.subject.DefineSubject;
import com.alei.observer.subject.Subject;

/**
 * @Author LeiLiMin
 * @Description: 观察者的具体实现
 * @date: 2022/5/15
 */
public class DefineClass extends DefineObserver {
    public DefineClass(DefineSubject subject) {
        super(subject);
    }

    @Override
    public void doSomething() {
        System.out.println("[Observer]接收到观察对象("+this.subject.getSubjectName()+")的变化通知");
    }
}
