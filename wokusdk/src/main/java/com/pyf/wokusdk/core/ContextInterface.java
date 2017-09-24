package com.pyf.wokusdk.core;

/**
 * 最终通知应用层是否成功
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/24
 */
public interface ContextInterface {

    void onSuccess();

    void onFailed();

    void onClickVideo(String url);
}
