package com.alei.alirobot.test;


import com.alei.alirobot.request.AbstractRequest;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @Author LeiLiMin
 * @Description: 文本类型通知
 * @date: 2022/3/1
 */
// TODO: 适配SpringBoot改造
public class DingRobotService {
    // 安全设置
    /**
     * 需要包含的关键词
     */
    protected List<String> keyWords;

    {
        keyWords = new ArrayList<>();
        keyWords.add("阿雷测试");
    }

    /**
     * 数字签名
     */
    protected String secret = "SEC18fa307bf960d88d9a263871d954730d82c3ee63ed284b4b73f1fd6862af845e";

    /**
     * 自定义--是否开启该机器人
     */
    protected boolean onOff = true;

    // 消息设置
    /**
     * 推送目标地址
     */
    protected String webhook = "https://oapi.dingtalk.com/robot/send";

    /**
     * access_token
     */
    protected String accessToken = "012894b09b204678788afefa0741b766dc5c609c87c628bb29e531453329f045";


    /**
     * 钉钉预警-- 发送Text类型消息
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public Response sendTextMsg(String msg, TextType.At at) throws Exception {
        // 1.通用设置
        Map<String, String> paramMap = globalOps(msg, at);

        // 2.生产请求体
        TextType textType = new TextType(msg);
        textType.setAt(at);

        // 3.发送设报警信息
        OkhttpClientUtil okhttpClientUtil = new OkhttpClientUtil();
        Response post = okhttpClientUtil.post(webhook, paramMap, textType);
        return post;
    }

    /**
     * 通用操作
     */
    private Map<String, String> globalOps(String msg, TextType.At at) throws Exception {
        assert this.onOff = true;
        // 1.消息检测 - 是否包含关键字
        if (!this.containsKeyWords(msg)) {
            throw new RuntimeException("the msg not contains keywords");
        }

        // 3.系统检测 - paramMap生产
        return genParamMap();
    }


    /**
     * 关键字检测
     *
     * @param msg 发送的信息
     * @return
     */
    private boolean containsKeyWords(String msg) {
        // 不存在关键词过滤限制
        if (Objects.isNull(keyWords) || keyWords.size() == 0) return true;

        // 需要检测关键字过滤
        for (String keyWord : keyWords) {
            if (msg.contains(keyWord)) return true;
        }
        return false;
    }

    /**
     * 生产 请求查询参数
     */
    private Map<String, String> genParamMap() throws Exception {
        HashMap<String, String> paramMap = new HashMap<>();
        // 1.通用部分
        paramMap.put("access_token", this.accessToken);

        // 2.数字签名部分
        if (!StringUtils.isBlank(this.secret)) {
            long currentMills = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            String sign = this.genSign(currentMills);
            paramMap.put("timestamp", String.valueOf(currentMills));
            paramMap.put("sign", sign);
        }
        return paramMap;
    }

    /**
     * 生产 数据签名
     *
     * @param timestamp 时间戳-毫秒值
     */
    private String genSign(Long timestamp) throws Exception {
        // 1.下个断言，该方法不允许在没有密钥时进入
        assert !StringUtils.isBlank(this.secret);

        // 2.生产数字签名
        String stringToSign = timestamp + "\n" + this.secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(this.secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");

        // 3.返回生成的签名
        return sign;

    }


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        DingRobotService textInvokeType = new DingRobotService();

        // 内容
        StringBuilder sb = new StringBuilder();
        sb.append("阿雷测试:\n");
        sb.append("fix processor" + "\n");
        // @
        ArrayList<String> arMobiles = new ArrayList<>();
        arMobiles.add("13683364007");
        TextType.At at = new TextType.At(arMobiles, null, false);
        // textInvokeType.sendTextMsg(sb.toString(), at);

        // 2
        System.out.println(textInvokeType.sendTextMsg2(sb.toString(), arMobiles));
    }

    /**
     * 钉钉预警-- 发送Text类型消息
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public Response sendTextMsg2(String msg, ArrayList<String> arMobiles) throws Exception {
        // 0.通用设置
        Map<String, String> paramMap = globalOps(msg, null);

        // 1.生产请求体
        AbstractRequest.At at = new AbstractRequest.At(new AbstractRequest.Text(msg));
        at.setAtMobiles(arMobiles);


        // 3.发送设报警信息
        OkhttpClientUtil okhttpClientUtil = new OkhttpClientUtil();
        Response post = okhttpClientUtil.post(webhook, paramMap, at.putInfo());
        return post;
    }
}
