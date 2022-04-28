package com.alei.redisclient;

import com.alei.io.FileReadUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class LuaConstant {
    public static String H_GET_SET;

    static {
        try {
            H_GET_SET = FileReadUtil.readFileToString("JavaUtils/src/main/resources/lua/redis/HgetSet.lua");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
