package com.alei.strategy.behavior;


/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/13
 */
public class RunFaster implements RunBehavior {
    @Override
    public void run() {
        System.out.println("跑得快");
    }
}
