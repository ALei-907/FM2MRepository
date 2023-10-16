package com.alei.msb.teacherhuang;

import java.util.concurrent.*;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/11
 */
public class T01_CreateThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.Extends Thread
        new T1().start();

        // 2.Implements Runnable
        new Thread(new T2()).start();

        // 3.Implements Callable
        FutureTask<Integer> ft = new FutureTask<>(new T3());
        new Thread(ft).start();
        System.out.println(ft.get());
    }
}

class T1 extends Thread{
    @Override
    public void run() {
        System.out.println("Extends Thread....");
    }
}


class T2 implements Runnable{
    @Override
    public void run() {
        System.out.println("Implements Runnable....");
    }
}

class T3 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Implements Callable....");
        return 7;
    }
}

