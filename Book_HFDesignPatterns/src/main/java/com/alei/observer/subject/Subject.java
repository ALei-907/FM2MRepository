package com.alei.observer.subject;

import com.alei.observer.Observer;

/**
 * 发布者顶级接口
 */
public interface Subject {
    /**
     * 注册观察者
     * @param observer 观察者
     */
    void registerObserver(Observer observer);

    /**
     * 移除观察者
     * @param observer 观察者
     */
    void removeObserver(Observer observer);

    /**
     * 通知所有观察者
     */
    void notifyObserver();
}
