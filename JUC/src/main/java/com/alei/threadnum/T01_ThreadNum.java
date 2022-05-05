package com.alei.threadnum;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/5
 */
public class T01_ThreadNum {
    public static void main(String[] args) {
        // 获取CPU的核数
        // 线程数=CPU核数 * 预计利用率 * (1 + 等待时长/计算时长)
        System.out.println("CPU Num=" + Runtime.getRuntime().availableProcessors());
    }
}
