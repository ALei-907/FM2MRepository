package com.alei.leetcode;

import com.alei.util.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <a href="https://leetcode.cn/problems/3sum/description/">LeetCode</a>
 * @author LeiLiMin
 * @date: 2022/12/21
 */
public class LC15_3NumSum {
    public static void main(String[] args) {
        LC15_3NumSum lc15_3NumSum = new LC15_3NumSum();
        int[] arr = Utils.genRandomIntArr(15, -10, 12);
        Arrays.sort(arr);
        String arrStr = Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(","));
        System.out.println(arrStr);

        List<List<Integer>> processor = lc15_3NumSum.processor(arr);
        List<List<Integer>> processor2 = lc15_3NumSum.processor2(arr);
        boolean flag = true;
        for (int i = 0; i < processor.size(); i++) {
            for (int i1 = 0; i1 < 3; i1++) {
                if (flag && !Objects.equals(processor.get(i).get(i1), processor2.get(i).get(i1))) {
                    flag = false;

                    break;
                }
            }
        }
        if(flag){
            System.out.println("Success");
            System.out.println(processor);
            System.out.println(processor2);
        }else {
            System.out.println("Fail");
            System.out.println(processor);
            System.out.println(processor2);
        }
    }

    public List<List<Integer>> processor(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 3) {
            return ans;
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int L = i + 1;
            int R = nums.length - 1;
            int target = -nums[i];
            for (; L < nums.length; L++) {
                if (L > i + 1 && nums[L] == nums[L - 1]) {
                    continue;
                }
                while (L < R && nums[R] + nums[L] > target) {
                    --R;
                }
                if (L == R) {
                    break;
                }
                if (nums[L] + nums[R] == target) {
                    List<Integer> subAns = List.of(nums[i], nums[L], nums[R]);
                    ans.add(subAns);
                }
            }
        }
        return ans;
    }

    public List<List<Integer>> processor2(int[] arr) {
        List<List<Integer>> ans = new ArrayList<>();
        HashSet<String> strings = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    if (arr[i] + arr[j] + arr[k] == 0) {
                        String collect = List.of(arr[i], arr[j], arr[k]).stream().map(String::valueOf).collect(Collectors.joining(","));
                        if (!strings.contains(collect)) {
                            strings.add(collect);
                            ans.add(List.of(arr[i], arr[j], arr[k]));
                        }
                    }
                }
            }
        }
        return ans;
    }
}
