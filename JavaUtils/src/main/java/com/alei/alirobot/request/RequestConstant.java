package com.alei.alirobot.request;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/6/15
 */
public interface RequestConstant {
    /**
     * paramMap的ke: 消息类型
     */
    String MSG_TYPE = "msgtype";

    /**
     * 基础消息类型: text
     */
    String MSG_TYPE_TEXT = "text";

    /**
     * 基础消息类型: link
     */
    String MSG_TYPE_LINK = "link";

    /**
     * 装饰消息类型: At
     */
    String MSG_DECORATE_AT = "at";

    /**
     * Config参数: access_token
     */
    String ACCESS_TOKEN="access_token";

    /**
     * Config参数: 时间戳
     */
    String TIMESTAMP="timestamp";

    /**
     * Config参数: 签名
     */
    String SIGN="sign";
}
