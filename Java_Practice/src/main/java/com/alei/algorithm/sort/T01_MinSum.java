package com.alei.algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author LeiLiMin
 * @date: 2022/12/20
 */
public class T01_MinSum {
    private int sum;

    /**
     * 计算最小和
     */
    public static void main(String[] args) {
        T01_MinSum mergeSor = new T01_MinSum();
        int[] arr = {4, 3, 5, 7, 2, 8, 6, 4};
        int processor = mergeSor.processor(arr, 0, arr.length - 1);
        String collect = Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(","));
        System.out.println(collect);
        System.out.println(processor);
    }

    public int processor(int arr[], int L, int R) {
        if (L == R) {
            return 0;
        }
        // 防止使用(L+R)/2时 L+R整型溢出
        int mid = L + ((R - L) >> 1);
        return processor(arr, L, mid) + processor(arr, mid + 1, R) + merge(arr, L, mid, R);
    }

    public int merge(int arr[], int L, int mid, int R) {
        int help[] = new int[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int i = 0;
        int res = 0;
        while (p1 <= mid && p2 <= R) {
            // 当拷贝左侧最小数时,计算最小和
            if (arr[p1] < arr[p2]) {
                res += arr[p1] * (R - p2 + 1);
                help[i++] = arr[p1++];
            } else {
                help[i++] = arr[p2++];
            }
        }
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (int i1 = 0; i1 < help.length; i1++) {
            arr[L + i1] = help[i1];
        }
        return res;
    }


}
