package com.alei.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LeiLiMin
 * @date: 2022/12/21
 */
public class Utils {
    public static int[] genRandomIntArr(int length, int minNum, int maxNum) {
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            ans[i] = ThreadLocalRandom.current().nextInt(minNum, maxNum);
        }
        return ans;
    }
}
