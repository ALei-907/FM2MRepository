package com.alei.redisclient;

import com.alei.io.FileReadUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/4/25
 */
public class HGetSetTest {
    public static void main(String[] args) throws IOException {
        RedisClient redisClient = new RedisClient();
        ArrayList<String> key = new ArrayList<>();
        key.add("alei");
        ArrayList<String> arg = new ArrayList<>();
        arg.add("name");
        String s = FileReadUtil.readFileToString("/Users/leilimin/IDEA-MySpace/FM2MRepository/JavaUtils/src/main/java/com/alei/redisclient/HgetSet.lua");
        redisClient.evalArgs(s,key,arg);
    }
}
