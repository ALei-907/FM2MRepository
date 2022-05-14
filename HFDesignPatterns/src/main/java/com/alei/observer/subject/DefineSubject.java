package com.alei.observer.subject;

import com.alei.observer.Observer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description: Subject下的具体一族
 * @date: 2022/5/15
 */
@Getter
@Setter
public abstract class DefineSubject implements Subject {
    /**
     * Observer集合,集中管理
     */
    Map<Observer, Observer> observerMap = new HashMap<>();

    private String subjectName;

    public DefineSubject(String subjectName) {
        this.subjectName = subjectName;
    }
}
