package com.alei.alirobot.request;

import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description: 消息请求模型接口
 * @date: 2022/6/14
 */
public interface Request {

    /**
     * 用来将封装请求数据Map
     */
    Map<String, Object> putInfo();
}
