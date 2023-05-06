package com.alei.leetcode;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/3sum/description/">LeetCode</a>
 *
 * @author LeiLiMin
 * @date: 2022/12/21
 */
public class LC49_StrCountBySameChar {
    public static void main(String[] args) {
        LC49_StrCountBySameChar lc49StrCountBySameChar = new LC49_StrCountBySameChar();

    }

    /**
     * 低运行时间,高内存使用
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> keyMap = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            List<String> value = keyMap.getOrDefault(key, new ArrayList<>());
            value.add(str);
            keyMap.put(key, value);
        }
        return new ArrayList<>(keyMap.values());
    }

    /**
     * 优化{@link LC49_StrCountBySameChar#groupAnagrams(String[])}方法中对char[]的排序时间复杂度
     * garbage!!!
     */
    public List<List<String>> groupAnagrams2(String[] strs) {
        HashMap<String, List<String>> keyMap = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            int[] counts = new int[26];
            for (char _char : chars) {
                counts[_char - 'a'] += 1;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] != 0) {
                    sb.append((char) ('a' + i));
                    sb.append(counts[i]);
                }
            }
            String key = sb.toString();
            List<String> value = keyMap.getOrDefault(key, new ArrayList<>());
            value.add(str);
            keyMap.put(key, value);
        }

        return new ArrayList<>(keyMap.values());
    }
}
