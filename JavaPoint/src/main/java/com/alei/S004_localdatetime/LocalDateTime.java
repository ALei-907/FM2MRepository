package com.alei.S004_localdatetime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/4/8
 */
public class LocalDateTime {
    public static void main(String[] args) {
        // 1.获取秒数
        Long second = java.time.LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        // 2.获取毫秒数
        Long milliSecond = java.time.LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

        // 3.获取当前时区-当日最小时间 00:00:00 的毫秒值
        long min = java.time.LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli();

        // 4.获取当前时区-当日最小时间 23:59:59 的毫秒值
        long max = java.time.LocalDateTime.of(LocalDate.now(), LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli();

        System.out.println(milliSecond.equals(System.currentTimeMillis())); //true
    }
}
