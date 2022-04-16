package com.alei.openapi.sensorsbi;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author LeiLiMin
 * @Description: 获取Aliyun的权限Token
 * 信息来自: https://help.aliyun.com/document_detail/28763.htm?spm=a2c4g.11186623.0.0.47976d202eDhfC#reference-clc-3sv-xdb
 * @date: 2022/1/24
 */
public class AppTokenService {
    // 设置编码集
    private static final String ENCODING = "UTF-8";

    // 日期格式化
    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 构造规范化请求字符串
     */
    private static String percentEncode(String value) {
        try {
            return value != null ? URLEncoder.encode(value, ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 指定格式化日期
     *
     * @param date
     * @return
     */
    private static String formatIso8601Date(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    /**
     * 准备所有的请求参数
     */
    public Map<String, String> getParaMap() {
        String signatureNonce = UUID.randomUUID().toString();
        String date = formatIso8601Date(new Date());
        String ak = "test_AccessKeyId";                 // TODO: 需要具体的权限信息

        // 1.参数准备
        HashMap<String, String> paramMap = new HashMap<>();
        // API自由参数
        paramMap.put("Action", "AssumeRole");
        paramMap.put("RoleArn", "要扮演的RAM角色ARN");    // TODO: 具体的ram
        paramMap.put("RoleSessionName", "client");
        paramMap.put("DurationSeconds", "3600");

        // 公共请求参数
        paramMap.put("Format", "JSON");
        paramMap.put("Version", "2015-04-01");
        paramMap.put("SignatureMethod", "HMAC-SHA1");
        paramMap.put("SignatureVersion", "1.0");
        paramMap.put("AccessKeyId", ak);
        paramMap.put("SignatureNonce", signatureNonce);
        paramMap.put("Timestamp", date);

        String signature = getSignature(paramMap);
        paramMap.put("Signature", signature);
        return paramMap;
    }

    /**
     * 根据参数Map生成Signature
     * 流程来源: https://help.aliyun.com/document_detail/28761.htm?spm=a2c4g.11186623.0.0.7b116dc1KyaMCF#concept-1940324
     * @param paramMap
     * @return
     */
    public String getSignature(Map<String, String> paramMap) {
        final String SEPARATOR = "&";
        final String HTTP_METHOD = "GET";
        try {
            // 1.排序Key
            String[] sortedKeys = paramMap.keySet().toArray(new String[paramMap.keySet().size()]);
            Arrays.sort(sortedKeys);

            // 2.拼接 stringToSign 字符串
            StringBuilder stringToSign = new StringBuilder();
            stringToSign.append(HTTP_METHOD).append(SEPARATOR);
            stringToSign.append(percentEncode("/")).append(SEPARATOR);
            StringBuilder canonicalizedQueryString = new StringBuilder();
            for (String key : sortedKeys) {
                canonicalizedQueryString.append("&")
                        .append(percentEncode(key)).append("=")
                        .append(percentEncode(paramMap.get(key)));
            }
            stringToSign.append(percentEncode(
                    canonicalizedQueryString.substring(1)));


            // 3.计算签名
            final String ALGORITHM = "HmacSHA1";
            final String ENCODING = "UTF-8";
            String key = "Test-AccessKeySecret" + "&";      // TODO:具体的密钥，并且需要在末尾加'&'
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM));
            byte[] signData = mac.doFinal(stringToSign.toString().getBytes(ENCODING));
            String signature = new String(Base64.encodeBase64(signData));
            return signature;
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
