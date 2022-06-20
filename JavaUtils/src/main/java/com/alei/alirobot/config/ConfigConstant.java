package com.alei.alirobot.config;

/**
 * @author LeiLiMin
 * @Description: 配置常量
 * @date: 2022/6/16
 */
public interface ConfigConstant {
    /**
     * 装饰消息类型: At
     */
    String MSG_DECORATE_AT = "at";

    /**
     * Config参数: access_token
     */
    String ACCESS_TOKEN = "access_token";

    /**
     * Config参数: 时间戳
     */
    String TIMESTAMP = "timestamp";

    /**
     * Config参数: 签名
     */
    String SIGN = "sign";

    /**
     * 自定义机器人WebHook前缀
     */
    String CUSTOM_WEBHOOK = "https://oapi.dingtalk.com/robot/send";

}
