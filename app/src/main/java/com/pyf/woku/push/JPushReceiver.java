package com.pyf.woku.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pyf.woku.activity.JPushActivity;
import com.pyf.woku.activity.LoginActivity;
import com.pyf.woku.activity.MainActivity;
import com.pyf.woku.manager.UserManager;
import com.pyf.woku.util.Util;
import com.pyf.wokusdk.util.ResponseEntityToModule;

import cn.jpush.android.api.JPushInterface;

/**
 * 处理极光推送的消息
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/28
 */
public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "onReceive");
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action)) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d("TAG", "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
            PushMessage pushMessage = (PushMessage) ResponseEntityToModule.parseJsonToModule(
                    bundle.getString(JPushInterface.EXTRA_EXTRA), PushMessage.class);
            if (Util.getCurrentTask(context)) {
                // 应用已经启动
                Intent pushIntent = new Intent();
                pushIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pushIntent.putExtra("pushMessage", pushMessage);
                if (pushMessage != null && pushMessage.messageType.equals("2") &&
                        !UserManager.getInstance().hasLogin()) {
                    // 需要登录
                    pushIntent.setClass(context, LoginActivity.class);
                    pushIntent.putExtra("from_push", true);
                } else {
                    // 不需要登录
                    pushIntent.setClass(context, JPushActivity.class);
                }
                context.startActivity(pushIntent);
            } else {
                // 应用没有启动
                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (pushMessage != null && pushMessage.messageType.equals("2") &&
                        !UserManager.getInstance().hasLogin()) {
                    // 需要登录
                    Intent loginIntent = new Intent(context, LoginActivity.class);
                    loginIntent.putExtra("from_push", true);
                    loginIntent.putExtra("pushMessage", pushMessage);
                    context.startActivities(new Intent[]{mainIntent, loginIntent});
                } else {
                    // 不需要登录
                    Intent pushIntent = new Intent(context, JPushActivity.class);
                    pushIntent.putExtra("pushMessage", pushMessage);
                    context.startActivities(new Intent[]{mainIntent, pushIntent});
                }
            }
        }
    }
}
