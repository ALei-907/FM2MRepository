package com.alei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author LeiLiMin
 * @date: 2023/2/13
 */
public class Test {
    public static final int LOOP = 100;

    public static void main(String[] args) throws IOException {

    }

    public static void test(Integer i) {
        final Integer[] ints = new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        for (int i1 = 0; i1 < LOOP; i1++) {
            ArrayList<Integer> temp = new ArrayList<>(Arrays.asList(ints));
            temp.remove(10);
        }

        long start = System.currentTimeMillis();
        for (int i1 = 0; i1 < LOOP; i1++) {
            ArrayList<Integer> temp = new ArrayList<>(Arrays.asList(ints));
            temp.remove(temp.size() - 2);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);


        start = System.currentTimeMillis();
        for (int i1 = 0; i1 < LOOP; i1++) {
            ArrayList<Integer> temp = new ArrayList<>(Arrays.asList(ints));
            temp.remove(0);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
