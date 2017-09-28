package com.pyf.woku.application;

import android.app.Application;
import android.os.Environment;

import com.pyf.woku.share.ShareManager;

/**
 * 应用程序的入口
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/16
 */
public class BaseApplication extends Application {

    // 全局的唯一实例
    private static BaseApplication mInstance;
    // 缓存目录
    private String mLocalCacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initMobSDK();
        initCacheDir();
    }

    private void initMobSDK() {
        ShareManager.initMobSDK(this);
    }

    /**
     * 创建缓存目录
     */
    private void initCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mLocalCacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WoKu";
        } else {
            mLocalCacheDir = Environment.getRootDirectory().getAbsolutePath() + "/WoKu";
        }
    }

    public String getLocalCacheDir() {
        return mLocalCacheDir;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
