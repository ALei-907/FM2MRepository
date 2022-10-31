package com.alei.observer.subject;

import com.alei.observer.Observer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description: Subject下的具体一族
 * @date: 2022/5/15
 */
public abstract class DefineSubject implements Subject {
    /**
     * Observer集合,集中管理
     */
    Map<Observer, Observer> observerMap = new HashMap<>();

    private String subjectName;

    public DefineSubject(String subjectName) {
        this.subjectName = subjectName;
    }

    public Map<Observer, Observer> getObserverMap() {
        return observerMap;
    }

    public void setObserverMap(Map<Observer, Observer> observerMap) {
        this.observerMap = observerMap;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
