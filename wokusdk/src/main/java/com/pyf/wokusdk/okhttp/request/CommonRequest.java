package com.pyf.wokusdk.okhttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 创建请求对象，包括get请求对象和post请求对象
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public class CommonRequest {

    /**
     * 创建get请求对象
     *
     * @param url
     *         网址
     * @param params
     *         请求参数
     * @return 返回get请求对象
     */
    public Request createGetRequest(String url, RequestParams params) {
        StringBuilder sb = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(sb.substring(0, sb.length() - 1)).build();
    }

    /**
     * 创建post请求对象
     *
     * @param url
     *         网址
     * @param params
     *         请求参数
     * @return 返回get请求对象
     */
    public Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();
    }
}
