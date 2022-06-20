package com.alei.alirobot.client;


import com.alei.alirobot.config.RobotConfig;
import com.alei.alirobot.okhttp.OkhttpClientUtil;
import com.alei.alirobot.request.AbstractRequest;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author LeiLiMin
 * @Description: 机器人客户端
 * @date: 2022/6/16
 */
@Data
public abstract class AbstractRobotClient<C extends RobotConfig> implements RobotClient {
    protected final OkhttpClientUtil jackpotOkHttpClient;

    public AbstractRobotClient(OkhttpClientUtil jackpotOkHttpClient) {
        this.jackpotOkHttpClient = jackpotOkHttpClient;
    }

    /**
     * 根据Config,检测消息是否符合要求
     */
    abstract void checkByConfig(C config, AbstractRequest request);

    /**
     * 根据Config,设置请求头参数
     */
    abstract Map<String, String> settingHeaderParamByConfig(C config) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;

    /**
     * 发送基础消息类型
     *
     * @param request 消息实体
     */
    abstract void sendBasicMsg(C config, AbstractRequest request);
}
