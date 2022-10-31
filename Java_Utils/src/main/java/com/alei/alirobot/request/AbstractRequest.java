package com.alei.alirobot.request;


import com.alei.alirobot.config.ConfigConstant;
import com.alei.alirobot.request.enums.UseMsgType;
import com.alei.alirobot.utils.RobotUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author LeiLiMin
 * @description: 消息请求实体抽象类
 * @date: 2022/6/15
 */
@Data
@Slf4j
public abstract class AbstractRequest implements Request {
    /**
     * 请求参数
     */
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 获取真实使用消息类型: 由于At对象可以装饰使用消息类型,所以需要再次获取真实的使用消息类型
     */
    public String getRealUseMsgType() {
        if (this instanceof At) {
            return ((At) this).getRequest().getUseMsgType();
        }
        return this.getUseMsgType();
    }

    /**
     * 获取自身使用消息类型
     * : At对象没有"使用消息类型"
     */
    public String getUseMsgType() {
        return (String) this.getParamMap().get(RequestConstant.MSG_TYPE);
    }

    /**
     * 基础消息类型-Text
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Text extends AbstractRequest {
        /**
         * 文本内容
         */
        private String content;

        public Text(String content) {
            this.getParamMap().put(RequestConstant.MSG_TYPE, UseMsgType.TEXT_BASIC_MSG.getUseMsgType());
            this.content = content;
        }

