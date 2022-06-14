package com.alei.alirobot.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author LeiLiMin
 * @Description: 钉钉机器人-Utils
 * @date: 2022/6/14
 */
public class RobotUtils {
    /**
     * header中的timestamp + "\n" + 机器人的appSecret当做签名字符串，使用HmacSHA256算法计算签名，然后进行Base64 encode，得到最终的签名值。
     * @param appSecret: 对应"群机器人"的数字签名
     */
    public static String calculateSign(String appSecret) throws Exception  {
        long currentMills = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        // 2.生产数字签名
        String stringToSign = currentMills + "\n" + appSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return sign;
    }
}
