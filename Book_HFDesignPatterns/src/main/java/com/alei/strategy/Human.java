package com.alei.strategy;


import com.alei.strategy.behavior.EatBehavior;
import com.alei.strategy.behavior.RunBehavior;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/13
 */
public abstract class Human {
    RunBehavior runBehavior;
    EatBehavior eatBehavior;

    public void setRunBehavior(RunBehavior runBehavior) {
        this.runBehavior = runBehavior;
    }

    public void setEatBehavior(EatBehavior eatBehavior) {
        this.eatBehavior = eatBehavior;
    }

    public void running(){
        this.runBehavior.run();
    }

    public void eating(){
        this.eatBehavior.eat();
    }
}

class Man extends Human {
    public Man(RunBehavior runBehavior, EatBehavior eatBehavior) {
        this.runBehavior = runBehavior;
        this.eatBehavior = eatBehavior;
    }
}
