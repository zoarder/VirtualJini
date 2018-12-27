/**
 * BJIT CONFIDENTIAL                                                 *
 * Copyright 2014,2015 BJIT Group                                *
 *
 * @file NotificationDialog.java
 * <p>
 * This is the activity to show Notification Dialog.
 * @file NotificationDialog.java
 * <p>
 * This is the activity to show Notification Dialog.
 * @file NotificationDialog.java
 * <p>
 * This is the activity to show Notification Dialog.
 */
/**
 * @file NotificationDialog.java
 *
 *       This is the activity to show Notification Dialog.
 */

package com.technologies.virtualjini.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.utils.StringUtils;

/**
 * The Class NotificationDialog.
 *
 * @author Al Muktadir
 */
public class NotificationDialog extends Dialog {
    private final String mHeader;
    private final String mMessage;
    private final String mButtonTitle;

    private TextView mHeaderTextView;
    private TextView mMessageTextView;
    private Button mActionButton;

    public NotificationDialog(Activity context, String header, String message,
                              String buttonTitle) {
        super(context);
        this.mHeader = header;
        this.mMessage = message;
        this.mButtonTitle = buttonTitle;
    }

    public NotificationDialog(Activity context, String message,
                              String buttonTitle) {
        super(context);
        this.mHeader = "";
        this.mMessage = message;
        this.mButtonTitle = buttonTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_notification);

        this.mHeaderTextView = (TextView) findViewById(R.id.dialog_notification_header_tv);
        if (StringUtils.isNullOrEmpty(mHeader)) {
            mHeaderTextView.setVisibility(View.INVISIBLE);
        } else {
            this.mHeaderTextView.setText(mHeader);
        }

        this.mMessageTextView = (TextView) findViewById(R.id.dialog_notification_message_tv);
        if (StringUtils.isNullOrEmpty(mHeader)) {
            this.mMessageTextView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        this.mMessageTextView.setText(mMessage);

        this.mActionButton = (Button) findViewById(R.id.dialog_notification_action_bt);
        this.mActionButton.setText(mButtonTitle);
        this.mActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
