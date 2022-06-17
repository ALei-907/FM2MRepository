package com.alei.alirobot.request;

import com.alei.alirobot.config.ConfigConstant;
import com.alei.alirobot.request.enums.UseMsgType;
import com.alei.alirobot.utils.RobotUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * : At对象没有使用消息类型
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
     * 基础消息类型-Text
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
     * 使用At装饰其他基础消息类型：扩充@信息
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
