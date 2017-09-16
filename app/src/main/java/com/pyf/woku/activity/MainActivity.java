package com.pyf.woku.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.base.BaseActivity;
import com.pyf.woku.fragment.main.HomeFragment;
import com.pyf.woku.fragment.main.MeFragment;
import com.pyf.woku.fragment.main.MessageFragment;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R2.id.tv_image_home)
    TextView mTvImageHome;
    @BindView(R2.id.tv_home)
    TextView mTvHome;
    @BindView(R2.id.ll_home)
    LinearLayout mLlHome;
    @BindView(R2.id.tv_image_message)
    TextView mTvImageMessage;
    @BindView(R2.id.tv_message)
    TextView mTvMessage;
    @BindView(R2.id.ll_message)
    LinearLayout mLlMessage;
    @BindView(R2.id.tv_image_me)
    TextView mTvImageMe;
    @BindView(R2.id.tv_me)
    TextView mTvMe;
    @BindView(R2.id.ll_me)
    LinearLayout mLlMe;
    private FragmentManager mFm;
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private MeFragment mMeFragment;

    @OnClick({R2.id.ll_me, R2.id.ll_message, R2.id.ll_home})
    void onClick(View v) {
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        switch (v.getId()) {
            case R.id.ll_home:
                mTvImageHome.setBackgroundResource(R.drawable.comui_tab_home_selected);
                mTvImageMessage.setBackgroundResource(R.drawable.comui_tab_message);
                mTvImageMe.setBackgroundResource(R.drawable.comui_tab_person);
                hideFragment(fragmentTransaction, mMeFragment);
                hideFragment(fragmentTransaction, mHomeFragment);
                hideFragment(fragmentTransaction, mMessageFragment);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.fl_container, mHomeFragment);
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.ll_me:
                mTvImageHome.setBackgroundResource(R.drawable.comui_tab_home);
                mTvImageMessage.setBackgroundResource(R.drawable.comui_tab_message);
                mTvImageMe.setBackgroundResource(R.drawable.comui_tab_person_selected);
                hideFragment(fragmentTransaction, mMeFragment);
                hideFragment(fragmentTransaction, mHomeFragment);
                hideFragment(fragmentTransaction, mMessageFragment);
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                    fragmentTransaction.add(R.id.fl_container, mMeFragment);
                } else {
                    fragmentTransaction.show(mMeFragment);
                }
                break;
            case R.id.ll_message:
                mTvImageHome.setBackgroundResource(R.drawable.comui_tab_home);
                mTvImageMessage.setBackgroundResource(R.drawable.comui_tab_message_selected);
                mTvImageMe.setBackgroundResource(R.drawable.comui_tab_person);
                hideFragment(fragmentTransaction, mMeFragment);
                hideFragment(fragmentTransaction, mHomeFragment);
                hideFragment(fragmentTransaction, mMessageFragment);
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.fl_container, mMessageFragment);
                } else {
                    fragmentTransaction.show(mMessageFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏fragment
     */
    private void hideFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mHomeFragment = new HomeFragment();
        mFm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, mHomeFragment);
        fragmentTransaction.commit();
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
