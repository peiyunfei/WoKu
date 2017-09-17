package com.pyf.wokusdk.okhttp;

import com.pyf.wokusdk.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public class CommonOkHttpClient {

    // 超时时间
    private static final int TIME_OUT = 30;

    /**
     * 发送get请求
     *
     * @param request
     *         get请求
     */
    public static Call get(Request request) {
        Call call = OkHttpClientHolder.OKHTTP_CLIENT.newCall(request);
//        call.enqueue();
        return call;
    }

    /**
     * 发送post请求
     *
     * @param request
     *         post请求
     */
    public static Call post(Request request) {
        Call call = OkHttpClientHolder.OKHTTP_CLIENT.newCall(request);
//        call.enqueue();
        return call;
    }

    private static final class OkHttpClientHolder {

        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final OkHttpClient OKHTTP_CLIENT = createOkHttpClient();

        private static final OkHttpClient createOkHttpClient() {
            BUILDER.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            //            BUILDER.cookieJar(new SimpleCookieJar());
            BUILDER.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            BUILDER.readTimeout(TIME_OUT, TimeUnit.SECONDS);
            BUILDER.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
            BUILDER.followRedirects(true);
            /**
             * trust all the https point
             */
            BUILDER.sslSocketFactory(HttpsUtils.getSslSocketFactory());
            return BUILDER.build();
        }
    }
}
