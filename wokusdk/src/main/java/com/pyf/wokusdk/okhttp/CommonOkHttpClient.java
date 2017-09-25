package com.pyf.wokusdk.okhttp;

import com.pyf.wokusdk.okhttp.https.HttpsUtils;
import com.pyf.wokusdk.okhttp.listener.DisposeDataHandle;
import com.pyf.wokusdk.okhttp.response.CommonFileCallback;
import com.pyf.wokusdk.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 封装OkHttpClient对象
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public class CommonOkHttpClient {

    // 超时时间
    private static final int TIME_OUT = 30;

    /**
     * 发送请求
     *
     * @param request
     *         get请求
     */
    public static Call request(Request request, DisposeDataHandle handle) {
        Call call = OkHttpClientHolder.OKHTTP_CLIENT.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call downloadFile(Request request, DisposeDataHandle handle) {
        Call call = OkHttpClientHolder.OKHTTP_CLIENT.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }

    /**
     * 创建OkHttpClient实例
     */
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
