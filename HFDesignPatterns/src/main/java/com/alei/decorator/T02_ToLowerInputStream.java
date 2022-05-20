package com.alei.decorator;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/20
 */
public class T02_ToLowerInputStream extends FilterInputStream {

    /**
     * 装饰者模式实战2:
     * 1.使用T01_MyInputStream来装饰FileInputStream
     * 2.组合二者的行为，既实现了文件的读取,也实现了文件转小写的功能
     */

    public T02_ToLowerInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int read = in.read();
        return read == -1 ? read : Character.toLowerCase(read);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = in.read(b, off, len);
        for (int i = off; i < off + result; i++) {
            b[i] = (byte) Character.toLowerCase(b[i]);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        T02_ToLowerInputStream streamMe = new T02_ToLowerInputStream(new FileInputStream(new File("/Users/leilimin/IDEA_ALei/FM2MRepository/HFDesignPatterns/src/main/java/com/alei/decorator/Upper.txt")));
        byte[] byteArr = new byte[8];
        streamMe.read(byteArr);

        String s = new String(byteArr, Charset.forName("UTF-8"));
        System.out.println(s);
    }
}
