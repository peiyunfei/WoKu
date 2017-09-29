package com.pyf.woku.network.http;

import com.pyf.woku.bean.CourseDetail;
import com.pyf.woku.bean.Update;
import com.pyf.woku.bean.User;
import com.pyf.wokusdk.okhttp.CommonOkHttpClient;
import com.pyf.wokusdk.okhttp.listener.DisposeDataHandle;
import com.pyf.wokusdk.okhttp.listener.DisposeDataListener;
import com.pyf.wokusdk.okhttp.listener.DisposeDownloadListener;
import com.pyf.wokusdk.okhttp.request.CommonRequest;
import com.pyf.wokusdk.okhttp.request.RequestParams;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public class RequestCenter {

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params,
                                   DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.request(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 用户登陆请求
     */
    public static void login(String userName, String passwd, DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("mb", userName);
        params.put("pwd", passwd);
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, User.class);
    }

    public static void courseDetail(int courseId, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("course_id", String.valueOf(courseId));
        RequestCenter.postRequest(HttpConstants.COURSE_DETAIL, params, listener, CourseDetail.class);
    }

    /**
     * 应用版本号请求
     */
    public static void checkVersion(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.UPDATE, null, listener, Update.class);
    }

    //    public static void requestRecommandData(DisposeDataListener listener) {
    //        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommandModel.class);
    //    }

    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }

}
