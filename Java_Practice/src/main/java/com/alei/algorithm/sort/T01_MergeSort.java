package com.alei.algorithm.sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author LeiLiMin
 * @date: 2022/12/20
 */
public class T01_MergeSort {
    public static void main(String[] args) {
        T01_MergeSort mergeSor = new T01_MergeSort();
        int[] arr = {1, 3, 5, 7, 9, 0, 2, 4, 6, 8};
        mergeSor.processor(arr, 0, arr.length - 1);
        String collect = Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(","));
        System.out.println(collect);
    }

    public void processor(int arr[], int L, int R) {
        if (L == R) {
            return;
        }
        // 防止使用(L+R)/2时 L+R整型溢出
        int mid = L + ((R - L) >> 1);
        // 左侧有序
        processor(arr, L, mid);
        // 右侧有序
        processor(arr, mid + 1, R);
        // 整体有序
        merge(arr, L, mid, R);
    }

    public void merge(int arr[], int L, int mid, int R) {
        int help[] = new int[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= R) {
            help[i++] = arr[p1] >= arr[p2] ? arr[p1++] : arr[p2++];
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
    }


}
