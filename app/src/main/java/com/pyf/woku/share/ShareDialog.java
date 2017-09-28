package com.pyf.woku.share;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pyf.woku.R;
import com.pyf.woku.constant.Constant;
import com.pyf.woku.network.http.RequestCenter;
import com.pyf.wokusdk.okhttp.listener.DisposeDownloadListener;
import com.pyf.wokusdk.util.Utils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/28
 */

public class ShareDialog extends Dialog implements View.OnClickListener {

    private LinearLayout mLlShareWeixin;
    private LinearLayout mLlShareFriend;
    private LinearLayout mLlShareQq;
    private LinearLayout mLlShareQqzone;
    private LinearLayout mLlShareDownload;
    private Button mBtnShareCancel;

    private Context mContext;
    private boolean mIsShowDownload;

    private int mShareType; //指定分享类型
    private String mShareTitle; //指定分享内容标题
    private String mShareText; //指定分享内容文本
    private String mShareTileUrl;
    private String mShareSiteUrl;
    private String mShareSite;
    private String mUrl;
    // 分享的网络图片
    private String mResourceUrl;

    public ShareDialog(@NonNull Context context, boolean isShowDwnload) {
        super(context, R.style.SheetDialogStyle);
        mIsShowDownload = isShowDwnload;
        mContext = context;
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        setContentView(R.layout.dialog_share_layout);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenWidth(mContext);
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        mLlShareWeixin = (LinearLayout) findViewById(R.id.ll_share_weixin);
        mLlShareFriend = (LinearLayout) findViewById(R.id.ll_share_friend);
        mLlShareQq = (LinearLayout) findViewById(R.id.ll_share_qq);
        mLlShareQqzone = (LinearLayout) findViewById(R.id.ll_share_qqzone);
        mLlShareDownload = (LinearLayout) findViewById(R.id.ll_share_download);
        mBtnShareCancel = (Button) findViewById(R.id.btn_share_cancel);
        if (mIsShowDownload) {
            mLlShareDownload.setVisibility(View.VISIBLE);
        } else {
            mLlShareDownload.setVisibility(View.GONE);
        }
        mLlShareWeixin.setOnClickListener(this);
        mLlShareFriend.setOnClickListener(this);
        mLlShareQq.setOnClickListener(this);
        mLlShareQqzone.setOnClickListener(this);
        mLlShareDownload.setOnClickListener(this);
        mBtnShareCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_cancel:
                dismiss();
                break;
            case R.id.ll_share_download:
                download();
                dismiss();
                break;
            case R.id.ll_share_friend:
                shareData(ShareManager.PlatformType.WeiChatMoments);
                dismiss();
                break;
            case R.id.ll_share_weixin:
                shareData(ShareManager.PlatformType.WeiChat);
                dismiss();
                break;
            case R.id.ll_share_qq:
                shareData(ShareManager.PlatformType.QQ);
                dismiss();
                break;
            case R.id.ll_share_qqzone:
                shareData(ShareManager.PlatformType.QZONE);
                dismiss();
                break;
        }
    }

    private void download() {
        RequestCenter.downloadFile(mResourceUrl,
                Constant.APP_PHOTO_DIR.concat(String.valueOf(System.currentTimeMillis() + ".jpg")),
                new DisposeDownloadListener() {
                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onSuccess(Object responseObj) {
                        Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setResourceUrl(String resourceUrl) {
        mResourceUrl = resourceUrl;
    }

    public void setShareTitle(String title) {
        mShareTitle = title;
    }

    public void setShareType(int type) {
        mShareType = type;
    }

    public void setShareSite(String site) {
        mShareSite = site;
    }

    public void setShareTitleUrl(String titleUrl) {
        mShareTileUrl = titleUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setShareSiteUrl(String siteUrl) {
        mShareSiteUrl = siteUrl;
    }

    public void setShareText(String text) {
        mShareText = text;
    }

    private void shareData(ShareManager.PlatformType platformType) {
        ShareData mData = new ShareData();
        Platform.ShareParams params = new Platform.ShareParams();
        params.setShareType(mShareType);
        params.setTitle(mShareTitle);
        params.setTitleUrl(mShareTileUrl);
        params.setSite(mShareSite);
        params.setSiteUrl(mShareSiteUrl);
        params.setText(mShareText);
        params.setImageUrl(mResourceUrl);
        params.setUrl(mUrl);
        mData.mPlatformType = platformType;
        mData.mParams = params;
        ShareManager.getInstance().shareData(mData, mListener);
    }

    private PlatformActionListener mListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(mContext, "取消分享", Toast.LENGTH_SHORT).show();
        }
    };
}
