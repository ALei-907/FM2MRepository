package com.alei.algorithm.sort;

import com.alei.util.Utils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author LeiLiMin
 * @date: 2022/12/21
 */
public class T01_BiggerThan2Right {
    /**
     * 有多少个右侧数*2小于当前数
     */
    public static void main(String[] args) {
        T01_BiggerThan2Right mainStart = new T01_BiggerThan2Right();

        int n = 100;
        while (n-- > 0) {
            int[] arr = Utils.genRandomIntArr(100, 0, 15);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            // String collect = Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(","));
            // System.out.println(collect);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != arr2[i]) {
                    System.out.println("数组拷贝错误!");
                    break;
                }
            }
            int processor = mainStart.processor(arr, 0, arr.length - 1);
            int processor2 = mainStart.processor2(arr2, 0, arr.length - 1);
            if (processor != processor2) {
                System.out.println("对数器官错误!");
                break;
            }
        }
        long begin = System.currentTimeMillis();
        int count = 1000;
        while (count-- > 0) {
            int[] arr = Utils.genRandomIntArr(10000, 0, 15);
            mainStart.processor(arr, 0, arr.length - 1);
        }
        long end = System.currentTimeMillis();
        System.out.println("processor: " + (end - begin));

        begin = System.currentTimeMillis();
        count = 1000;
        while (count-- > 0) {
            int[] arr = Utils.genRandomIntArr(10000, 0, 15);
            mainStart.processor2(arr, 0, arr.length - 1);
        }
        end = System.currentTimeMillis();
        System.out.println("processor2: " + (end - begin));
    }

    public int processor(int[] arr, int L, int R) {
        if (arr.length < 2) {
            return -1;
        }
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return processor(arr, L, mid) + processor(arr, mid + 1, R) + merge(arr, L, mid, R);

    }

    public int merge(int[] arr, int L, int Mid, int R) {
        int ans = 0;
        int cursorR = Mid + 1;
        for (int i = L; i <= Mid; i++) {
            while (cursorR <= R && arr[i] > arr[cursorR] << 1) {
                cursorR++;
            }
            ans += cursorR - Mid - 1;
        }

        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = Mid + 1;
        int i = 0;
        while (p1 <= Mid && p2 <= R) {
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= Mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (int i1 = 0; i1 < help.length; i1++) {
            arr[L + i1] = help[i1];
        }
        return ans;
    }

    public int processor2(int[] arr, int L, int R) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int i1 = i + 1; i1 < arr.length; i1++) {
                if (arr[i] > arr[i1] * 2) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
