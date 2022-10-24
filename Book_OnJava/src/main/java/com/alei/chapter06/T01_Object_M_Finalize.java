package com.alei.chapter06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author LeiLiMin
 * @date: 2022/10/24
 */
public class T01_Object_M_Finalize {
    /**
     * 描述: finalize()GC回收时会调用该方法
     * 实验: 在未关闭流的情况下,只要对象满足GC条件(GC Roots,可达链路上),依然还是会被回收
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String fileName = "/Users/leilimin/IDEA_ALei/FM2MRepository/Book_OnJava/src/main/java/com/alei/chapter06/MethodInvoke.java";
        new MyStream(fileName);
        System.gc();
        Thread.sleep(10000);
    }

}
class MyStream extends FileInputStream {

    public MyStream(String name) throws FileNotFoundException {
        super(name);
    }

    @Override
    protected void finalize() throws IOException {
        System.out.println("GC .... " + this);
        // super.finalize();
    }
}