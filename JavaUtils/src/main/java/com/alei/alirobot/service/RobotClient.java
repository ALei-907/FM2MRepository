package com.alei.alirobot.service;

import com.alei.alirobot.request.AbstractRequest;

/**
 * 机器人服务
 */
public interface RobotClient {

    /**
     * 发送消息
     */
    void sendMsg(AbstractRequest request);


}
