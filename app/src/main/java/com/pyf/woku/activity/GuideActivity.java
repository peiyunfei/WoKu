package com.pyf.woku.activity;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.base.BaseActivity;
import com.pyf.wokusdk.view.CustomVideoView;

import butterknife.BindView;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/20
 */
public class GuideActivity extends BaseActivity {

    @BindView(R2.id.rl_guide)
    RelativeLayout mRlGuide;

    @Override
    protected int initView() {
        return R.layout.activity_guide;
    }

    @Override
    public void initData() {
        CustomVideoView videoView = new CustomVideoView(this, mRlGuide);
        videoView.setDataSource("http://fairee.vicp.net:83/2016rm/0116/baishi160116.mp4");
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        mRlGuide.addView(videoView);

    }
}