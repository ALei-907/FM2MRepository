package com.alei.algorithm.struct;

/**
 * @author LeiLiMin
 * @date: 2022/12/19
 */
public class ArrQueue {
    /**
     * 队列
     */
    private final int[] arr;

    /**
     * 队列容量
     */
    private int size;

    /**
     * 取元素时的下标
     */
    private int pop;

    /**
     * 存元素时的下标
     */
    private int push;

    public ArrQueue(int size) {
        this.arr = new int[size];
        this.size = 0;
    }

    public boolean push(int num) {
        if (size >= arr.length) {
            return false;
        }
        arr[(push++)%arr.length] = num;
        size++;
        return true;
    }

    public int pop() {
        if (size <= 0) {
            return -1;
        }
        size--;
        return arr[pop++%arr.length];
    }

    public static void main(String[] args) throws InterruptedException {
        ArrQueue arrQueue = new ArrQueue(5);
        int pushNum = 1;
        while (true) {
            boolean pushFlag = arrQueue.push(pushNum++);
            if (!pushFlag) {
                int popNum = arrQueue.pop();
                while (popNum > 0) {
                    Thread.sleep(1000L);
                    System.out.println(popNum);
                    popNum = arrQueue.pop();
                }
            }
            Thread.sleep(1000L);
        }

    }
}
