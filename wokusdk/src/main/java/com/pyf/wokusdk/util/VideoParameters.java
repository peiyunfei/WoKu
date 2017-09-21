package com.pyf.wokusdk.util;

import com.pyf.wokusdk.constant.SDKConstant;

/**
 * 视频SDK全局参数配置
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/20
 */
public class VideoParameters {

    //用来记录可自动播放的条件，默认都可以自动播放
    private static SDKConstant.AutoPlaySetting currentSetting = SDKConstant.AutoPlaySetting.AUTO_PLAY_3G_4G_WIFI;

    public static void setCurrentSetting(SDKConstant.AutoPlaySetting setting) {
        currentSetting = setting;
    }

    public static SDKConstant.AutoPlaySetting getCurrentSetting() {
        return currentSetting;
    }

    /**
     * 获取sdk当前版本号
     */
    public static String getAdSDKVersion() {
        return "1.0.0";
    }
}
