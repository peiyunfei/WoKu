package com.pyf.wokusdk.core.video;

import android.content.Intent;
import android.view.ViewGroup;

import com.pyf.wokusdk.activity.AdBrowserActivity;
import com.pyf.wokusdk.core.ContextInterface;
import com.pyf.wokusdk.module.VideoValue;
import com.pyf.wokusdk.report.ReportManager;
import com.pyf.wokusdk.response.HttpConstant;
import com.pyf.wokusdk.response.HttpConstant.Params;
import com.pyf.wokusdk.util.ResponseEntityToModule;
import com.pyf.wokusdk.util.Utils;
import com.pyf.wokusdk.view.CustomVideoView.FrameImageLoadListener;

/**
 * 与应用层进行交互的类
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/24
 */
public class VideoContext implements VideoSlot.SDKSlotListener {

    private ViewGroup mParentView;
    private VideoValue mVideoValue;
    private FrameImageLoadListener mFrameImageLoadListener;
    private VideoSlot mVideoSlot;
    private ContextInterface mContextInterface;

    public VideoContext(ViewGroup parentView, String videoValueJson,
                        FrameImageLoadListener frameLoadListener) {
        mParentView = parentView;
        mVideoValue = (VideoValue) ResponseEntityToModule.
                parseJsonToModule(videoValueJson, VideoValue.class);
        mFrameImageLoadListener = frameLoadListener;
        load();
    }

    private void load() {
        if (mVideoValue != null && mVideoValue.resource != null) {
            mVideoSlot = new VideoSlot(mVideoValue, this, mFrameImageLoadListener);
            // 发送解析成功事件
            sendAnalyseReport(Params.ad_analize, HttpConstant.AD_DATA_SUCCESS);
        } else {
            // 创建空的slot,不响应任何事件
            mVideoSlot = new VideoSlot(null, this, mFrameImageLoadListener);
            if (mContextInterface != null) {
                mContextInterface.onFailed();
            }
            sendAnalyseReport(Params.ad_analize, HttpConstant.AD_DATA_FAILED);
        }
    }

    private void sendAnalyseReport(Params step, String result) {
        try {
            ReportManager.sendAdMonitor(Utils.isPad(mParentView.getContext().
                            getApplicationContext()), mVideoValue == null ? "" : mVideoValue.resourceID,
                    (mVideoValue == null ? null : mVideoValue.adid), Utils.getAppVersion(mParentView.getContext()
                            .getApplicationContext()), step, result);
        } catch (Exception e) {

        }
    }

    public void setContextInterface(ContextInterface contextInterface) {
        mContextInterface = contextInterface;
    }

    /**
     * 根据滑动距离来判断是否可以自动播放, 出现超过50%自动播放，离开超过50%,自动暂停
     */
    public void updateAdInScrollView() {
        if (mVideoSlot != null) {
            mVideoSlot.updateAdInScrollView();
        }
    }

    @Override
    public ViewGroup getParent() {
        return mParentView;
    }

    @Override
    public void onVideoPlaySuccess() {
        if (mContextInterface != null) {
            mContextInterface.onSuccess();
        }
        sendAnalyseReport(Params.ad_load, HttpConstant.AD_PLAY_SUCCESS);
    }

    @Override
    public void onVideoPlayFailed() {
        if (mContextInterface != null) {
            mContextInterface.onFailed();
        }
        sendAnalyseReport(Params.ad_load, HttpConstant.AD_PLAY_FAILED);
    }

    @Override
    public void onVideoPlayComplete() {

    }

    @Override
    public void onClickVideo(String url) {
        if (mContextInterface != null) {
            mContextInterface.onClickVideo(url);
        } else {
            Intent intent = new Intent(mParentView.getContext(), AdBrowserActivity.class);
            intent.putExtra(AdBrowserActivity.KEY_URL, url);
            mParentView.getContext().startActivity(intent);
        }
    }
}
