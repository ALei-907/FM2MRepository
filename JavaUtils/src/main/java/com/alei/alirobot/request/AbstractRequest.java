package com.alei.alirobot.request;

import com.alei.alirobot.utils.RobotUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description: 消息请求实体抽象类
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
     * 基础消息类型-Text
     */
    @Data
    public static class Text extends AbstractRequest {
        /**
         * 文本内容
         */
        private String content;

        public Text(String content) {
            this.getParamMap().put(RequestConstant.MSG_TYPE, RequestConstant.MSG_TYPE_TEXT);
            this.content = content;
        }


        @Override
        public Map<String, Object> putInfo() {
            Map<String, Object> entry = RobotUtils.objConvertMap(this);
            this.getParamMap().put(RequestConstant.MSG_TYPE_TEXT, entry);
            return this.getParamMap();
        }
    }

    /**
     * 基础消息类型-Text
     */
    @Data
    public static class Link implements Request {
        private String text;
        private String title;
        private String picUrl;
        private String messageUrl;

        @Override
        public Map<String, Object> putInfo() {
            return null;
        }
    }


    /**
     * 使用At装饰其他基础消息类型：扩充@信息
     */
    @Data
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

        public At(AbstractRequest request) {
            this.request = request;
        }

        @Override
        public Map<String, Object> putInfo() {
            Map<String, Object> beDecoratedMap = this.request.putInfo();

            // 丢被装饰对象的引用,避免序列化多余字段
            this.setRequest(null);

            // 填充"At装饰对象"信息
            Map<String, Object> subMap = RobotUtils.objConvertMap(this);
            beDecoratedMap.put(RequestConstant.MSG_DECORATE_AT, subMap);
            return beDecoratedMap;
        }
    }
}
