package com.pyf.wokusdk.core.video;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pyf.wokusdk.activity.AdBrowserActivity;
import com.pyf.wokusdk.constant.SDKConstant;
import com.pyf.wokusdk.core.VideoParameters;
import com.pyf.wokusdk.module.VideoValue;
import com.pyf.wokusdk.report.ReportManager;
import com.pyf.wokusdk.util.Utils;
import com.pyf.wokusdk.view.CustomVideoView;
import com.pyf.wokusdk.view.VideoFullDialog;

/**
 * 播放器的业务逻辑层
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/24
 */
public class VideoSlot implements CustomVideoView.VideoPlayerListener {

    private Context mContext;

    private CustomVideoView mVideoView;
    private ViewGroup mParentView;

    private VideoValue mVideoValue;
    private SDKSlotListener mSDKSlotListener;
    // 是否可自动暂停标志位
    private boolean mCanPause = false;
    // 防止将要滑入滑出时播放器的状态改变
    private int mLastArea = 0;

    public VideoSlot(VideoValue videoValue, SDKSlotListener sdkSlotListener,
                     CustomVideoView.FrameImageLoadListener frameImageLoadListener) {
        mVideoValue = videoValue;
        mSDKSlotListener = sdkSlotListener;
        mParentView = sdkSlotListener.getParent();
        mContext = mParentView.getContext();
        initVideoView(frameImageLoadListener);
    }

    private void initVideoView(CustomVideoView.FrameImageLoadListener frameImageLoadListener) {
        mVideoView = new CustomVideoView(mContext, mParentView);
        if (mVideoView != null) {
            mVideoView.setDataSource(mVideoValue.resource);
            mVideoView.setFrameURI(mVideoValue.thumb);
            mVideoView.setVideoPlayerListener(this);
            mVideoView.setFrameLoadListener(frameImageLoadListener);
        }
        RelativeLayout paddingView = new RelativeLayout(mContext);
        paddingView.setBackgroundColor(mContext.getResources().getColor(android.R.color.black));
        paddingView.setLayoutParams(mVideoView.getLayoutParams());
        mParentView.addView(paddingView);
        mParentView.addView(mVideoView);
    }

    /**
     * 滑入播放，滑出暂停
     */
    public void updateAdInScrollView() {
        // 获取播放器在屏幕中可见的比例
        int currentArea = Utils.getVisiblePercent(mParentView);
        if (currentArea <= 0) {
            return;
        }
        // 刚要滑入和滑出时，异常状态的处理
        if (Math.abs(currentArea - mLastArea) >= 100) {
            return;
        }
        // 播放器在屏幕中可见的比例小于50%
        if (currentArea < SDKConstant.VIDEO_SCREEN_PERCENT) {
            if (mCanPause) {
                pauseVideo(true);
                mCanPause = false;
            }
            mLastArea = 0;
            // 滑动出50%后标记为从头开始播
            mVideoView.setIsComplete(false);
            mVideoView.setIsRealPause(false);
            return;
        }
        // 进入手动暂停或者播放结束，播放结束和不满足自动播放条件都作为手动暂停
        if (isRealPause() || isComplete()) {
            pauseVideo(true);
            mCanPause = false;
        }
        // 满足自动播放条件或者用户主动点击播放，开始播放
        if (Utils.canAutoPlay(mContext, VideoParameters.getCurrentSetting()) || isPlaying()) {
            mLastArea = currentArea;
            resumeVideo();
            mCanPause = true;
            mVideoView.setIsRealPause(false);
        } else {
            pauseVideo(true);
            // 不能自动播放则设置为手动暂停效果
            mVideoView.setIsRealPause(true);
        }
    }

