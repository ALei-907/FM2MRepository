package com.alei.redisclient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/4/25
 */
public class RedisClientTest {
    public static void main(String[] args) throws IOException {
        RedisClient redisClient = new RedisClient();
        ArrayList<String> key = new ArrayList<>();
        key.add("Key");
        ArrayList<String> arg = new ArrayList<>();
        arg.add("getAttr");
        arg.add("setValue");
        System.out.println(redisClient.hGetSet(key, arg));
    }
}
