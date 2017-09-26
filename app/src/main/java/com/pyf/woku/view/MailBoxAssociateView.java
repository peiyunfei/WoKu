package com.pyf.woku.view;

import android.content.Context;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.util.AttributeSet;

/**
 * 邮箱联想控件，输入@符后开始联想
 */
public class MailBoxAssociateView extends AppCompatMultiAutoCompleteTextView {
    public MailBoxAssociateView(Context context) {
        super(context);
    }

    public MailBoxAssociateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MailBoxAssociateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean enoughToFilter() {
        return getText().toString().contains("@") && getText().toString().indexOf("@") > 0;
    }
}