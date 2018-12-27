package com.technologies.virtualjini.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.interfaces.ImageChooseOptionDialogInterface;

public class ImageChooseOptionDialog extends Dialog {

    private final Activity mContext;
    private Button mCameraButton, mDefaultButton;
    private Button mCancelButton;
    private Button mGalleryButtom;
    private ImageChooseOptionDialogInterface mImageChooseOptionDialogInterfaceListener;

    public void setImageChooseOptionDialogInterfaceListener(
            ImageChooseOptionDialogInterface mImageChooseOptionDialogInterfaceListener) {
        this.mImageChooseOptionDialogInterfaceListener = mImageChooseOptionDialogInterfaceListener;
    }

    public ImageChooseOptionDialog(Activity context) {
        super(context);
        mContext = context;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_choose_option);

        /**
         * used to set dialog to the bottom of screen
         */
        Window activityWindow = getWindow();
        WindowManager.LayoutParams windowLayoutParameters = activityWindow.getAttributes();
        windowLayoutParameters.windowAnimations = R.style.DialogAnimation;
        windowLayoutParameters.gravity = Gravity.BOTTOM;
        windowLayoutParameters.width = ViewGroup.LayoutParams.MATCH_PARENT;
        windowLayoutParameters.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        activityWindow.setAttributes(windowLayoutParameters);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mGalleryButtom = (Button) findViewById(R.id.dialog_image_choose_option_use_camera_roll_bt);
        mGalleryButtom.setText(mContext.getResources().getString(R.string.image_capture));
        mGalleryButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageChooseOptionDialogInterfaceListener.onGalleryOptionClicked();
                dismiss();
            }
        });
        mCameraButton = (Button) findViewById(R.id.dialog_image_choose_option_take_picture_bt);
        mCameraButton.setText(mContext.getResources().getString(R.string.image_capture_1));
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageChooseOptionDialogInterfaceListener.onCameraOptionClicked();
                dismiss();
            }
        });

        mDefaultButton = (Button) findViewById(R.id.dialog_image_choose_option_default_bt);
        mDefaultButton.setText(mContext.getResources().getString(R.string.image_pick_from_gallery));
        mDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageChooseOptionDialogInterfaceListener.onDefaultOptionClicked();
                dismiss();
            }
        });

        mCancelButton = (Button) findViewById(R.id.dialog_image_choose_option_cancel_bt);
        mCancelButton.setText(mContext.getResources().getString(R.string.cancel));
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
    }
}