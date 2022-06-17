package com.alei.alirobot.request.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;


/**
 * 使用消息类型
 */
@AllArgsConstructor
@Getter
public enum UseMsgType {
    /**
     * 基础消息类型: 文本
     */
    TEXT_BASIC_MSG(MsgType.BASIC_MSG, "text"),

    /**
     * 基础消息类型: 链接
     */
    LINK_BASIC_MSG(MsgType.BASIC_MSG, "link");

    /**
     * 消息类型
     */
    private MsgType msgType;

    /**
     * 使用消息类型
     */
    private String useMsgType;

    /**
     * 根据"使用消息类型"来返回消息类型,以决定使用具体发送方式
     *
     * @param useMsgType 使用消息类型
     * @return {@link MsgType} 消息类型
     */
    public static MsgType getMsgTypeByInUseMsgType(String useMsgType) {
        for (UseMsgType value : UseMsgType.values()) {
            if (Objects.equals(value.getUseMsgType(), useMsgType)) {
                return value.getMsgType();
            }
        }
        // 返回默认消息类型,以终止消息发送
        return MsgType.DEFAULT_NONE_MSG;
    }
}
