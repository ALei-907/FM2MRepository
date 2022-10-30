package com.alei.observer;

import com.alei.observer.subject.DefineSubject;

/**
 * @Author LeiLiMin
 * @Description: 观察者的具体一族
 * @date: 2022/5/15
 */
public abstract class DefineObserver implements Observer {
    DefineSubject subject;

    /**
     * 必须传入所观察的对象
     */
    public DefineObserver(DefineSubject subject) {
        this.subject = subject;
    }
}
