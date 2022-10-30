package com.alei.strategy.behavior;


/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/13
 */
public class RunSlow implements RunBehavior {
    @Override
    public void run() {
        System.out.println("跑的慢");
    }
}
