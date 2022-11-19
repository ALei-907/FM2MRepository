package com.alei.redisclient;

import com.alei.openapi.sensorsbi.utils.DefaultThreadFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/4/25
 */
public class RedisClientTest {
    public static void main(String[] args) throws IOException {
        final RedisClient redisClient = new RedisClient();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> key = new ArrayList<>();
                key.add("slotsdata:10016:h:s:jackpotPool");
                key.add("slotsdata:10016:h:s:bonusPool");
                ArrayList<String> arg = new ArrayList<>();
                arg.add(String.valueOf(System.currentTimeMillis()));
                System.out.println(redisClient.eval(LuaConstant.SLOTS_10016_INCR_JP_POOL, key, arg));
            }
        }, 0,30, TimeUnit.SECONDS);

    }
}
