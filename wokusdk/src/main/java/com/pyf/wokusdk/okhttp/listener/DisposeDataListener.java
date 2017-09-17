package com.pyf.wokusdk.okhttp.listener;

/**
 * 请求回调接口
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public interface DisposeDataListener {

    /**
     * 请求成功回调事件处理
     */
    void onSuccess(Object responseObj);

    /**
     * 请求失败回调事件处理
     */
    void onFailure(Object reasonObj);
}
