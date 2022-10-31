package com.alei.S005_threadlocalrandom;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/4/8
 */
public class ThreadLocalRandom_Test {
    public static void main(String[] args) {
        // 1.随机Float [0,1)
        float randomFloat = ThreadLocalRandom.current().nextFloat();

        // 2.随机Double [0,1)
        double randomDouble = ThreadLocalRandom.current().nextDouble();

        // 3.随机Double [0,bound)
        int randomInt = ThreadLocalRandom.current().nextInt(1);
    }
}
