package com.alei;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/6/21
 */
public enum EnumTest {
    ONE,
    TWO,
    THREE;

    public static EnumTest getEnumByOriginal(int original) {
        // EnumTest[] values = [ONE,TWO,THREE];
        EnumTest[] values = EnumTest.values();
        return values[original];
    }
}
