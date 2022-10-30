package com.alei.strategy.behavior;


/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/13
 */
public class EatMore implements EatBehavior {
    @Override
    public void eat() {
        System.out.println("吃的多");
    }
}