        @Override
        public Map<String, Object> putInfo() throws IllegalAccessException {
            Map<String, Object> entry = RobotUtils.objConvertToMap(this);
            this.getParamMap().put(RequestConstant.MSG_TYPE_TEXT, entry);
            return this.getParamMap();
        }
    }

    /**
     * 基础消息类型-Link
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Link extends AbstractRequest {
        /**
         * 消息体
         */
        private String text;

        /**
         * 消息标题
         */
        private String title;

        /**
         * 图片地址
         */
        private String picUrl;

        /**
         * 链接地址
         */
        private String messageUrl;

        public Link() {
            this.getParamMap().put(RequestConstant.MSG_TYPE, UseMsgType.LINK_BASIC_MSG.getUseMsgType());
        }

        @Override
        public Map<String, Object> putInfo() throws IllegalAccessException {
            Map<String, Object> entry = RobotUtils.objConvertToMap(this);
            this.getParamMap().put(RequestConstant.MSG_TYPE_LINK, entry);
            return this.getParamMap();
        }
    }

    /**
     * 基础消息类型-Markdown
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Markdown extends AbstractRequest {
        /**
         * 消息标题
         */
        private String title;

        /**
         * 消息体
         * 如需要实现@效果,需要text内出现"@158xxx" 与 {@link At#atMobiles}内对应即可
         */
        private String text;

        public Markdown() {
            this.getParamMap().put(RequestConstant.MSG_TYPE, UseMsgType.MARKDOWN_BASIC_MSG.getUseMsgType());
        }

        @Override
        public Map<String, Object> putInfo() throws IllegalAccessException {
            Map<String, Object> entry = RobotUtils.objConvertToMap(this);
            this.getParamMap().put(RequestConstant.MSG_TYPE_MARKDOWN, entry);
            return this.getParamMap();
        }
    }

    /**
     * 基础消息类型-ActionCard整体跳转
     * : 单个按钮,点击整个报警信息都会进行跳转
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ActionCardSingle extends AbstractRequest {
        /**
         * 链接的打开方式
         * true: 钉钉内部打开
         * false: 外部浏览器打开
         */
        private boolean pcSlide = true;

        /**
         * 消息标题
         */
        private String title;

        /**
         * 消息体
         */
        private String text;

        /**
         * 单个按钮的标题
         */
        private String singleTitle;

        /**
         * 单个按钮的跳转链接
         */
        private String singleURL;

        public ActionCardSingle() {
            this.getParamMap().put(RequestConstant.MSG_TYPE, UseMsgType.ACTIONCARD_BASIC_MSG.getUseMsgType());
        }

        @Override
        public Map<String, Object> putInfo() throws IllegalAccessException {
            Map<String, Object> entry = RobotUtils.objConvertToMap(this);
            this.getParamMap().put(RequestConstant.MSG_TYPE_ACTIONCARD, entry);
            return this.getParamMap();
        }

        public void setSingleURL(String singleURL) {
            this.singleURL = this.pcSlide ? singleURL : RobotUtils.genExternalBrowserUrl(singleURL);
        }
    }


    /**
     * 基础消息类型-ActionCard多按钮
     * : 多个不同的按钮,只有点击按钮才会进行跳转
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ActionCardMultiple extends AbstractRequest {
        /**
         * 链接的打开方式
         * true: 钉钉内部打开
         * false: 外部浏览器打开
         */
        private boolean pcSlide = true;

        /**
         * 消息标题
         */
        private String title;

        /**
         * 消息体
         */
        private String text;

        /**
         * 按钮排列顺序。
         * 0：按钮竖直排列
         * 1：按钮横向排列
         */
        private int btnOrientation = 0;

        /**
         * 按钮集合
         */
        private List<ActionButton> btns;

        public ActionCardMultiple() {
            this.getParamMap().put(RequestConstant.MSG_TYPE, UseMsgType.ACTIONCARD_BASIC_MSG.getUseMsgType());
        }

        @Override
        public Map<String, Object> putInfo() throws IllegalAccessException {
            Map<String, Object> entry = RobotUtils.objConvertToMap(this);
            this.getParamMap().put(RequestConstant.MSG_TYPE_ACTIONCARD, entry);
            return this.getParamMap();
        }

        /**
         * 设置按钮参数
         *
         * @param btnSetting 按钮设置 key:按钮名称,Value:按钮跳转链接
         */
        public void setBtns(Map<String, String> btnSetting) {
            List<ActionButton> buttons = new ArrayList<>();
            if (Objects.nonNull(btnSetting) && btnSetting.entrySet().size() > 0) {
                for (Map.Entry<String, String> entry : btnSetting.entrySet()) {
                    String url = this.pcSlide ? entry.getValue() : RobotUtils.genExternalBrowserUrl(entry.getValue());
                    buttons.add(new ActionButton(entry.getKey(), url));
                }
            }
            this.btns = buttons;
        }

        /**
         * ActionCard内部类-按钮Bean,不对外暴露
         */
        @Data
        @AllArgsConstructor
        static class ActionButton {
            /**
             * 按钮标题
             */
            private String title;

            /**
             * 按钮跳转链接
             */
            private String actionURL;
        }
    }

    /**
     * 使用At装饰其他基础消息类型：扩充@信息
     * : 目前仅支持{@link Text},{@link Markdown}这2种消息类型
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class At extends AbstractRequest {
        /**
         * 根据手机号@用户
         */
        private List<String> atMobiles;

        /**
         * 根据用户ID@用户
         */
        private List<String> atUserIds;

        /**
         * 是否@所有人
         */
        private Boolean isAtAll;

        /**
         * 被修饰的对象：基础消息类型
         */
        private AbstractRequest request;

        /**
         * 包装{@link AbstractRequest},扩展为带At信息的消息类型
         */
        public At(AbstractRequest request) {
            this.request = request;
        }

        /**
         * 生成请求消息体,包括"使用消息信息",At信息
         *
         * @return 请求消息体
         */
        @Override
        public Map<String, Object> putInfo() throws IllegalAccessException {
            Map<String, Object> beDecoratedMap = this.request.putInfo();

            // 被装饰对象引用置空,避免序列化多余字段
            this.setRequest(null);

            // 填充"At装饰对象"信息
            Map<String, Object> subMap = RobotUtils.objConvertToMap(this);
            beDecoratedMap.put(ConfigConstant.MSG_DECORATE_AT, subMap);
            return beDecoratedMap;
        }
    }
}
