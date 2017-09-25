package com.pyf.woku.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.base.BaseActivity;
import com.pyf.woku.db.SharedPreferenceManager;
import com.pyf.wokusdk.constant.SDKConstant;
import com.pyf.wokusdk.core.VideoParameters;

import butterknife.BindView;

/**
 * 设置界面
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/24
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.back_view)
    ImageView mBackView;

    @BindView(R2.id.alway_layout)
    RelativeLayout mAlwayLayout;
    @BindView(R2.id.alway_check_box)
    CheckBox mAlwayCheckBox;

    @BindView(R2.id.wifi_layout)
    RelativeLayout mWifiLayout;
    @BindView(R2.id.wifi_check_box)
    CheckBox mWifiCheckBox;

    @BindView(R.id.close_layout)
    RelativeLayout mCloseLayout;
    @BindView(R2.id.close_check_box)
    CheckBox mCloseCheckBox;

    @Override
    protected int initView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {
        int currentSetting = SharedPreferenceManager.getInstance().
                getInt(SharedPreferenceManager.VIDEO_PLAY_SETTING, 1);
        switch (currentSetting) {
            case 0:
                mAlwayCheckBox.setBackgroundResource(R.drawable.setting_selected);
                mWifiCheckBox.setBackgroundResource(0);
                mCloseCheckBox.setBackgroundResource(0);
                break;
            case 1:
                mAlwayCheckBox.setBackgroundResource(0);
                mWifiCheckBox.setBackgroundResource(R.drawable.setting_selected);
                mCloseCheckBox.setBackgroundResource(0);
                break;
            case 2:
                mAlwayCheckBox.setBackgroundResource(0);
                mWifiCheckBox.setBackgroundResource(0);
                mCloseCheckBox.setBackgroundResource(R.drawable.setting_selected);
                break;
        }
        mAlwayLayout.setOnClickListener(this);
        mWifiLayout.setOnClickListener(this);
        mCloseLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alway_layout:
                SharedPreferenceManager.getInstance()
                        .putInt(SharedPreferenceManager.VIDEO_PLAY_SETTING, 0);
                VideoParameters.setCurrentSetting(SDKConstant.AutoPlaySetting.AUTO_PLAY_3G_4G_WIFI);
                mAlwayCheckBox.setBackgroundResource(R.drawable.setting_selected);
                mWifiCheckBox.setBackgroundResource(0);
                mCloseCheckBox.setBackgroundResource(0);
                break;
            case R.id.wifi_layout:
                SharedPreferenceManager.getInstance()
                        .putInt(SharedPreferenceManager.VIDEO_PLAY_SETTING, 1);
                VideoParameters.setCurrentSetting(SDKConstant.AutoPlaySetting.AUTO_PLAY_ONLY_WIFI);
                mAlwayCheckBox.setBackgroundResource(0);
                mWifiCheckBox.setBackgroundResource(R.drawable.setting_selected);
                mCloseCheckBox.setBackgroundResource(0);
                break;
            case R.id.close_layout:
                SharedPreferenceManager.getInstance()
                        .putInt(SharedPreferenceManager.VIDEO_PLAY_SETTING, 2);
                VideoParameters.setCurrentSetting(SDKConstant.AutoPlaySetting.AUTO_PLAY_NEVER);
                mAlwayCheckBox.setBackgroundResource(0);
                mWifiCheckBox.setBackgroundResource(0);
                mCloseCheckBox.setBackgroundResource(R.drawable.setting_selected);
                break;
            case R.id.back_view:
                finish();
                break;
        }
    }
}
