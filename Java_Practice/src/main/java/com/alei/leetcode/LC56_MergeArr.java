package com.alei.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author LeiLiMin
 * @date: 2022/12/27
 */
public class LC56_MergeArr {


    public static void main(String[] args) {
        int[][] intervals = {
                {1, 3},
                {2, 6},
                {8, 10},
                {15, 18}
        };
        LC56_MergeArr lc56_mergeArr = new LC56_MergeArr();
        lc56_mergeArr.merge(intervals);
    }


    public int[][] merge(int[][] intervals) {
        // 1.先根据"最小边界排序"
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        ArrayList<int[]> ansTemp = new ArrayList<>();
        ansTemp.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] left = ansTemp.get(ansTemp.size() - 1);
            int[] right = intervals[i];
            // 可合并
            if (right[0] <= left[1]) {
                left[1] = Math.max(right[1], left[1]);
            } else {
                ansTemp.add(right);
            }
        }
        return ansTemp.toArray(new int[ansTemp.size()][]);
    }

    public void processor(int[][] intervals, int L, int R) {

    }

    // public void merge1(int[][] intervals, int L, int mid, int R) {
    //     // 存储区间内的变化情况,最长也不过[L,R]的范围内
    //     int[][] tempArr = new int[R - L + 1][intervals[0].length];
    //
    //     int p1 = L;
    //     int p2 = mid + 1;
    //     int tempIndex = 0;
    //     while (p1 <= mid && p2 <= R) {
    //         // 不能合并: 就排序,保证右边一定比左边大
    //         if (intervals[p2][0] > intervals[p1][1] || intervals[p2][1] < intervals[p1][0]) {
    //             tempArr[tempIndex++] = intervals[p2][0] < intervals[p1][0] ? intervals[p2++] : intervals[p1++];
    //         } else {
    //             // 能合并: 就先将合并结果保存至p1,p2++
    //             int[] merge = {Math.min(intervals[p1][0], intervals[p2][0]), Math.min(intervals[p1][1], intervals[p2][1])};
    //             tempArr[tempIndex++] = merge;
    //             intervals[p1] = merge;
    //             p2++;
    //         }
    //     }
    //     while (p1 <= mid) {
    //         tempArr[tempIndex++] = intervals[p1++];
    //     }
    //     while (p2 <= R) {
    //         tempArr[tempIndex++] = intervals[p2++];
    //     }
    //     // 合并了多少组: 原始数组数量-当前合并后的数量
    //     int su = (R - L + 1) - tempArr.length;
    //
    //     int[][] ansTemp = new int[intervals.length - su][intervals[0].length];
    //     int ansIndex = 0;
    //     for (int i = 0; i < intervals.length; i++) {
    //         if (i >= L && i <= (R - su)) {
    //             ansTemp[ansIndex++] = tempArr[i - L];
    //         } else if (i < L || i > R) {
    //             ansTemp[ansIndex++] = intervals[i];
    //         }
    //     }
    //     intervals = ansTemp;
    // }
}
