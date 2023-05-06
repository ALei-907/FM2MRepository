package com.alei.random;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author LeiLiMin
 * @Description: 权重随机根据类
 * @date: 2022/4/8
 */
public class RandomByWeightUtil {

    /**
     * why?
     * 1.根据上诉权重可得：1，2，2，3，3，3
     * 2.根据以下逻辑
     * *：random出的值会很随机的遍布在上诉列表的某一点上
     * *：而在每次循环不匹配时的扣除当次的值，这就将整个列表抽象成了一个域图
     */
    public static <T> T getRandomSchemeByWeight(List<T> list, List<Integer> weight) {
        // 1.权重求和
        int weightAccMax = weight.stream().mapToInt(Integer::intValue).sum();
        // 2.随机random
        int randomWeight = ThreadLocalRandom.current().nextInt(weightAccMax);
        // 3.权重求值
        for (int i = 0; i < weight.size(); i++) {
            if (randomWeight < weight.get(i)) {
                return list.get(i);
            }
            randomWeight -= weight.get(i);
        }
        // 4.默认返回
        return list.get(0);
    }

    /**
     * 生产随机ID测试
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> value = new ArrayList<>();
        value.add(1);
        value.add(2);
        value.add(3);
        List<Integer> weight = new ArrayList<>();
        weight.add(1);
        weight.add(2);
        weight.add(3);

        int totalNum = 99999999;
        int result1 = 0;
        int result2 = 0;
        int result3 = 0;
        for (int i = 0; i < totalNum; i++) {
            Integer randomSchemeByWeight = getRandomSchemeByWeight(value, weight);
            switch (randomSchemeByWeight) {
                case 1:
                    result1++;
                    break;
                case 2:
                    result2++;
                    break;
                case 3:
                    result3++;
                    break;
                default:
                    throw new RuntimeException("Calculate Error");
            }
        }
        System.out.println("result1:" + result1 + "\tAvg:" + (float) result1 / (float) totalNum);
        System.out.println("result2:" + result2 + "\tAvg:" + (float) result2 / (float) totalNum);
        System.out.println("result3:" + result3 + "\tAvg:" + (float) result3 / (float) totalNum);


    }

}
