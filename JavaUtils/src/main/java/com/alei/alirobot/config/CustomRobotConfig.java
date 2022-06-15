package com.alei.alirobot.config;

import lombok.Data;

import java.util.List;

/**
 * @Author LeiLiMin
 * @Description: 自定义机器人配置
 * @date: 2022/6/15
 */
@Data
public class CustomRobotConfig {
    /**
     * 推送目标地址
     */
    private String webhook = "https://oapi.dingtalk.com/robot/send";

    /**
     * access_token
     */
    private String accessToken;

    /**
     * 关键词
     */
    private List<String> keyWords;

    /**
     * 数字签名
     */
    protected String secret;
}
