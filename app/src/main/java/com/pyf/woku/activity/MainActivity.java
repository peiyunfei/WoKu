package com.pyf.woku.activity;

import android.widget.Toast;

import com.pyf.woku.R;
import com.pyf.woku.activity.base.BaseActivity;

/**
 * 主界面
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/16
 */
public class MainActivity extends BaseActivity {

    // 延迟时间
    private static final long WAIT_TIME = 2000;
    // 按下的时间
    private static long TOUCH_TIME = 0;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    /**
     * 再按一次退出
     */
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - TOUCH_TIME) < WAIT_TIME) {
            finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出我酷", Toast.LENGTH_SHORT).show();
        }
    }
}
