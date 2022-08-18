package com.alei.zcr.chapter_3;

import java.util.ArrayList;

/**
 * @author LeiLiMin
 * @date: 2022/8/18
 */
public class T07_GCLog {
    /**
     * 查看GC基本信息 > -XX:+PrintGC
     * 查看GC详细信息JDK9之前 > -XX:-PrintGCDetails
     * 查看GC详细信息JDK9之后 > -Xlog:gc*
     * 其他见书P124
     */
    public static void main(String[] args) {
        ArrayList<int[]> ints = new ArrayList<>();
        while (true){
            ints.add(new int[1024]);
        }
    }
}
