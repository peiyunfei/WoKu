package com.pyf.wokusdk.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pyf.wokusdk.R;
import com.pyf.wokusdk.activity.AdBrowserActivity;
import com.pyf.wokusdk.constant.SDKConstant;
import com.pyf.wokusdk.core.video.VideoSlot;
import com.pyf.wokusdk.module.VideoValue;
import com.pyf.wokusdk.report.ReportManager;

/**
 * 创建全屏的对话框来实现全屏播放的功能
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/24
 */
public class VideoFullDialog extends Dialog implements CustomVideoView.VideoPlayerListener,
        View.OnClickListener {

    private Context mContext;

    private CustomVideoView mVideoView;
    private RelativeLayout mRlPlayerView;
    private RelativeLayout mContentLayout;
    private Button mBtnSdkClose;
    private ImageView mIvSdkError;
    private ImageView mIvSdkLoading;
    private Button mBtnSdkPlay;

    private VideoValue mVideoValue;
    private int mPosition;
    private FullToSmallListener mFullToSmallListener;
    private boolean isFirst = true;

    //动画要执行的平移值
    private int deltaY;
    private VideoSlot.SDKSlotListener mSDKSlotListener;
    private Bundle mStartBundle;
    // 用于对话框出入场动画
    private Bundle mEndBundle;

    public VideoFullDialog(@NonNull Context context, CustomVideoView videoView,
                           VideoValue videoValue, int position) {
        super(context, R.style.dialog_full_screen);
        mContext = context;
        mVideoView = videoView;
        mVideoValue = videoValue;
        mPosition = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sdk_video_player_dialog);
        initView();
    }

    private void initView() {
        mRlPlayerView = (RelativeLayout) findViewById(R.id.rl_player_view);
        mContentLayout = (RelativeLayout) findViewById(R.id.content_layout);
        mBtnSdkClose = (Button) findViewById(R.id.btn_sdk_close);
        mIvSdkError = (ImageView) findViewById(R.id.iv_sdk_error);
        mIvSdkLoading = (ImageView) findViewById(R.id.iv_sdk_loading);
        mBtnSdkPlay = (Button) findViewById(R.id.btn_sdk_play);
        mVideoView.mute(false);
        mContentLayout.addView(mVideoView);
        mVideoView.setVideoPlayerListener(this);
        mBtnSdkClose.setOnClickListener(this);
        mRlPlayerView.setOnClickListener(this);
        mContentLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mContentLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                //                prepareScene();
                //                runEnterAnimation();
                return true;
            }
        });
    }

    public void setSDKSlotListener(VideoSlot.SDKSlotListener SDKSlotListener) {
        mSDKSlotListener = SDKSlotListener;
    }

    public void setFullToSmallListener(FullToSmallListener fullToSmallListener) {
        mFullToSmallListener = fullToSmallListener;
    }

    @Override
    public void dismiss() {
        mContentLayout.removeView(mVideoView);
        super.dismiss();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 防止第一次，有些手机仍显示全屏按钮
        mVideoView.isShowFullBtn(false);
        if (!hasFocus) {
            mPosition = mVideoView.getCurrentPosition();
            mVideoView.pauseForFullScreen();
        } else {
            if (isFirst) {
                // 第一次创建对话框并且首次获取焦点
                // 为了适配某些手机不执行seekAndResume中的播放方法
                mVideoView.seekAndResume(mPosition);
            } else {
                mVideoView.resume();
            }
        }
        isFirst = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clickBackBtn();
    }

    @Override
    public void onBufferUpdate(int position) {
        try {
            if (mVideoValue != null) {
                ReportManager.suReport(mVideoValue.middleMonitor, position / SDKConstant.MILLION_UNIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickFullScreenBtn() {
        onClickVideo();
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

    }

    @Override
    public void onVideoLoadSuccess() {
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    @Override
    public void onVideoLoadFailed() {
    }

    @Override
    public void onVideoPlayComplete() {
        try {
            int position = mVideoView.getDuration() / SDKConstant.MILLION_UNIT;
            ReportManager.sueReport(mVideoValue.endMonitor, true, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismiss();
        if (mFullToSmallListener != null) {
            // 全屏播放完成
            mFullToSmallListener.playComplete();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSdkClose) {
            clickBackBtn();
        } else if (v == mRlPlayerView) {
            onClickVideo();
        }
    }

    /**
     * 点击回退或者返回按钮
     */
    private void clickBackBtn() {
        dismiss();
        if (mFullToSmallListener != null) {
            mFullToSmallListener.getCurrentPlayPosition(mVideoView.getCurrentPosition());
        }
    }

    /**
     * 全屏到小屏的监听
     */
    public interface FullToSmallListener {

        /**
         * 获取当前保底进度
         *
         * @param position
         *         当前保底进度
         */
        void getCurrentPlayPosition(int position);

        /**
         * 全屏播放完成
         */
        void playComplete();
    }
}
