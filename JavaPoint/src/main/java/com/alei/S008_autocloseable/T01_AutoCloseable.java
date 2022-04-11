package com.alei.S008_autocloseable;

/**
 * @Author LeiLiMin
 * @Description: 语法糖: AutoCloseable
 * @date: 2022/4/11
 */
public class T01_AutoCloseable {
    public static void main(String[] args) {
        /**
         * try(Resource res = xxx)//可指定多个资源
         * {
         *   work with res
         * }
         */
        // try块退出时，会自动调用res.close()方法，关闭资源。
        try (TestA testA = new TestA()) {
        } catch (Exception e) {
            System.out.println("has error");
        }
    }
}

class TestA implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("Resource Close");
    }
}
