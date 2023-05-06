package com.alei.redisclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author LeiLiMin
 * @date: 2022/4/25
 */
public class RedisClientTest {
    public static void main(String[] args) throws IOException {
        final RedisClient redisClient = new RedisClient();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            ArrayList<String> key = new ArrayList<>();
            key.add("slotsdata:10016:h:s:jackpotPool");
            key.add("slotsdata:10016:h:s:jackpotexpire");
            ArrayList<String> arg = new ArrayList<>();
            arg.add("jackpot5");
            arg.add(String.valueOf(System.currentTimeMillis()));
            arg.add(String.valueOf(1));
            arg.add(String.valueOf(3));

            System.out.println(redisClient.eval(LuaConstant.SLOTS_10016_INCR_JP_POOL, key, arg));
        }, 0, 1, TimeUnit.SECONDS);

    }
}
