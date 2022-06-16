package com.alei.alirobot.service;

import com.alei.alirobot.config.CustomRobotConfig;
import com.alei.alirobot.request.AbstractRequest;
import com.alei.alirobot.request.RequestConstant;
import com.alei.alirobot.test.OkhttpClientUtil;
import com.alei.alirobot.utils.RobotUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/6/15
 */
@Slf4j
public class CustomRobotClient extends AbstractRobotClient<CustomRobotConfig> {

    /**
     * 自定义机器人: 检测配置
     */
    @Override
    public void checkByConfig(CustomRobotConfig config, AbstractRequest request) {
    }

    @Override
    Map<String, String> settingRequestParamByConfig(CustomRobotConfig config) {
        HashMap<String, String> paramMap = new HashMap<>();
        // 1.加签信息
        paramMap.put(RequestConstant.ACCESS_TOKEN, config.getAccessToken());

        // 2.数字签名部分
        long currentMills = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        paramMap.put(RequestConstant.TIMESTAMP, String.valueOf(currentMills));

        if (!StringUtils.isBlank(config.getSecret())) {
            try {
                paramMap.put(RequestConstant.SIGN, RobotUtils.calculateSign(config.getSecret(), currentMills));
            } catch (Exception e) {
                log.error("[CustomRobotClient Error!] settingRequestParamByConfig fail");
            }
        }
        return paramMap;
    }

    @Override
    void sendBasicMsg(CustomRobotConfig config, AbstractRequest request) throws IOException {
        this.checkByConfig(config, request);
        Map<String, String> requestParam = this.settingRequestParamByConfig(config);

        // 3.发送设报警信息
        OkhttpClientUtil okhttpClientUtil = new OkhttpClientUtil();
        Response post = okhttpClientUtil.post(config.getWebhook(), requestParam, request.putInfo());

    }

    @Override
    public void sendMsg(AbstractRequest request) {

    }

    public static void main(String[] args) throws IOException {
        CustomRobotConfig customRobotConfig = new CustomRobotConfig();
        customRobotConfig.setWebhook("https://oapi.dingtalk.com/robot/send");
        customRobotConfig.setAccessToken("012894b09b204678788afefa0741b766dc5c609c87c628bb29e531453329f045");
        customRobotConfig.setSecret("SEC18fa307bf960d88d9a263871d954730d82c3ee63ed284b4b73f1fd6862af845e");
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("阿雷测试");
        customRobotConfig.setKeyWords(keyWords);

        StringBuilder sb = new StringBuilder();
        sb.append("阿雷测试:\n");
        sb.append("fix processor" + "\n");
        AbstractRequest.Text request = new AbstractRequest.Text(sb.toString());

        new CustomRobotClient().sendBasicMsg(customRobotConfig,request);
    }
}
