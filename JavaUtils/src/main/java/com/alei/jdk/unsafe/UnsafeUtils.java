package com.alei.jdk.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author LeiLiMin
 * @date: 2023/3/22
 */
public class UnsafeUtils {

    public static Unsafe getUnsafe() {
        try {
            Field getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            return (Unsafe) getUnsafe.get(null);
        } catch (Exception e) {
            throw new RuntimeException("UnsafeUtils.getUnsafe() Error");
        }
    }
}
