package com.alei.redisclient;

import com.alei.io.FileReadUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class LuaConstant {
    public static String H_GET_SET;
    public static String SLOTS_10016_INCR_JP_POOL;

    static {
        try {
            H_GET_SET = FileReadUtil.readFileToString("Java_Utils/src/main/resources/lua/redis/HgetSet.lua");
            SLOTS_10016_INCR_JP_POOL = FileReadUtil.readFileToString("Java_Utils/src/main/resources/lua/redis/Slots10016IncrJpPool.lua");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
