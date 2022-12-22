package com.alei.leetcode;

import com.alei.util.Utils;

import java.util.Arrays;

/**
 * @author LeiLiMin
 * @date: 2022/12/22
 */
public class LC16_Nearest3NumSum {
    public static void main(String[] args) {


        int n = 100;
        while (n-- > 0) {
            // processor(arr, 10);
            // processor2(arr2, 10);
            int[] arr = Utils.genRandomIntArr(15, -10, 10);
            int[] arr2 = Arrays.copyOf(arr, arr.length);

            int processor = processor(arr, 10);
            int processor2 = processor2(arr2, 10);
            if (processor != processor2) {
                System.out.println("processor: " + processor(arr, 10) + "\tprocessor2: " + processor2(arr2, 10));
            }
        }


    }

    public static int processor(int[] nums, int target) {
        if (nums.length < 3) {
            return Integer.MAX_VALUE;
        }
        // 1.先排序
        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int p1 = i + 1;
            int p2 = nums.length - 1;
            while (p1 < p2) {
                int sum = nums[i] + nums[p1] + nums[p2];
                if (Math.abs(sum - target) < Math.abs(ans - target)) {
                    ans = sum;
                }
                // 三数和==target,直接返回
                if (sum == target) {
                    return ans;
                }
                // 三数和 > target,因为数组有序,所以向左移动右指针

                if (sum > target) {
                    p2--;
                }
                // 三数和 < target,因为数组有序,所以向右移动左指针
                if (sum < target) {
                    p1++;
                }
            }
        }
        return ans;
    }

    public static int processor2(int[] nums, int target) {
        if (nums.length < 3) {
            return Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (Math.abs(sum - target) < Math.abs(ans - target)) {
                        ans = sum;
                    }
                    if (ans == target) {
                        return ans;
                    }
                }
            }
        }
        return ans;
    }

}
