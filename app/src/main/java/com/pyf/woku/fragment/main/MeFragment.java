package com.pyf.woku.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.LoginActivity;
import com.pyf.woku.activity.SettingActivity;
import com.pyf.woku.bean.User;
import com.pyf.woku.constant.Constant;
import com.pyf.woku.fragment.BaseFragment;
import com.pyf.woku.imageloader.ImageLoaderManager;
import com.pyf.woku.manager.UserManager;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/16
 */
public class MeFragment extends BaseFragment {

    private LoginBroadcast mLoginBroadcast;

    @BindView(R2.id.civ_avatar)
    CircleImageView mCivAvatar;
    @BindView(R2.id.rl_login_layout)
    RelativeLayout mRlLoginLayout;
    @BindView(R2.id.civ_user_avatar)
    CircleImageView mCivUserAvatar;
    @BindView(R2.id.tv_login)
    TextView mTvLogin;
    @BindView(R2.id.tv_username)
    TextView mTvUsername;
    @BindView(R2.id.tv_tick)
    TextView mTvTick;
    @BindView(R2.id.rl_loagined_layout)
    RelativeLayout mRlLoaginedLayout;

    @OnClick(R2.id.tv_video_setting)
    void onVideoSettingClick() {
        Intent intent = new Intent(mContext, SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.tv_share)
    void onShareClick() {

    }

    @OnClick(R2.id.tv_my_qtcode)
    void onMyQtcodeClick() {

    }

    @OnClick({R2.id.tv_login, R2.id.rl_login_layout})
    void onLoginClick() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.tv_update)
    void onUpdateClick() {
        // 判断是否有权限
        if (hasPermission(Constant.WRITE_READ_EXTERNAL_PERMISSION)) {
            checkVersion();
        } else {
            // 申请权限
            requestPermissions(Constant.WRITE_READ_EXTERNAL_CODE,
                    Constant.WRITE_READ_EXTERNAL_PERMISSION);
        }
    }

    @Override
    protected Object setLayout() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = UserManager.getInstance().getUser();
        if (user != null) {
            mRlLoginLayout.setVisibility(View.GONE);
            mRlLoaginedLayout.setVisibility(View.VISIBLE);
            mTvUsername.setText(user.data.name);
            mTvTick.setText(user.data.tick);
            ImageLoaderManager.getInstance(mContext)
                    .displayImage(mCivUserAvatar,user.data.photoUrl);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterLoginBroadcast();
    }

    private void unRegisterLoginBroadcast() {
        if (mLoginBroadcast != null) {
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mLoginBroadcast);
        }
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        registerLoginBroadcast();
    }

    private void registerLoginBroadcast() {
        if (mLoginBroadcast == null) {
            mLoginBroadcast = new LoginBroadcast();
            IntentFilter filter = new IntentFilter(LoginActivity.LOGIN_ACTION);
            LocalBroadcastManager.getInstance(mContext).registerReceiver(mLoginBroadcast, filter);
        }
    }

    class LoginBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            User user = UserManager.getInstance().getUser();
            if (user != null) {
                mRlLoginLayout.setVisibility(View.GONE);
                mRlLoaginedLayout.setVisibility(View.VISIBLE);
                mTvUsername.setText(user.data.name);
                mTvTick.setText(user.data.tick);
                ImageLoaderManager.getInstance(mContext)
                        .displayImage(mCivUserAvatar,user.data.photoUrl);
            }
        }
    }
}
