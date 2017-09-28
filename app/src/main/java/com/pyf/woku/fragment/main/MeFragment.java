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
import android.widget.Toast;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.LoginActivity;
import com.pyf.woku.activity.SettingActivity;
import com.pyf.woku.bean.Update;
import com.pyf.woku.bean.User;
import com.pyf.woku.constant.Constant;
import com.pyf.woku.fragment.BaseFragment;
import com.pyf.woku.imageloader.ImageLoaderManager;
import com.pyf.woku.manager.UserManager;
import com.pyf.woku.network.http.RequestCenter;
import com.pyf.woku.service.update.UpdateService;
import com.pyf.woku.share.ShareDialog;
import com.pyf.woku.util.Util;
import com.pyf.woku.view.CommonDialog;
import com.pyf.woku.view.MyQrCodeDialog;
import com.pyf.wokusdk.okhttp.listener.DisposeDataListener;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
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
    RelativeLayout mRlLoginedLayout;

    @OnClick(R2.id.tv_video_setting)
    void onVideoSettingClick() {
        Intent intent = new Intent(mContext, SettingActivity.class);
        startActivity(intent);
    }

    private void toLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.tv_share)
    void onShareClick() {
        ShareDialog dialog =new ShareDialog(mContext,false);
        dialog.show();
        dialog.setResourceUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?" +
                "image&quality=100&size=b4000_4000&sec=1506579965&di" +
                "=f193d0c3370027e4261d07557ec84cfa&src=http://cdn2.image.apk.gfan.com" +
                "/asdf/PImages/2014/5/8/ldpi_705001_2a0b7ee23-e950-41f6-8f40-e90f37bd10de.png");
        dialog.setUrl("www.imooc.com");
        dialog.setShareText("分享慕课网");
        dialog.setShareTitleUrl("www.imooc.com");
        dialog.setShareTitle("慕课网");
        dialog.setShareSite("慕课网");
        dialog.setShareSiteUrl("www.imooc.com");
        dialog.setShareType(Platform.SHARE_IMAGE);
    }

    @OnClick(R2.id.tv_my_qtcode)
    void onMyQtcodeClick() {
        if (!UserManager.getInstance().hasLogin()) {
            toLogin();
            Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            //已登陆根据用户ID生成二维码显示
            MyQrCodeDialog dialog = new MyQrCodeDialog(mContext);
            dialog.show();
        }
    }

    @OnClick({R2.id.tv_login, R2.id.rl_login_layout})
    void onLoginClick() {
        toLogin();
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

    /**
     * 版本更新
     */
    private void checkVersion() {
        RequestCenter.checkVersion(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Update update = (Update) responseObj;
                if (Util.getVersionCode(mContext) < update.data.currentVersion) {
                    final CommonDialog dialog = new CommonDialog(mContext,
                            getString(R.string.update_new_version),
                            getString(R.string.update_title),
                            getString(R.string.update_install),
                            getString(R.string.cancel));
                    dialog.show();
                    dialog.setConfirmClickListener(new CommonDialog.DialogConfirmClickListener() {
                        @Override
                        public void onConfirmClick() {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Intent intent = new Intent(mContext, UpdateService.class);
                            mContext.startService(intent);
                        }
                    });
                    dialog.setCancelClickListener(new CommonDialog.DialogCancelClickListener() {
                        @Override
                        public void onCancelClick() {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
                } else {
                    final CommonDialog dialog = new CommonDialog(mContext,
                            getString(R.string.no_new_version_title),
                            getString(R.string.no_new_version_msg),
                            getString(R.string.confirm));
                    dialog.show();
                    dialog.setConfirmClickListener(new CommonDialog.DialogConfirmClickListener() {
                        @Override
                        public void onConfirmClick() {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void doWriteSdCard() {
        checkVersion();
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
            mRlLoginedLayout.setVisibility(View.VISIBLE);
            mTvUsername.setText(user.data.name);
            mTvTick.setText(user.data.tick);
            ImageLoaderManager.getInstance(mContext)
                    .displayImage(mCivUserAvatar, user.data.photoUrl);
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
                mRlLoginedLayout.setVisibility(View.VISIBLE);
                mTvUsername.setText(user.data.name);
                mTvTick.setText(user.data.tick);
                ImageLoaderManager.getInstance(mContext)
                        .displayImage(mCivUserAvatar, user.data.photoUrl);
            }
        }
    }
}
