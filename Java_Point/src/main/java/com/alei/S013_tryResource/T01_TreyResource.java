package com.alei.S013_tryResource;

import java.io.InputStream;

/**
 * @author LeiLiMin
 * @date: 2022/8/28
 */
public class T01_TreyResource {
    public static void main(String[] args) {
        /**
         * try resource的方式可以使得在try()内的对象,在语句快结束时自动调用对象的close()
         */
        try(InputStream in = T01_TreyResource.class.getResourceAsStream("FilePath")) {
            assert in != null;
            System.out.println(in.read());
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
