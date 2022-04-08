package com.alei.random;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @Author LeiLiMin
 * @Description: 完全随机根据
 * @date: 2022/4/8
 */
public class RandomUtil {
    /**
     * 通过已经使用的元素在全量数组上快速获取随机的可用元素
     * why?
     * 1.主要是为了防止，当可用元素存量极少时，发送高概率的空转
     */
    public static <T> T getUsefulRandomElement(T[] allData, List<T> used) {
        Set<T> currentIndex = used.stream().collect(Collectors.toSet());
        List<T> usableIndex = Arrays.stream(allData).filter(e -> !currentIndex.contains(e)).collect(Collectors.toList());
        int size = usableIndex.size();
        int chooseIndex = ThreadLocalRandom.current().nextInt(size);
        return usableIndex.get(chooseIndex);
    }
}
