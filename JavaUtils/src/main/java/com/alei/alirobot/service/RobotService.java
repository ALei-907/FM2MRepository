package com.alei.alirobot.service;

import com.alei.alirobot.request.AbstractRequest;
import com.alei.alirobot.request.Request;

/**
 * 机器人服务
 */
public interface RobotService {

    /**
     * 发送基础消息类型
     * @param request 消息实体
     */
    void sendBasicMsg(AbstractRequest request);
}
