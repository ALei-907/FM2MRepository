package com.alei.strategy;

import com.alei.strategy.behavior.EatLess;
import com.alei.strategy.behavior.RunFaster;
import com.alei.strategy.behavior.RunSlow;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/13
 */
public class Demo {
    /**
     * 1.Run和Eat不再作为Human的具体方法实现
     *   而是通过Behavior接口进行了抽象 -> 以后可以支持更多的策略
     *
     * 2.通过SetBehavior可以动态的对运行中的Human修改行为
     */
    public static void main(String[] args) {
        Man man = new Man(new RunFaster(), new EatLess());
        man.running();
        man.eating();

        System.out.println("修改策略");
        man.setRunBehavior(new RunSlow());
        man.running();
    }
}
