package com.alei.alirobot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

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
 * @Author LeiLiMin
 * @Description: 钉钉机器人-Utils
 * @date: 2022/6/14
 */
@Slf4j
public class RobotUtils {
    /**
     * header中的timestamp + "\n" + 机器人的appSecret当做签名字符串，使用HmacSHA256算法计算签名，然后进行Base64 encode，得到最终的签名值。
     * @param appSecret: 对应"群机器人"的数字签名
     * @param currentMills: 当前时间戳
     */
    public static String calculateSign(String appSecret, long currentMills) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        // 生成数字签名
        String stringToSign = currentMills + "\n" + appSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
    }

    /**
     * 将对象转为Map< String , Object >
     * @param obj: 需要进行转换的对象
     */
    public static Map<String, Object> objConvertMap(Object obj) throws IllegalAccessException {
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
}
