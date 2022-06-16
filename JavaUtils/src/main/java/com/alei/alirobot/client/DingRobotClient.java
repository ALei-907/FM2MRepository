package com.alei.alirobot.client;

import com.google.gson.Gson;
import lombok.Data;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author LeiLiMin
 * @Description: OkHttpClient 工具类
 * @date: 2022/1/14
 */
@Data
public class DingRobotClient {
    private Gson gson = new Gson();

    private final OkHttpClient httpClient;

    // 最大连接数
    private static final int MAX_CONNECTION = 100;

    // 每个route能使用的最大连接数，一般和MAX_CONNECTION取值一样
    private static final int MAX_CONCURRENT_CONNECTIONS = 100;

    // 建立连接的超时时间，单位毫秒
    private static final int CONNECTION_TIME_OUT = 3000;

    // 请求超时时间，单位毫秒
    private static final int REQUEST_TIME_OUT = 3000;

    // 长连接的时间
    private static final long keepAliveDuration = 5;

    public DingRobotClient() {
        ConnectionPool connectionPool = new ConnectionPool(MAX_CONNECTION, keepAliveDuration, TimeUnit.MINUTES);
        httpClient = new OkHttpClient.Builder()
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .build();
    }


    // public void execute






















    public Response get(String url, Map<String, String> paramMap, Map<String, String> headerMap) throws IOException {
        // 构建url参数
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        // 构建请求参数
        setParameters(urlBuilder, paramMap);
        // 构建请求头参数
        Request.Builder requestBuilder = new Request.Builder();
        setHeaders(requestBuilder, headerMap);
        // 构建请求题
        Request get = requestBuilder
                .method("GET", null)
                .url(urlBuilder.build())
                .build();
        // 执行方法
        Call call = httpClient.newCall(get);
        Response response = call.execute();
        return response;
    }


    public Response post(String url, Map<String, String> paramMap, Object json) throws IOException {
        // 构建url参数
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        // 构建请求参数
        setParameters(urlBuilder, paramMap);
        // 构建请求体
        Request post = buildRequest(urlBuilder, "POST", json);
        // 执行方法
        Call call = httpClient.newCall(post);
        Response response = call.execute();
        return response;
    }

    /**
     * 设置url查询参数
     *
     * @param urlBuilder url构建
     * @param paramMap   参数映射
     */
    private void setParameters(HttpUrl.Builder urlBuilder, Map<String, String> paramMap) {
        if (Objects.nonNull(paramMap)) {
            paramMap.forEach(urlBuilder::addQueryParameter);
        }
    }

    private void setHeaders(Request.Builder requestBuilder, Map<String, String> headerMap) {
        if (Objects.nonNull(headerMap) && !headerMap.entrySet().isEmpty()) {
            // 添加请求头
            Headers.Builder headersBuilder = new Headers.Builder();
            headerMap.forEach(headersBuilder::add);
            requestBuilder.headers(headersBuilder.build());
        }
    }

    /**
     * 设置请求体
     *
     * @param urlBuilder     url构建
     * @param httpMethodType 请求方式
     * @param json           json数据
     */
    private Request buildRequest(HttpUrl.Builder urlBuilder, String httpMethodType, Object json) {
        // 构建请求
        Request.Builder requestBuilder = new Request.Builder();
        // 请求体，如果json为空，则请求体也为空
        RequestBody requestBody = Objects.nonNull(json) ? RequestBody.create(gson.toJson(json), MediaType.parse("application/json; charset=utf-8")) : null;
        Request request = requestBuilder
                .method(httpMethodType, requestBody)
                .url(urlBuilder.build())
                .build();
        return request;
    }
}
