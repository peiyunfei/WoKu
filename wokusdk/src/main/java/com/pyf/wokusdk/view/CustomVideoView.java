package com.pyf.wokusdk.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pyf.wokusdk.R;
import com.pyf.wokusdk.constant.SDKConstant;
import com.pyf.wokusdk.util.Utils;
import com.pyf.wokusdk.util.VideoParameters;

import java.io.IOException;

/**
 * 自定义视频播放器
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/18
 */
public class CustomVideoView extends RelativeLayout implements View.OnClickListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
        TextureView.SurfaceTextureListener {

    // 更新缓存时间的标记
    private static final int TIME_MSG = 0x01;
    // 更新缓存时间的间隔
    private static final int TIME_UPDATE_BUFFER = 1000;
    // 视频播放的生命周期状态--错误状态
    private static final int STATE_ERROR = -1;
    // 视频播放的生命周期状态--空闲状态
    private static final int STATE_IDLE = 0;
    // 视频播放的生命周期状态--正在播放状态
    private static final int STATE_PLAYING = 1;
    // 视频播放的生命周期状态--暂停状态
    private static final int STATE_PAUSE = 2;
    // 如果连续三次都加载视频失败了，则认为加载失败
    private static final int LOAD_TOTAL_COUNT = 3;

    /**
     * UI
     */
    // 视频播放器的父容器
    private ViewGroup mParentContainer;
    private RelativeLayout mPlayerView;
    private TextureView mVideoView;
    // 退出播放
    private Button mBtnSdkClose;
    // 全屏播放
    private ImageView mIvSdkFull;
    // 加载播放
    private ImageView mIvSdkLoading;
    // 播放失败
    private ImageView mIvSdkError;
    // 播放按钮
    private Button mBtnSdkPlay;
    // 音量控制器
    private AudioManager mAudioManager;
    // 显示帧数据的类
    private Surface mVideoSurface;

    // 视频播放地址
    private String mUrl;
    // 是否静音
    private boolean mIsMute;
    // 视频播放器的宽度
    private int mVideoViewWidth;
    // 视频播放器的高度
    private int mVideoViewHeight;

    // 是否能播放
    private boolean mCanPlay = true;
    // 是否真的停止播放了
    private boolean mIsRealPause;
    // 是否播放完成
    private boolean mIsComplete;
    // 当前加载视频的次数
    private int mCurrentCount;
    // 当前播放状态，默认是空闲状态
    private int mPlayerState = STATE_IDLE;

    // 视频播放的核心类
    private MediaPlayer mMediaPlayer;
    // 关屏广播
    private ScreenEventReceiver mScreenReceiver;
    private VideoPlayerListener mVideoPlayerListener;

    public void setVideoPlayerListener(VideoPlayerListener videoPlayerListener) {
        mVideoPlayerListener = videoPlayerListener;
    }

    /**
     * 每隔一秒去更新缓存
     */
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_MSG:
                    if (isPlaying()) {
                        if (mVideoPlayerListener != null) {
                            // 更新当前缓存的位置
                            mVideoPlayerListener.onBufferUpdate(getCurrentPosition());
                        }
                    }
                    sendEmptyMessageDelayed(TIME_MSG, TIME_UPDATE_BUFFER);
                    break;
            }
        }
    };

    public CustomVideoView(Context context, ViewGroup parentContainer) {
        this(context, null, parentContainer);
    }

    public CustomVideoView(Context context, AttributeSet attrs, ViewGroup parentContainer) {
        super(context, attrs);
        mParentContainer = parentContainer;
        initView();
        // 注册关屏广播
        registerBroadcastReceiver();
    }

    /**
     * 注册关屏广播
     */
    private void registerBroadcastReceiver() {
        if (mScreenReceiver == null) {
            mScreenReceiver = new ScreenEventReceiver();
            IntentFilter filter = new IntentFilter();
            // 监听关屏动作
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            // 监听用户解锁动作
            filter.addAction(Intent.ACTION_USER_PRESENT);
            getContext().registerReceiver(mScreenReceiver, filter);
        }
    }

    /**
     * 注销关屏广播和用户解锁广播
     */
    private void unRegisterBroadcastReceiver() {
        if (mScreenReceiver != null) {
            getContext().unregisterReceiver(mScreenReceiver);
        }
    }

    private void initView() {
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mVideoViewWidth = Utils.getScreenWidth(getContext());
        mVideoViewHeight = (int) (mVideoViewWidth * (SDKConstant.VIDEO_HEIGHT_PERCENT));
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        mPlayerView = (RelativeLayout) inflater.inflate(R.layout.sdk_video_player, this);
        mVideoView = (TextureView) mPlayerView.findViewById(R.id.sdk_player_video_textureView);
        LayoutParams params = new LayoutParams(mVideoViewWidth, mVideoViewHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mPlayerView.setLayoutParams(params);
        //        mBtnSdkClose = (Button) mPlayerView.findViewById(R.id.btn_sdk_close);
        mBtnSdkPlay = (Button) mPlayerView.findViewById(R.id.btn_sdk_play);
        mIvSdkFull = (ImageView) mPlayerView.findViewById(R.id.iv_sdk_full);
        mIvSdkLoading = (ImageView) mPlayerView.findViewById(R.id.iv_sdk_loading);
        mIvSdkError = (ImageView) mPlayerView.findViewById(R.id.iv_sdk_error);
        mBtnSdkPlay.setOnClickListener(this);
        //        mBtnSdkClose.setOnClickListener(this);
        mIvSdkFull.setOnClickListener(this);
        mVideoView.setOnClickListener(this);
        mVideoView.setKeepScreenOn(true);
        mVideoView.setSurfaceTextureListener(this);
        // 初始化小屏播放模式
        //        initSmallLayoutMode();
    }

    /**
     * 初始化小屏播放模式
     */
    private void initSmallLayoutMode() {

    }

    /**
     * 加载视频播放地址
     */
    public void load() {
        if (mPlayerState != STATE_IDLE) {
            return;
        }
        try {
            // 显示加载视图
            showLoadingView();
            // 设置空闲状态
            setCurrentPlayState(STATE_IDLE);
            // 检测播放器是否为空
            checkMediaPlayer();
            // 静音
            //            mute(true);
            // 设置播放源
            mMediaPlayer.setDataSource(mUrl);
            // 异步加载
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            stop();
        }
    }

    /**
     * 设置声音
     */
    private void mute(boolean mute) {
        mIsMute = mute;
        if (mMediaPlayer != null && mAudioManager != null) {
            float volume = mIsMute ? 0.0f : 1.0f;
            // 设置声音
            mMediaPlayer.setVolume(volume, volume);
        }
    }

    /**
     * 检测播放器是否为空
     */
    private synchronized void checkMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = createMediaPlayer();
        }
    }

    /**
     * 创建播放器
     */
    private MediaPlayer createMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.reset();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnInfoListener(this);
        // 设置音频流
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (mVideoSurface != null && mVideoView.isAvailable()) {
            mMediaPlayer.setSurface(mVideoSurface);
        } else {
            stop();
        }
        return mMediaPlayer;
    }

    /**
     * 设置播放状态
     *
     * @param state
     *         播放状态
     */
    private void setCurrentPlayState(int state) {
        mPlayerState = state;
    }

    /**
     * 显示正在加载布局
     */
    private void showLoadingView() {
        //        mBtnSdkClose.setVisibility(GONE);
        mBtnSdkPlay.setVisibility(GONE);
        mIvSdkFull.setVisibility(GONE);
        mIvSdkError.setVisibility(GONE);
        mIvSdkLoading.setVisibility(VISIBLE);
        AnimationDrawable anim = (AnimationDrawable) mIvSdkLoading.getBackground();
        anim.start();
    }

    /**
     * 异步加载定帧图
     */
    private void loadFrameImage() {
        // TODO: 2017/9/20

    }

    public void setDataSource(String url) {
        mUrl = url;
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (mPlayerState != STATE_PLAYING) {
            return;
        }
        setCurrentPlayState(STATE_PAUSE);
        if (isPlaying()) {
            mMediaPlayer.pause();
        }
        showPauseOrPlayView(false);
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 恢复播放
     */
    public void resume() {
        if (mPlayerState != STATE_PAUSE) {
            return;
        }
        if (!isPlaying()) {
            // 设置播放状态
            entryResumeState();
            // 显示播放时的界面
            showPauseOrPlayView(true);
            // 开始播放
            mMediaPlayer.start();
            // 发送更新缓存的消息
            mHandler.sendEmptyMessage(TIME_MSG);
        }
    }

    /**
     * 进入播放状态时的状态更新
     */
    private void entryResumeState() {
        // 可以播放
        mCanPlay = true;
        // 播放状态
        setCurrentPlayState(STATE_PLAYING);
        // 设置非暂停状态
        setIsRealPause(false);
        // 设置播放未完成状态
        setIsComplete(false);
    }

    /**
     * 设置播放完成与否的状态
     *
     * @param isComplete
     *         true 播放完成状态，false 播放未完成状态
     */
    public void setIsComplete(boolean isComplete) {
        mIsComplete = isComplete;
    }

    /**
     * 如果视频播放完成，就是真正的暂停状态
     *
     * @param isRealPause
     *         true 真正的暂停状态，false 非真正的暂停状态
     */
    public void setIsRealPause(boolean isRealPause) {
        this.mIsRealPause = isRealPause;
    }

    /**
     * 播放完成后回到初始状态
     */
    public void playBack() {
        // 暂停状态
        setCurrentPlayState(STATE_PAUSE);
        mHandler.removeCallbacksAndMessages(null);
        if (mMediaPlayer != null) {
            // 跳转到视频的初始位置
            mMediaPlayer.seekTo(0);
            mMediaPlayer.pause();
            mMediaPlayer.setOnSeekCompleteListener(null);
        }
        showPauseOrPlayView(false);
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.setOnSeekCompleteListener(null);
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mHandler.removeCallbacksAndMessages(null);
        setCurrentPlayState(STATE_IDLE);
        if (mCurrentCount < LOAD_TOTAL_COUNT) {
            mCurrentCount++;
            // 重新加载
            load();
        } else {
            // 显示暂停状态
            showPauseOrPlayView(false);
        }
    }

    /**
     * 显示暂停或者播放时的界面
     *
     * @param show
     *         false 暂停状态，true 播放状态
     */
    private void showPauseOrPlayView(boolean show) {
        mIvSdkFull.setVisibility(show ? View.VISIBLE : View.GONE);
        mBtnSdkPlay.setVisibility(show ? View.GONE : View.VISIBLE);
        mIvSdkLoading.clearAnimation();
        mIvSdkLoading.setVisibility(View.GONE);
        if (!show) {
            mIvSdkError.setVisibility(View.VISIBLE);
            loadFrameImage();
        } else {
            mIvSdkError.setVisibility(View.GONE);
        }
    }

    /**
     * 销毁
     */

    public void destroy() {
        unRegisterBroadcastReceiver();
    }

    /**
     * 跳转到指定点播放
     *
     * @param position
     *         指定点
     */
    public void seekAndResume(int position) {

    }

    /**
     * 跳转到指定点暂停播放
     *
     * @param position
     *         指定点
     */
    public void seekAndPause(int position) {

    }

    /**
     * 是否正在播放
     *
     * @return true 正在播放，false 没有播放
     */
    public boolean isPlaying() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            return true;
        }
        return false;
    }

    /**
     * 获取视频的当前位置
     *
     * @return 视频的当前位置
     */
    public int getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 获取视频总时长
     *
     * @return 视频总时长
     */
    public int getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * 判断是否能播放
     */
    private void decideCanPlay() {
        // 网络状态与用户的设置一致，并且来回切换页面时，控件的可见比例>=50，才自动播放
        if (Utils.canAutoPlay(getContext(), VideoParameters.getCurrentSetting()) &&
                Utils.getVisiblePercent(mParentContainer) >= SDKConstant.VIDEO_SCREEN_PERCENT) {
            setCurrentPlayState(STATE_PAUSE);
            resume();
        } else {
            setCurrentPlayState(STATE_PLAYING);
            pause();
        }
    }

    public boolean isRealPause() {
        return mIsRealPause;
    }

    public boolean isComplete() {
        return mIsComplete;
    }

    /**
     * 在控件可见或者不可见时回调
     */
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE && mPlayerState == STATE_PAUSE) {
            if (isRealPause() || isComplete()) {
                pause();
            } else {
                decideCanPlay();
            }
        } else {
            pause();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * 监听锁屏和用户解锁事件的广播接收器
     */
    private class ScreenEventReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                // 用户解锁
                case Intent.ACTION_USER_PRESENT:
                    if (mPlayerState == STATE_PAUSE) {
                        // 播放完成，真正的暂停状态
                        if (mIsRealPause) {
                            // 播放完成，依然暂停
                            pause();
                        } else {
                            decideCanPlay();
                        }
                    }
                    break;
                // 锁屏
                case Intent.ACTION_SCREEN_OFF:
                    if (mPlayerState != STATE_PAUSE) {
                        pause();
                    }
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * 更新缓存
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    /**
     * 播放完成
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mVideoPlayerListener != null) {
            // 播放完成
            mVideoPlayerListener.onVideoPlayComplete();
        }
        // 播放完成状态
        setIsComplete(true);
        // 视频播放完成，进入真正的暂停状态
        setIsRealPause(true);
        // 播放完成回到初始状态
        playBack();
    }

    /**
     * 播放失败
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        setCurrentPlayState(STATE_ERROR);
        if (mCurrentCount >= LOAD_TOTAL_COUNT) {
            if (mVideoPlayerListener != null) {
                mVideoPlayerListener.onVideoPlayFailed();
            }
            showPauseOrPlayView(false);
        }
        stop();
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return true;
    }

    /**
     * 准备播放
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer = mp;
        if (mMediaPlayer != null) {
            // 加载成功，更新缓存
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mCurrentCount = 0;
            if (mVideoPlayerListener != null) {
                // 加载视频成功
                mVideoPlayerListener.onVideoPlaySuccess();
            }
            decideCanPlay();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mVideoSurface = new Surface(surface);
        load();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    /**
     * 供应用层来实现具体点击逻辑，具体逻辑还会变，如果对UI的点击没有具体监测的话可以不回调
     */
    public interface VideoPlayerListener {

        /**
         * 更新当前缓存的位置
         *
         * @param position
         *         当前缓存的位置
         */
        void onBufferUpdate(int position);

        /**
         * 点击了全屏按钮
         */
        void onClickFullScreenBtn();

        void onClickVideo();

        /**
         * 点击了后退按钮
         */
        void onClickBackBtn();

        /**
         * 点击了播放按钮
         */
        void onClickPlay();

        /**
         * 视频播放成功
         */
        void onVideoPlaySuccess();

        /**
         * 视频播放失败
         */
        void onVideoPlayFailed();

        /**
         * 视频播放完成
         */
        void onVideoPlayComplete();
    }
}
