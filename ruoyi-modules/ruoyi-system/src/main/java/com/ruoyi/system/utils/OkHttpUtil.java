package com.ruoyi.system.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

/**
 * @author jiangxianwei
 * @version 1.0
 * @description: oktttp3的调用工具
 * @date 2022/2/15 13:28
 */
public class OkHttpUtil {

    private OkHttpsClientUtil httpsClientUtil = new OkHttpsClientUtil();

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public JSONObject get(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Connection", "close")// Connection:close
                //.header("User-Agent", "*****")
                .addHeader("Accept", "*/*")
                .url(url)
                .build();

        //同步执行请求，将响应结果存放到response中
        Response response = httpsClientUtil.getNomalClientInstance().newCall(request).execute();
        if (response.isSuccessful()) {
            //处理response的响应消息
            return JSONObject.parseObject(response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public JSONObject post(String url, JSONObject jsonObject) throws IOException {
        //请求体传输json格式的数据
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        for (String e : jsonObject.keySet()) {
            formBodyBuilder.add(e, jsonObject.getString(e));
        }

        //创建请求
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Connection", "close")// Connection:close
                //.header("User-Agent", "*****")
                .addHeader("Accept", "*/*")
                .post(formBodyBuilder.build())
                .build();

        //同步请求
        Response response = httpsClientUtil.getNomalClientInstance().newCall(request).execute();
        if (response.isSuccessful()) {
            return JSONObject.parseObject(response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public JSONObject sslGet(String url, String cerPath) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Connection", "close")// Connection:close
                //.header("User-Agent", "*****")
                .addHeader("Accept", "*/*")
                .url(url)
                .build();

        //同步执行请求，将响应结果存放到response中
        Response response = httpsClientUtil.getSslClientInstance(cerPath).newCall(request).execute();
        if (response.isSuccessful()) {
            //处理response的响应消息
            return JSONObject.parseObject(response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public JSONObject sslPost(String url, String cerPath, JSONObject jsonObject) throws IOException {
        //请求体传输json格式的数据
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        for (String e : jsonObject.keySet()) {
            formBodyBuilder.add(e, jsonObject.getString(e));
        }

        //创建请求
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Connection", "close")// Connection:close
                //.header("User-Agent", "*****")
                .addHeader("Accept", "*/*")
                .post(formBodyBuilder.build())
                .build();

        //同步请求
        Response response = httpsClientUtil.getSslClientInstance(cerPath).newCall(request).execute();
        if (response.isSuccessful()) {
            return JSONObject.parseObject(response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

}
