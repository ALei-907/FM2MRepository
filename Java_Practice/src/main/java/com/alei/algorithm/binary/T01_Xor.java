package com.alei.algorithm.binary;

import lombok.extern.slf4j.Slf4j;

/**
 * 异或运算也可以称之为无进位相加
 * 0^N=N => 无进位相加,0+N=N
 * N^N=0 => 每一位都是无进位相加,所以0+0=0,1+1=0
 *
 * @author LeiLiMin
 * @date: 2022/12/17
 */
@Slf4j
public class T01_Xor {
    public static void main(String[] args) {
        swapAB(1, 2);
        System.out.println(findRightOne(3));

        int[] argArr = {7, 2, 2, 3, 3, 4, 4};
        System.out.println(kmFindk(argArr, 1, 2));
    }

    public static void swapAB(int a, int b) {
        System.out.println("Swap Before: a=[" + a + "],b=[" + b + "]");
        //             a          b
        a = a ^ b;//   a^b        b
        b = a ^ b;//   a^b        a^b^b=a
        a = a ^ b;//   a^b^a=b    a
        System.out.println("Swap After: a=[" + a + "],b=[" + b + "]");
    }

    /**
     * 题目1: 找到最右侧的1
     * ~a+1 = -a,所以a & (~a + 1);也可以写成a&-a
     */
    public static int findRightOne(int a) {
        return a & (~a + 1);
    }

    /**
     * 假设提供一个数组,只有一种数字出现了K次,其他数字出现了m次,且1< k < m,求出现k次的数
     */
    public static int kmFindk(int[] arr, int k, int m) {
        // 因为整形为32bit,所以使用一个临时数组来存储数组中每个元素在int-bit上的计数
        int[] temp = new int[32];
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                temp[i] += (num >> i) & 1;
            }
        }
        // 在int-bit中如果某一位%m!=0,申明这一位不为0,并且这一位上的bit为出现K次数字的组成部分
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (temp[i] % m != 0) {
                ans |= (1 << i);
            }
        }
        return ans;
    }
}
