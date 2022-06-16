package com.alei.alirobot.service;

import com.alei.alirobot.config.RobotConfig;
import com.alei.alirobot.request.AbstractRequest;
import com.alei.alirobot.request.Request;

import java.io.IOException;
import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/6/16
 */
public abstract class AbstractRobotClient<C extends RobotConfig> implements RobotClient {
    /**
     * 根据Config,检测消息是否符合要求
     */
    abstract void checkByConfig(C config, AbstractRequest request);

    /**
     * 根据Config,设置请求头参数
     */
    abstract Map<String, String> settingRequestParamByConfig(C config);

    /**
     * 发送基础消息类型
     *
     * @param request 消息实体
     */
    abstract void sendBasicMsg(C config,AbstractRequest request) throws IOException;
}
