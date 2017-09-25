package com.pyf.woku.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pyf.woku.R;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public class CommonDialog extends Dialog {

    private ViewGroup contentView;
    private TextView mTitle;
    private TextView mMessage;
    private LinearLayout mAllLayout;
    private TextView mConfirmBtn;
    private TextView mCancleBtn;
    private LinearLayout mSignalLayout;
    private TextView mSignalConfirmBtn;
    private DialogConfirmClickListener mConfirmClickListener;
    private DialogCancelClickListener mCancelClickListener;

    public void setConfirmClickListener(DialogConfirmClickListener confirmClickListener) {
        mConfirmClickListener = confirmClickListener;
    }

    public void setCancelClickListener(DialogCancelClickListener cancelClickListener) {
        mCancelClickListener = cancelClickListener;
    }

    public interface DialogConfirmClickListener {
        void onConfirmClick();
    }

    public interface DialogCancelClickListener {
        void onCancelClick();
    }

    public CommonDialog(Context context, String titleMsg, String contentMsg,
                        String confirmText) {
        this(context, titleMsg, contentMsg, confirmText, "");
    }

    public CommonDialog(Context context, String titleMsg, String contentMsg,
                        String confirmText, String cancelText) {
        super(context, R.style.Base_Theme_AppCompat_Dialog);
        initDialogStyle(titleMsg, contentMsg, confirmText, cancelText);
    }

    private void initDialogStyle(String msg, String contentMsg,
                                 String confirmText, String cancelText) {
        setContentView(createDialogView(R.layout.dialog_common_layout));
        initView();
        setParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!TextUtils.isEmpty(contentMsg)) {
            mMessage.setText(contentMsg);
        }
        if (!TextUtils.isEmpty(msg)) {
            mTitle.setText(msg);
        }
        if (TextUtils.isEmpty(cancelText)) {
            // 只有一个确认按钮
            mAllLayout.setVisibility(View.GONE);
            mSignalLayout.setVisibility(View.VISIBLE);
            mSignalConfirmBtn.setText(confirmText);
            mSignalConfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConfirmClickListener != null) {
                        mConfirmClickListener.onConfirmClick();
                    }
                }
            });
        } else {
            // 有一个确认按钮和一个取消按钮
            mAllLayout.setVisibility(View.VISIBLE);
            mSignalLayout.setVisibility(View.GONE);
            mConfirmBtn.setText(confirmText);
            mCancleBtn.setText(cancelText);
            mConfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConfirmClickListener != null) {
                        mConfirmClickListener.onConfirmClick();
                    }
                }
            });
            mCancleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCancelClickListener != null) {
                        mCancelClickListener.onCancelClick();
                    }
                }
            });
        }
    }

    private void setParams(int width, int height) {
        WindowManager.LayoutParams dialogParams = this.getWindow().getAttributes();
        dialogParams.width = width;
        dialogParams.height = height;
        this.getWindow().setAttributes(dialogParams);
    }


    private void initView() {
        mTitle = (TextView) findViewById(R.id.tv_update_title);
        mMessage = (TextView) findViewById(R.id.message);
        mAllLayout = (LinearLayout) findViewById(R.id.all_layout);
        mConfirmBtn = (TextView) findViewById(R.id.confirm_btn);
        mCancleBtn = (TextView) findViewById(R.id.cancle_btn);
        mSignalLayout = (LinearLayout) findViewById(R.id.signal_layout);
        mSignalConfirmBtn = (TextView) findViewById(R.id.signal_confirm_btn);
    }

    private ViewGroup createDialogView(int layoutId) {
        contentView = (ViewGroup) LayoutInflater.from(getContext()).inflate(layoutId, null);
        return contentView;
    }
}
