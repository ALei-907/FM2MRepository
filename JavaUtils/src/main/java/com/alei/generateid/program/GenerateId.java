package com.alei.generateid.program;

import com.alei.io.FileReadUtil;
import com.alei.random.RandomUtil;
import com.alei.redisclient.RedisClient;

import java.io.IOException;
import java.util.*;

/**
 * @Author LeiLiMin
 * @Description: 内存中模拟无序Id的生成
 * @date: 2022/4/10
 */
public class GenerateId {
    /**
     * 前缀号池
     */
    private List<Integer> prefixList;

    /**
     * ID号池
     */
    private List<Integer> idList;

    /**
     * 当前前缀
     */
    private Integer currentPrefix;

    /**
     * Id号池的大小
     */
    private Integer idSize;


    public GenerateId(int prefixSize, int idSize) {
        // 初始化: 前缀号池,Id号池,当前前缀
        this.sizeCheck0(prefixSize, idSize);

        this.idSize = idSize;
        this.prefixList = new ArrayList<>();
        this.idList = new ArrayList<>();

        for (int i = 1; i < prefixSize; i++) {
            this.prefixList.add(i);
        }

        for (int i = 1; i < idSize; i++) {
            this.idList.add(i);
        }

        int prefix = RandomUtil.getRandomIndexByList(prefixSize);
        this.currentPrefix = this.prefixList.remove(prefix);
    }

    public Integer getId() {

        if (this.idList.isEmpty()) {
            if (this.prefixList.isEmpty()) {
                throw new RuntimeException("prefix is empty");
            }
            // : 取前缀
            int prefixIndex = RandomUtil.getRandomIndexByList(prefixList.size());
            this.currentPrefix = this.prefixList.remove(prefixIndex);

            // : 生成新号池
            for (int i = 0; i < this.idSize; i++) {
                this.idList.add(i);
            }
        }
        int idIndex = RandomUtil.getRandomIndexByList(idList.size());
        Integer currentId = idList.remove(idIndex);
        return this.currentPrefix * this.idSize + currentId;
    }

    /**
     * 检测size只能为10的倍数
     */
    private void sizeCheck0(int... size) {
        try {
            Arrays.stream(size).forEach(e -> {
                assert e > 10;
                assert e % 10 == 0;
            });
        } catch (Exception e) {
            throw new RuntimeException("Size must be multiples of ten");
        }
    }

    public static void main(String[] args) throws IOException {
        // GenerateId generateId = new GenerateId(100, 100);
        // Set<Integer> set = new HashSet<>();
        // while (true) {
        //     try {
        //         Integer id = generateId.getId();
        //         set.add(id);
        //     } catch (Exception e) {
        //         System.out.println(1);
        //     }
        //
        // }

        // Lua脚本测试
        RedisClient redisClient = new RedisClient();
        String fileContent = FileReadUtil.readFileToString("/Users/leilimin/IDEA-MySpace/FM2MRepository/JavaUtils/src/main/java/com/alei/generateid/redis/GenerateId.lua");
        String Id = redisClient.evalNothing(fileContent);
        System.out.println(Id);
    }
}
