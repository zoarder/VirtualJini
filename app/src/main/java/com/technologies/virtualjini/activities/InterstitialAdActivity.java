package com.technologies.virtualjini.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.models.PostData;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.SharedPreferencesManager;

public class InterstitialAdActivity extends BaseActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";

    //    private static final int START_LEVEL = 1;
//    private int mLevel;
//    private Button mNextLevelButton;
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
//    private TextView mLevelTextView;

    private PostData mPostItem;
    private String from;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void setContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_interstitial_ad);
        if (getIntent() != null) {
            mPostItem = getIntent().getParcelableExtra("post");
            from = getIntent().getStringExtra("from");
            position = getIntent().getIntExtra("position", 0);
        }
    }

    @Override
    void setupActionBar() {

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
        mProgressBar = (ProgressBar) findViewById(R.id.activity_interstitial_ad_main_pb);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
//        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
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
        int adCounter = SharedPreferencesManager.getIntegerSetting(InterstitialAdActivity.this, AFCHealthConstants.AD_COUNTER, 0);
        SharedPreferencesManager.setIntegerSetting(InterstitialAdActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mProgressBar.setVisibility(View.GONE);
                SharedPreferencesManager.setIntegerSetting(InterstitialAdActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                showInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mProgressBar.setVisibility(View.GONE);
                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void goToNextLevel() {
        Intent intent = new Intent(InterstitialAdActivity.this, PostDetailsActivity.class);
        intent.putExtra("post", mPostItem);
        intent.putExtra("from", from);
        intent.putExtra("position", position);
        startNextWithAnimation(intent, true);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        exitThisWithAnimation();
    }
}
