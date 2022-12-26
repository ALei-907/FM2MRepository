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
    private static final String hostKey = "redisHost";

    private static final String portKey = "redisPort";

    private static final String authKey = "redisAuth";

    /**
     * 资源文件路径
     */
    private final static String sourcePath = "JavaUtils.properties";


    private Jedis jedisClient;

    public RedisClient() {
        try {
            Map<String, String> clientInfo = FileReadUtil.convertToMapByPp(this.sourcePath);
            this.jedisClient = new Jedis(clientInfo.get(this.hostKey), Integer.parseInt(clientInfo.get(this.portKey)));
            this.jedisClient.auth(clientInfo.get(this.authKey));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * HGetSet: 让Hash的存取行为分布式安全
     */
    public Object hGetSet(List<String> keys, List<String> args) {
        return jedisClient.eval(LuaConstant.H_GET_SET, keys, args);
    }

    public Object eval(String script, List<String> keys, List<String> args) {
        return jedisClient.eval(script, keys, args);
    }

    public long hincr(String key, String attr, long delta) {
        return jedisClient.hincrBy(key, attr, delta);
    }


    // /**
    //  * 没有任何参数的Lua脚本
    //  *
    //  * @param script: Lua脚本
    //  */
    // public String evalNothing(String script) {
    //     Object eval = jedisClient.eval(script);
    //     return eval.toString();
    // }

    // /**
    //  * 没有任何参数的Lua脚本
    //  *
    //  * @param script: Lua脚本
    //  */
    // public String evalNoArgs(String script, List<String> keys) {
    //     String[] keysArr = keys.toArray(new String[keys.size()]);
    //     Object eval = jedisClient.eval(script, keysArr.length, keysArr);
    //     return eval.toString();
    // }
    //
    // public String evalArgs(String script, List<String> keys, List<String> args) {
    //     Object eval = jedisClient.eval(script, keys, args);
    //     return String.valueOf(eval);
    // }


}
