package com.pyf.woku.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.base.BaseActivity;
import com.pyf.woku.push.PushMessage;
import com.pyf.wokusdk.activity.AdBrowserActivity;

import butterknife.BindView;

/**
 * 显示极光推送消息的界面
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/28
 */
public class JPushActivity extends BaseActivity {
    @BindView(R2.id.tv_message_type)
    TextView mIvMessageType;
    @BindView(R2.id.tv_message_content)
    TextView mTvMessageContent;
    private PushMessage mPushMessage;

    @Override
    protected int initView() {
        return R.layout.activity_push;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mPushMessage = (PushMessage) intent.getSerializableExtra("pushMessage");
            mIvMessageType.setText(mPushMessage.messageType);
            mTvMessageContent.setText(mPushMessage.messageContent);
            if (!TextUtils.isEmpty(mPushMessage.messageUrl)) {
                Intent browserIntent = new Intent(this, AdBrowserActivity.class);
                startActivity(browserIntent);
            }
        }
    }
}
