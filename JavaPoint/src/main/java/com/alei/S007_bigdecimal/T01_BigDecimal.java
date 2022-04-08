package com.alei.S007_bigdecimal;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Author LeiLiMin
 * @Description: BigDecimal实战-精度丢失的解决方案
 * @date: 2022/4/8
 */
public class T01_BigDecimal {
    public static void main(String[] args) {
        // 1.BigDecimal的格式化类,可以让目标值精确到x位小数
        DecimalFormat format = new DecimalFormat("0.000000");

        // 2.踩坑 BigDecimal(0.0000001F); 并不难避免精度丢失,必须使用DecimalFormat进行格式化
        BigDecimal baseRatio = new BigDecimal(format.format(0.0000001F));
        long val = baseRatio.multiply(BigDecimal.valueOf(100000L)).longValue();
    }
}
