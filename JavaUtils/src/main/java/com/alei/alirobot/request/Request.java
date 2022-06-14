package com.alei.alirobot.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Data;

import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/6/14
 */
@Data
public abstract class Request {
    public String msgtype;

    private Map<String, Object> requestInfo;

    public abstract void putInfo();

// =============== 装饰开始 ===============
    public static class Text extends Request {

        @Override
        public void putInfo() {
            // this.getRequestInfo().put("")
        }
    }



    public static class At extends Request {

    }
}
