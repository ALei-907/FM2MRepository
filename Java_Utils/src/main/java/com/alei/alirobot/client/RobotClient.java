package com.alei.alirobot.client;


import com.alei.alirobot.config.RobotConfig;
import com.alei.alirobot.request.AbstractRequest;

/**
 * 机器人服务
 */
public interface RobotClient {

    /**
     * 发送消息
     */
    void sendMsg(RobotConfig config, AbstractRequest request);


}
