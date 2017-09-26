package com.pyf.woku.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.EditText;
import android.widget.Toast;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.base.BaseActivity;
import com.pyf.woku.bean.User;
import com.pyf.woku.manager.UserManager;
import com.pyf.woku.network.http.RequestCenter;
import com.pyf.woku.view.MailBoxAssociateView;
import com.pyf.wokusdk.okhttp.listener.DisposeDataListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录界面
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public class LoginActivity extends BaseActivity {

    public static final String LOGIN_ACTION = "com.pyf.woku.activity.LoginActivity";
    @BindView(R2.id.et_email)
    MailBoxAssociateView mEtEmail;
    @BindView(R2.id.et_password)
    EditText mEtPassword;

    @OnClick(R2.id.tv_login)
    void onLoginClick() {
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        // 省去对邮箱和密码的判断
        RequestCenter.login(email, password, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                User user = (User) responseObj;
                UserManager.getInstance().setUser(user);
                sendLocalBroadcast();
                // 将用户信息保存到数据库，样可以保证用户打开应用后总是登陆状态
                // 只有用户手动退出登陆时候，将用户数据从数据库中删除。
                finish();
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendLocalBroadcast() {
        Intent intent = new Intent(LOGIN_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected int initView() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }
}
