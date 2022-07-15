package com.alei.S003_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author LeiLiMin
 * @Description: Dev 测试
 * @date: 2022/7/15
 */
public class T03_StreamTest {
    public static void main(String[] args) {
        findFirst(3);
    }

    public static void findFirst(int target) {
        Integer[] nodeMetricArr = {3, 5, 7};
        List<Integer> nodeMetrics = Arrays.asList(nodeMetricArr);
        Optional<Integer> first = nodeMetrics.stream().filter(e -> target < e).findFirst();
        System.out.println(first.get());

    }
}
