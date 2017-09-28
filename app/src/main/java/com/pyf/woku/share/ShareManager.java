package com.pyf.woku.share;

import android.content.Context;

import com.mob.MobSDK;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 统一管理分享的入口
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/28
 */
public class ShareManager {

    private Platform mPlatform;

    private ShareManager() {

    }

    public static ShareManager getInstance() {
        return ShareManagerHolder.INSTANCE;
    }

    private static final class ShareManagerHolder {
        private static final ShareManager INSTANCE = new ShareManager();
    }

    public static void initMobSDK(Context context) {
        MobSDK.init(context);
    }

    public void shareData(ShareData shareData, PlatformActionListener listener) {
        switch (shareData.mPlatformType) {
            case QQ:
                mPlatform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case QZONE:
                mPlatform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case WeiChat:
                mPlatform = ShareSDK.getPlatform(Wechat.NAME);
                break;
            case WeiChatMoments:
                mPlatform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
        }
        // 分享
        mPlatform.share(shareData.mParams);
        // 监听分享
        mPlatform.setPlatformActionListener(listener);
    }

    /**
     * 要分享到的平台
     */
    public enum PlatformType {
        QQ, QZONE, WeiChat, WeiChatMoments
    }
}
