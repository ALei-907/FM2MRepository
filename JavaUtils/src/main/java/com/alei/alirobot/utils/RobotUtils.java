package com.alei.alirobot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author LeiLiMin
 * @Description: 钉钉机器人-Utils
 * @date: 2022/6/14
 */
@Slf4j
public class RobotUtils {
    /**
     * header中的timestamp + "\n" + 机器人的appSecret当做签名字符串，使用HmacSHA256算法计算签名，然后进行Base64 encode，得到最终的签名值。
     *
     * @param appSecret: 对应"群机器人"的数字签名
     * @param currentMills: 当前时间戳
     */
    public static String calculateSign(String appSecret, long currentMills) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        // 生成数字签名
        String stringToSign = currentMills + "\n" + appSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)), String.valueOf(StandardCharsets.UTF_8));
    }

    /**
     * 将对象转为Map< String , Object >
     *
     * @param obj: 需要进行转换的对象
     */
    public static Map<String, Object> objConvertToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object o = field.get(obj);
            if (Objects.nonNull(o)) {
                map.put(field.getName(), o);
            }
        }
        return map;
    }

    /**
     * 将URL访问方式修改为外部浏览器访问的URL
     * : 如果使用原URL进行跳转,将会在钉钉内部打开链接
     * : 使用包装过的URL,将使用外部浏览器打开
     *
     * @param originUrl 原始URL
     */
    public static String genExternalBrowserUrl(String originUrl) {
        assert StringUtils.isNotBlank(originUrl);
        return "dingtalk://dingtalkclient/page/link?url=" + originUrl + "&pc_slide=false";
    }
}
