package com.alei.zcr.chapter_7;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author LeiLiMin
 * @date: 2022/8/23
 */
public class T04_ClassLoader {
    /**
     * 类加载器和类本身一起确立其在java虚拟机中的唯一性
     *
     * 例子中: 使用自定义类加载器加载出的class对象仍然和当前类不匹配(因为当前类是由虚拟机的应用程序类加载加载的App加载器)
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) {
                try {
                    String fileName = name.substring(name.lastIndexOf('.') + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    //
                    throw new RuntimeException(e.getMessage());
                }
            }
        };
        Object obj = classLoader.loadClass("com.alei.zcr.chapter_7.T04_ClassLoader").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof T04_ClassLoader);

    }
}
