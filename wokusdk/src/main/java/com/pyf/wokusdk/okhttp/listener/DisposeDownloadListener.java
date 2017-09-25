package com.pyf.wokusdk.okhttp.listener;

/**
 * 监听下载进度
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public interface DisposeDownloadListener extends DisposeDataListener {

    /**
     * 当下载进度改变时回调
     *
     * @param progrss
     *         下载进度
     */
    void onProgress(int progrss);
}
