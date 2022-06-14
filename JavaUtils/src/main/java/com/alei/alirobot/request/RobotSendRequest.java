package com.alei.alirobot.request;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author LeiLiMin
 * @Description: 发送消息，内部封装基础消息类型
 * @date: 2022/6/14
 */
public class RobotSendRequest {
    public static class Text {
        /**
         * 消息类型
         */
        private String msgtype = "text";

        /**
         * 文本内容
         */
        private TextContent text;

        /**
         * At对象的信息
         */
        private At at;

        public Text(@NotNull String content, List<String> atMobiles, List<String> atUserIds, Boolean isAtAll) {
            this.text = new TextContent(content);
            this.at = new At(atMobiles, atUserIds, isAtAll);
        }

        /**
         * 文本内部类
         */
        class TextContent {
            /**
             * 消息主体
             */
            private String content;

            public TextContent(String content) {
                this.content = content;
            }
        }

        public static class At {
            private List<String> atMobiles;
            private List<String> atUserIds;
            private Boolean isAtAll;

            public At(List<String> atMobiles, List<String> atUserIds, Boolean isAtAll) {
                this.atMobiles = atMobiles;
                this.atUserIds = atUserIds;
                this.isAtAll = isAtAll;
            }
        }
    }

    public static class Link{
        /**
         * 消息类型
         */
        private String msgtype = "link";
    }
}
