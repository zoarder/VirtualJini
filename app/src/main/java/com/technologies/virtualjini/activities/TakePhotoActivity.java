package com.technologies.virtualjini.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.imagepicker.ZoomableImageView;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.CommonUtils;
import com.technologies.virtualjini.utils.ImagePickerConstants;
import com.technologies.virtualjini.utils.ImageUtils;
import com.technologies.virtualjini.utils.SharedPreferencesManager;
import com.technologies.virtualjini.utils.ShowLog;
import com.technologies.virtualjini.utils.StringUtils;

public class TakePhotoActivity extends ParentActivity {

    public static final String TAG = TakePhotoActivity.class.getSimpleName();
    private ZoomableImageView mProfileImageView;
    private String action = null;
    private Activity activity;
    private Context context;
    private String image_for;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);


        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_take_photo_parent_tb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;
        context = getApplicationContext();

        mProfileImageView = (ZoomableImageView) findViewById(R.id.activity_take_photo_title_iv);
//        mNameTextView = (EditText) findViewById(R.id.activity_take_photo_patient_name_tv);
//        mMobileTextView = (EditText) findViewById(R.id.activity_take_photo_patient_mobile_tv);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            action = b.getString("action");
            image_for = b.getString("image_for");
        }

        if (action.equals("camera")) {
            CommonUtils.dispatchTakePictureIntent(activity, image_for);
        } else {
            Intent intent = new Intent(activity, ImagePickerActivity.class);
            if (intent != null) {
                intent.putExtra("image_for", image_for);
                int adCounter = SharedPreferencesManager.getIntegerSetting(TakePhotoActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                SharedPreferencesManager.setIntegerSetting(TakePhotoActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                startActivityForResult(intent,
                        ImagePickerConstants.REQUEST_FOR_GALLERY_PHOTO);
                overridePendingTransition(R.anim.trans_bottom_in,
                        R.anim.trans_bottom_out);
            }
        }

//        mMobileTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (keyEvent != null && id == EditorInfo.IME_ACTION_GO) {
//                    // if shift key is down, then we want to insert the '\n' char in the TextView;
//                    // otherwise, the default action is to send the message.
//                    if (!keyEvent.isShiftPressed()) {
//                        attemptSendImage();
//                        return true;
//                    }
//                    return false;
//                } else {
//                    attemptSendImage();
//                    return true;
//                }
//            }
//        });
//
//        mNameTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mNameTextView.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        mMobileTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mMobileTextView.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    public void onClickItem(View v) {
        // TODO Auto-generated method stub
        Intent intent = null;
        switch (v.getId()) {
            case R.id.activity_take_photo_take_picture_iv:
                CommonUtils.dispatchTakePictureIntent(activity, image_for);
                break;
            case R.id.activity_take_photo_use_camera_roll_iv:
                intent = new Intent(activity, ImagePickerActivity.class);
                if (intent != null) {
                    intent.putExtra("image_for", image_for);
                    int adCounter = SharedPreferencesManager.getIntegerSetting(TakePhotoActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                    SharedPreferencesManager.setIntegerSetting(TakePhotoActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                    startActivityForResult(intent, ImagePickerConstants.REQUEST_FOR_GALLERY_PHOTO);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                }
                break;
            case R.id.activity_take_photo_proceed:
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                super.exitThisWithAnimation();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        System.gc();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImagePickerConstants.REQUEST_FOR_TAKE_PHOTO:
                    setImage();
                    break;
                case ImagePickerConstants.REQUEST_FOR_GALLERY_PHOTO:
                    if (data != null) {
                        if (image_for.equals(ImagePickerConstants.KEY_MAIN_IMAGE)) {
                            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_MAIN_IMAGE_PATH,
                                    data.getStringExtra(ImagePickerConstants.KEY_MAIN_IMAGE_PATH));
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_1)) {
                            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_1,
                                    data.getStringExtra(ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_1));
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_2)) {
                            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_2,
                                    data.getStringExtra(ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_2));
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_3)) {
                            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_3,
                                    data.getStringExtra(ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_3));
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_4)) {
                            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_4,
                                    data.getStringExtra(ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_4));
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_5)) {
                            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_5,
                                    data.getStringExtra(ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_5));
                        } else {
                            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_DEFAULT_IMAGE_PATH,
                                    data.getStringExtra(ImagePickerConstants.KEY_DEFAULT_IMAGE_PATH));
                        }
                        setImage();
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            exitThisWithAnimation();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setImage() {
        String imagePath = "";
        if (image_for.equals(ImagePickerConstants.KEY_MAIN_IMAGE)) {
            imagePath = SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_MAIN_IMAGE_PATH);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_1)) {
            imagePath = SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_1);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_2)) {
            imagePath = SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_2);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_3)) {
            imagePath = SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_3);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_4)) {
            imagePath = SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_4);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_5)) {
            imagePath = SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_5);
        } else {
            imagePath = SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_DEFAULT_IMAGE_PATH);
        }
        if (!StringUtils.isNullOrEmpty(imagePath)) {
//            isImageSet = true;
            // mProfileImageView.setImageDrawable(new BitmapDrawable(getResources(), imagePath));
            //File image = new File(imagePath);
            //BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            //Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            //bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            Bitmap bitmap = null;
            try {
                bitmap = ImageUtils.RotateBitmap(activity, imagePath);
            } catch (Exception e) {
                ShowLog.e(TAG, e.getMessage());
            }
            mProfileImageView.setImageBitmap(bitmap);
        } else {
            mProfileImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitThisWithAnimation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitThisWithAnimation();
    }

    @Override
    void exitThisWithAnimation() {
        if (image_for.equals(ImagePickerConstants.KEY_MAIN_IMAGE)) {
            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_MAIN_IMAGE_PATH,
                    null);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_1)) {
            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_1,
                    null);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_2)) {
            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_2,
                    null);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_3)) {
            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_3,
                    null);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_4)) {
            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_4,
                    null);
        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_5)) {
            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_5,
                    null);
        } else {
            SharedPreferencesManager.setStringSetting(activity, ImagePickerConstants.KEY_DEFAULT_IMAGE_PATH,
                    null);
        }
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        super.exitThisWithAnimation();
    }
}

