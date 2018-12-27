package com.technologies.virtualjini.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.SharedPreferencesManager;

public class ContactUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void setContentView() {
        setContentView(R.layout.activity_contact_us);
    }

    @Override
    void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_contact_us_parent_tb);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    void initializeEditTextComponents() {

    }

    @Override
    void initializeButtonComponents() {

    }

    @Override
    void initializeTextViewComponents() {

    }

    @Override
    void initializeImageViewComponents() {

    }

    @Override
    void initializeOtherViewComponents() {

    }

    @Override
    void initializeViewComponentsEventListeners() {

    }

    @Override
    void removeViewComponentsEventListeners() {

    }

    @Override
    void exitThisWithAnimation() {
        finish();
        overridePendingTransition(R.anim.trans_top_in,
                R.anim.trans_top_out);
    }

    @Override
    void startNextWithAnimation(Intent intent, boolean isFinish) {
        if (isFinish) {
            finish();
        }
        int adCounter = SharedPreferencesManager.getIntegerSetting(ContactUsActivity.this, AFCHealthConstants.AD_COUNTER, 0);
        SharedPreferencesManager.setIntegerSetting(ContactUsActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        exitThisWithAnimation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitThisWithAnimation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
