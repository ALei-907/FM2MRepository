package com.alei.alirobot.test;

import java.util.List;

/**
 * @Author LeiLiMin
 * @Description: 文本类型
 * @date: 2022/3/1
 */
public class TextType {
    /**
     * 消息类型
     */
    private String msgtype;

    /**
     * 文本内容
     */
    private Text text;

    /**
     * @对象 的信息
     */
    private At at;

    public TextType(String content) {
        this.msgtype = "text";
        this.text = new Text(content);
    }

    public void setAt(At at) {
        this.at = at;
    }

    /**
     * 文本内部类
     */
    class Text {
        /**
         * 消息主体
         */
        private String content;

        public Text(String content) {
            this.content = content;
        }
    }

    /**
     * @ 内部类
     */
    public static class At {
        private List<String> atMobiles;
        private List<String> atUserIds;
        private Boolean isAtAll;

        public At(List<String> atMobiles, List<String> atUserIds, Boolean isAtAll) {
            this.atMobiles = atMobiles;
            this.atUserIds = atUserIds;
            this.isAtAll = isAtAll;
        }

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public List<String> getAtUserIds() {
            return atUserIds;
        }

        public void setAtUserIds(List<String> atUserIds) {
            this.atUserIds = atUserIds;
        }

        public Boolean getAtAll() {
            return isAtAll;
        }

        public void setAtAll(Boolean atAll) {
            isAtAll = atAll;
        }
    }
}