    /**
     * 发送视频开始播放监测
     */
    private void sendSUSReport(boolean isAuto) {
        try {
            ReportManager.susReport(mVideoValue.startMonitor, isAuto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isPlaying() {
        if (mVideoView != null) {
            return mVideoView.isPlaying();
        }
        return false;
    }

    private boolean isRealPause() {
        if (mVideoView != null) {
            return mVideoView.isRealPause();
        }
        return false;
    }

    private boolean isComplete() {
        if (mVideoView != null) {
            return mVideoView.isComplete();
        }
        return false;
    }

    /**
     * 暂停播放
     */
    private void pauseVideo(boolean isAuto) {
        if (mVideoView != null) {
            if (isAuto) {
                //发自动暂停监测
                if (!isRealPause() && isPlaying()) {
                    try {
                        ReportManager.pauseVideoReport(mVideoValue.event.pause.content,
                                getPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mVideoView.seekAndPause(0);
        }
    }

    /**
     * 恢复播放
     */
    private void resumeVideo() {
        if (mVideoView != null) {
            mVideoView.resume();
            if (isPlaying()) {
                sendSUSReport(true); //发自动播放监测
            }
        }
    }

    private int getPosition() {
        return mVideoView.getCurrentPosition() / SDKConstant.MILLION_UNIT;
    }

    private int getDuration() {
        return mVideoView.getDuration() / SDKConstant.MILLION_UNIT;
    }

    public void destroy() {
        mVideoView.destroy();
        mVideoView = null;
        mContext = null;
        mVideoValue = null;
    }

    @Override
    public void onBufferUpdate(int position) {
        try {
            ReportManager.suReport(mVideoValue.middleMonitor, position / SDKConstant.MILLION_UNIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击全屏按钮
     */
    @Override
    public void onClickFullScreenBtn() {
        try {
            ReportManager.fullScreenReport(mVideoValue.event.full.content, getPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParentView.removeView(mVideoView);
        int position = mVideoView.getCurrentPosition();
        VideoFullDialog dialog = new VideoFullDialog(mContext, mVideoView, mVideoValue, position);
        dialog.setSDKSlotListener(mSDKSlotListener);
        dialog.setFullToSmallListener(new VideoFullDialog.FullToSmallListener() {
            @Override
            public void getCurrentPlayPosition(int position) {
                // 全屏跳转到小屏
                backToSmallMode(position);
            }

            @Override
            public void playComplete() {
                // 全屏播放完成
                bigPlayComplete();
            }
        });
        dialog.show();
    }

    private void bigPlayComplete() {
        if (mVideoView.getParent() == null) {
            mParentView.addView(mVideoView);
        }
        // 显示全屏按钮
        mVideoView.isShowFullBtn(true);
        // 静音
        mVideoView.mute(true);
        // 重新设置回调监听
        mVideoView.setVideoPlayerListener(this);
        // 跳转到视频起始位置并暂停
        mVideoView.seekAndPause(0);
        mCanPause = false;
    }

    /**
     * 全屏跳转到小屏
     *
     * @param position
     *         视频播放的位置
     */
    private void backToSmallMode(int position) {
        if (mVideoView.getParent() == null) {
            mParentView.addView(mVideoView);
        }
        // 显示全屏按钮
        mVideoView.isShowFullBtn(true);
        // 静音
        mVideoView.mute(true);
        // 重新设置回调监听
        mVideoView.setVideoPlayerListener(this);
        // 跳转到指定位置播放
        mVideoView.seekAndResume(position);
    }

    @Override
    public void onClickVideo() {
        String destinationUrl = mVideoValue.clickUrl;
        if (mSDKSlotListener != null) {
            if (mVideoView.isFrameHidden() && !TextUtils.isEmpty(destinationUrl)) {
                mSDKSlotListener.onClickVideo(destinationUrl);
                try {
                    // 上传暂停监控
                    ReportManager.pauseVideoReport(mVideoValue.clickMonitor,
                            mVideoView.getCurrentPosition() / SDKConstant.MILLION_UNIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 走默认样式
            if (mVideoView.isFrameHidden() && !TextUtils.isEmpty(destinationUrl)) {
                Intent intent = new Intent(mContext, AdBrowserActivity.class);
                intent.putExtra(AdBrowserActivity.KEY_URL, mVideoValue.clickUrl);
                mContext.startActivity(intent);
                try {
                    // 上传暂停监控
                    ReportManager.pauseVideoReport(mVideoValue.clickMonitor,
                            mVideoView.getCurrentPosition() / SDKConstant.MILLION_UNIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClickBackBtn() {

    }

    @Override
    public void onClickPlay() {
        sendSUSReport(false);
    }

    @Override
    public void onVideoLoadSuccess() {
        if (mSDKSlotListener != null) {
            mSDKSlotListener.onVideoPlaySuccess();
        }
    }

    @Override
    public void onVideoLoadFailed() {
        if (mSDKSlotListener != null) {
            mSDKSlotListener.onVideoPlayFailed();
        }
        //加载失败全部回到初始状态
        mCanPause = false;
    }

    @Override
    public void onVideoPlayComplete() {
        try {
            ReportManager.sueReport(mVideoValue.endMonitor, false, getDuration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mSDKSlotListener != null) {
            mSDKSlotListener.onVideoPlayComplete();
        }
        mVideoView.setIsRealPause(true);
    }

    /**
     * 传递消息到应用层
     */
    public interface SDKSlotListener {

        /**
         * 获取播放器的父控件
         *
         * @return 播放器的父控件
         */
        ViewGroup getParent();

        /**
         * 播放成功
         */
        void onVideoPlaySuccess();

        /**
         * 播放失败
         */
        void onVideoPlayFailed();

        /**
         * 播放完成
         */
        void onVideoPlayComplete();

        /**
         * 点击播放器界面
         *
         * @param url
         *         播放地址
         */
        void onClickVideo(String url);
    }
}
