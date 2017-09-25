package com.pyf.woku.service.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.pyf.woku.R;
import com.pyf.woku.application.BaseApplication;
import com.pyf.woku.network.http.RequestCenter;
import com.pyf.wokusdk.okhttp.listener.DisposeDownloadListener;

import java.io.File;

/**
 * 更新版本服务
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public class UpdateService extends Service {

    /**
     * 固定的安装包下载地址
     */
    private static final String APK_URL = "http://192.168.1.228:8080/server/mukewang.apk";
    // 安装包下载到本地的地址
    private String mFilePath;
    // 通知管理器
    private NotificationManager mNotificationManager;
    // 通知
    private Notification mNotification;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mFilePath = BaseApplication.getInstance().getLocalCacheDir() + "/download/我酷.apk";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notifyUser(getString(R.string.update_download_start),
                getString(R.string.update_download_start), 0);
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        RequestCenter.downloadFile(APK_URL, mFilePath, new DisposeDownloadListener() {
            @Override
            public void onProgress(int progrss) {
                notifyUser(getString(R.string.update_download_processing),
                        getString(R.string.update_download_processing), progrss);
            }

            @Override
            public void onSuccess(Object responseObj) {
                notifyUser(getString(R.string.update_download_finish),
                        getString(R.string.update_download_finish), 100);
                // 停掉服务自身
                stopSelf();
                // 下载成功后安装
                startActivity(getInstallApkIntent());
            }

            @Override
            public void onFailure(Object reasonObj) {
                notifyUser(getString(R.string.update_download_failed_msg),
                        getString(R.string.update_download_failed_msg), 0);
                // 删除无用的安装包
                deleteApkFile();
                // 停掉服务自身
                stopSelf();
            }
        });
    }

    private void notifyUser(String tickerMsg, String message, int progress) {
        notifyThatExceedLv21(tickerMsg, message, progress);
    }

    private void notifyThatExceedLv21(String tickerMsg, String message, int progress) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.bg_message_imooc);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.drawable.bg_message_imooc));
        notification.setAutoCancel(true);
        notification.setContentTitle(getString(R.string.app_name));
        if (progress > 0 && progress < 100) {
            notification.setProgress(100, progress, false);
        } else {
            // 隐藏进度条
            notification.setProgress(0, 0, false);
            notification.setContentText(message);
        }
        notification.setWhen(System.currentTimeMillis());
        notification.setTicker(tickerMsg);
        // 进度大于等于100，启动安装程序，否则什么也不做
        notification.setContentIntent(progress >= 100 ? getInstallIntent() :
                PendingIntent.getActivity(this, 0,
                        new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        mNotification = notification.build();
        mNotificationManager.notify(0, mNotification);
    }

    private PendingIntent getInstallIntent() {
        return PendingIntent.getActivity(this, 0,
                getInstallApkIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 获取安装应用的意图
     */
    private Intent getInstallApkIntent() {
        File apkfile = new File(mFilePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 删除无用安装包文件
     */
    private boolean deleteApkFile() {
        File apkFile = new File(mFilePath);
        if (apkFile.exists() && apkFile.isFile()) {
            return apkFile.delete();
        }
        return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
