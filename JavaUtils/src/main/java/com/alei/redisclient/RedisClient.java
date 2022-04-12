package com.alei.redisclient;

import com.alei.io.FileReadUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description: jedis调用
 * @date: 2022/4/12
 */
public class RedisClient {
    private String hostKey = "redisHost";

    private String portKey = "redisPort";

    private String authKey = "redisAuth";

    /**
     * 资源文件路径
     */
    private final static String sourcePath = "JavaUtils.properties";


    private Jedis jedisClient;

    public RedisClient() throws IOException {
        if (jedisClient == null) {
            synchronized (this) {
                if (jedisClient == null) {
                    Map<String, String> clientInfo = FileReadUtil.convertToMapByPp(sourcePath);
                    jedisClient = new Jedis(clientInfo.get(hostKey), Integer.parseInt(clientInfo.get(portKey)));
                    jedisClient.auth(clientInfo.get(authKey));
                }
            }
        }
    }

    /**
     * 没有任何参数的Lua脚本
     *
     * @param script: Lua脚本
     */
    public String evalNothing(String script) {
        Object eval = jedisClient.eval(script);
        return eval.toString();
    }

    /**
     * 没有任何参数的Lua脚本
     *
     * @param script: Lua脚本
     */
    public String evalNoArgs(String script, List<String> keys) {
        String[] keysArr = keys.toArray(new String[keys.size()]);
        Object eval = jedisClient.eval(script, keysArr.length, keysArr);
        return eval.toString();
    }

    public String evalArgs(String script, List<String> keys, List<String> args) {
        Object eval = jedisClient.eval(script, keys, args);
        return String.valueOf(eval);
    }
}
