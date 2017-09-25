package com.pyf.woku.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pyf.woku.application.BaseApplication;

/**
 * 封装SharedPreference
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/24
 */
public class SharedPreferenceManager {

    private static final String SHARED_PREFERENCE_NAME = "woku.pre";
    public static final String VIDEO_PLAY_SETTING = "video_play_setting";

    private final SharedPreferences SP;
    private final Editor EDITOR;

    private SharedPreferenceManager() {
        SP = BaseApplication.getInstance().getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        EDITOR = SP.edit();
    }

    public static SharedPreferenceManager getInstance() {
        return SharedPreferenceManagerHolder.INSTANCE;
    }

    private static class SharedPreferenceManagerHolder {
        private static final SharedPreferenceManager INSTANCE = new SharedPreferenceManager();
    }

    public void putInt(String key, int value) {
        EDITOR.putInt(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return SP.getInt(key, defValue);
    }
}
