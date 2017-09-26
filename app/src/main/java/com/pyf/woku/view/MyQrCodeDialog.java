package com.pyf.woku.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.bean.User;
import com.pyf.woku.imageloader.ImageLoaderManager;
import com.pyf.woku.manager.UserManager;
import com.pyf.woku.util.Util;
import com.pyf.wokusdk.util.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户二维码对话框
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/26
 */
public class MyQrCodeDialog extends Dialog {

    private Context mContext;
    private CircleImageView mCivQrcodeAvatar;
    private TextView mTvQrcodeName;
    private ImageView mIvQrcode;

    public MyQrCodeDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_qrcode_layout);
        mCivQrcodeAvatar = (CircleImageView) findViewById(R.id.civ_qrcode_avatar);
        mTvQrcodeName = (TextView) findViewById(R.id.tv_qrcode_name);
        mIvQrcode = (ImageView) findViewById(R.id.iv_qrcode);
        initData();
    }

    private void initData() {
        User user = UserManager.getInstance().getUser();
        if (user != null) {
            ImageLoaderManager.getInstance(mContext)
                    .displayImage(mCivQrcodeAvatar, user.data.photoUrl);
            mTvQrcodeName.setText(user.data.name.concat(mContext.getString(R.string.personal_info)));
            mIvQrcode.setImageBitmap(Util.createQRCode(
                    Utils.dip2px(mContext, 200),
                    Utils.dip2px(mContext, 200),
                    user.data.name));
        }
    }
}
