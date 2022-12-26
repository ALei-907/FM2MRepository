package com.alei.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <a href="https://leetcode.cn/problems/3sum/description/">LeetCode</a>
 *
 * @author LeiLiMin
 * @date: 2022/12/21
 */
public class LC18_4NumSum {
    public static void main(String[] args) {
        LC18_4NumSum lc15_3NumSum = new LC18_4NumSum();

        int n = 10000;
        while (n-- > 0) {
            // int[] arr = Utils.genRandomIntArr(15, -10, 12);
            int[] arr = {1, 0, -1, 0, -2, 2};
            // int random = ThreadLocalRandom.current().nextInt(10);
            int random = 0;
            Arrays.sort(arr);
            String arrStr = Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(","));
            List<List<Integer>> processor = lc15_3NumSum.processor(arr, random);
            List<List<Integer>> processor2 = lc15_3NumSum.processor2(arr, random);
            boolean flag = true;
            for (int i = 0; i < processor.size(); i++) {
                for (int i1 = 0; i1 < 4; i1++) {
                    if (flag && !Objects.equals(processor.get(i).get(i1), processor2.get(i).get(i1))) {
                        flag = false;

                        break;
                    }
                }
            }
            if (!flag) {
                System.out.println("Fail");
                System.out.println(arrStr);
                System.out.println(processor);
                System.out.println(processor2);
            }
        }


    }

    public List<List<Integer>> processor(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 4||(nums[nums.length - 1] < 0) && target >= 0) {
            return ans;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0 && nums[i] > target) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int L0 = i + 1;
            for (; L0 < nums.length; L0++) {
                if (L0 > i + 1 && nums[L0] == nums[L0 - 1]) {
                    continue;
                }

                int L1 = L0 + 1;
                int R = nums.length - 1;
                for (; L1 < nums.length; L1++) {
                    if (L1 > L0 + 1 && nums[L1] == nums[L1 - 1]) {
                        continue;
                    }
                    long iL = nums[i];
                    long l0L = nums[L0];
                    long l1L = nums[L1];
                    long rL = nums[R];

                    while (L1 < R && iL + l0L + l1L + rL > target) {
                        --R;
                        rL = nums[R];
                    }
                    if (L1 == R) {
                        break;
                    }
                    if (iL + l0L + l1L + rL == target) {

                        List<Integer> subAns = List.of(nums[i], nums[L0], nums[L1], nums[R]);
                        ans.add(subAns);
                    }
                }

            }
        }
        return ans;
    }

    public List<List<Integer>> processor2(int[] arr, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        HashSet<String> strings = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    for (int l = k + 1; l < arr.length; l++) {
                        long iL = arr[i];
                        long jL = arr[j];
                        long kL = arr[k];
                        long lL = arr[l];

                        if (iL + jL + kL + lL == target) {
                            String collect = List.of(arr[i], arr[j], arr[k], arr[l]).stream().map(String::valueOf).collect(Collectors.joining(","));
                            if (!strings.contains(collect)) {
                                strings.add(collect);
                                ans.add(List.of(arr[i], arr[j], arr[k], arr[l]));
                            }
                        }
                    }

                }
            }
        }
        return ans;
    }
}
