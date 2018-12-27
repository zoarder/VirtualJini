package com.technologies.virtualjini.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.SharedPreferencesManager;

public abstract class ParentActivity extends AppCompatActivity {
    private static final String TAG = "ParentActivity";
    ProgressDialog pDialog;
    private Activity activity;
    // The top level content view.
    private ViewGroup m_contentView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        activity = this;
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onResume() {
        System.gc();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        finish();
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup mainView = (ViewGroup) LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(mainView);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        m_contentView = (ViewGroup) view;
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        m_contentView = (ViewGroup) view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fixes android memory issue 8488 :
        // http://code.google.com/p/android/issues/detail?id=8488
        nullViewDrawablesRecursive(m_contentView);
        m_contentView = null;
        System.gc();
    }

    private void nullViewDrawablesRecursive(View view) {
        if (view != null) {
            try {
                ViewGroup viewGroup = (ViewGroup) view;

                int childCount = viewGroup.getChildCount();
                for (int index = 0; index < childCount; index++) {
                    View child = viewGroup.getChildAt(index);
                    nullViewDrawablesRecursive(child);
                }
            } catch (Exception e) {
            }

            nullViewDrawable(view);
        }
    }

    public void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.network_connection_disabled_title));
        builder.setMessage(getResources().getString(R.string.network_connection_disabled_content));
        builder.setPositiveButton("OK", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
        return;
    }

    private void nullViewDrawable(View view) {
        try {
            view.setBackgroundDrawable(null);
        } catch (Exception e) {
        }

        try {
            ImageView imageView = (ImageView) view;
            imageView.setImageDrawable(null);
            imageView.setBackgroundDrawable(null);
        } catch (Exception e) {
        }
    }

    void exitThisWithAnimation() {
        finish();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    void startNextWithAnimation(Intent intent, boolean isFinish) {
        if (isFinish) {
            finish();
        }
        int adCounter = SharedPreferencesManager.getIntegerSetting(ParentActivity.this, AFCHealthConstants.AD_COUNTER, 0);
        SharedPreferencesManager.setIntegerSetting(ParentActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }
}