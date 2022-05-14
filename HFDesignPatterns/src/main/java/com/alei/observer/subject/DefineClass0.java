package com.alei.observer.subject;

import com.alei.observer.Observer;

/**
 * @Author LeiLiMin
 * @Description: DefineSubject一族的具体实现
 * @date: 2022/5/15
 */
public class DefineClass0 extends DefineSubject {
    public DefineClass0(String subjectName) {
        super(subjectName);
    }

    @Override
    public void registerObserver(Observer observer) {
        observerMap.put(observer, observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerMap.remove(observer);
    }

    /**
     * 实际调度
     */
    @Override
    public void notifyObserver() {
        for (Observer observer : observerMap.keySet()) {
            observer.doSomething();
        }
    }
}
