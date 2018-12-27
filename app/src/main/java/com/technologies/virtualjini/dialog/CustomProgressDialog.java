package com.technologies.virtualjini.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.utils.StringUtils;


public class CustomProgressDialog extends Dialog {

    private Activity mContext;
    private ImageView mLoadingImageView;
    private TextView mMessageTextView;
    private String mMessage;

    public CustomProgressDialog(Activity context, String message) {
        super(context);
        mContext = context;
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
        if (mMessageTextView != null) {
            if (StringUtils.isNullOrEmpty(mMessage)) {
                mMessageTextView.setVisibility(View.GONE);
            } else {
                mMessageTextView.setText(mMessage);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_custom_progress);

        mLoadingImageView = (ImageView) findViewById(R.id.dialog_custom_progress_loading_iv);
        mMessageTextView = (TextView) findViewById(R.id.dialog_custom_progress_message_tv);

        if (mLoadingImageView != null) {
            AnimationDrawable loadingAnimation = (AnimationDrawable) mLoadingImageView.getBackground();
            loadingAnimation.setOneShot(false);
            loadingAnimation.start();
        }
        setMessage(mMessage);
    }
}
