package com.technologies.virtualjini.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.dialog.ImageChooseOptionDialog;
import com.technologies.virtualjini.interfaces.ImageChooseOptionDialogInterface;
import com.technologies.virtualjini.models.AddEditPostResponse;
import com.technologies.virtualjini.models.PostData;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.AppConfig;
import com.technologies.virtualjini.utils.AppController;
import com.technologies.virtualjini.utils.CommonUtils;
import com.technologies.virtualjini.utils.DateTimeUtils;
import com.technologies.virtualjini.utils.ImagePickerConstants;
import com.technologies.virtualjini.utils.ImageUtils;
import com.technologies.virtualjini.utils.SharedPreferencesManager;
import com.technologies.virtualjini.utils.ShowLog;
import com.technologies.virtualjini.utils.StringUtils;
import com.technologies.virtualjini.volley.ObjectRequest;
import com.technologies.virtualjini.volley.VolleyUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.technologies.virtualjini.utils.AFCHealthConstants.LOGIN_USER_PHONE_NO;


public class AddEditPostActivity extends BaseActivity {

    private static final String TAG = AddEditPostActivity.class.getSimpleName();
    private PostData data;
    private int type;
    private int position;
    private Activity activity;
    private Context context;
    private EditText mToletTitleEditText;
    private EditText mToletAddressEditText;
    private EditText mRentedCostEditText;
    private EditText mRentedSizeEditText;
    private EditText mBedroomEditText;
    private EditText mBathroomEditText;
    private EditText mCarParkingEditText;
    private EditText mLivingRoomEditText;
    private EditText mDiningRoomEditText;
    private AutoCompleteTextView mToletTypeEditText;
    private EditText mRentedFromEditText;
    private TextView mAddEditTextView;
    private ImageButton mRentedFromImageButton;
    private ImageView mMainImageView;
    private ImageView mOptionalImageView1;
    private ImageView mOptionalImageView2;
    private ImageView mOptionalImageView3;
    private ImageView mOptionalImageView4;
    private ImageView mOptionalImageView5;
    private Button mActionButton;

    private ObjectRequest<AddEditPostResponse> mAddEditPostObjectRequest;
    private PostData mAddEditPostResponseData = null;
    private AddEditPostResponse mAddEditPostResponse = null;
    private String[] toletTypeList = {"Residential", "Commercial"};
    private ImageChooseOptionDialog mImageChooseOptionDialog;
    private String image_for;
    private int code;
    private boolean isSetMainImage = false;
    private boolean isSetOptionalImage1 = false;
    private boolean isSetOptionalImage2 = false;
    private boolean isSetOptionalImage3 = false;
    private boolean isSetOptionalImage4 = false;
    private boolean isSetOptionalImage5 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toletTypeList);
        mToletTypeEditText.setThreshold(0);
        mToletTypeEditText.setAdapter(adapter);

        mToletTypeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mToletTypeEditText.showDropDown();
                }
            }
        });

        mToletTypeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                mToletTypeEditText.showDropDown();
            }
        });
    }

    @Override
    void setContentView() {
        setContentView(R.layout.activity_add_edit_post);
        activity = this;
        context = getApplicationContext();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            type = b.getInt("type");
            position = b.getInt("position");
            data = b.getParcelable("data");
        }
    }

    @Override
    void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_add_edit_post_parent_tb);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (type == 1) {
                getSupportActionBar().setTitle(R.string.add_post_title);
            } else if (type == 2) {
                getSupportActionBar().setTitle(R.string.edit_post_title);
            }
        }
    }

    @Override
    void initializeEditTextComponents() {
        mToletTitleEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_tolet_title_et);
        mToletAddressEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_tolet_address_et);
        mRentedCostEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_rented_cost_et);
        mRentedSizeEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_rented_size_et);
        mBedroomEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_no_of_bedroom_et);
        mBathroomEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_no_of_bathroom_et);
        mCarParkingEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_no_of_carparking_et);
        mDiningRoomEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_no_of_dyningroom_et);
        mLivingRoomEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_no_of_livingroom_et);
        mToletTypeEditText = (AutoCompleteTextView) findViewById(R.id.activity_add_edit_post_content_layout_tolet_type_et);
        mRentedFromEditText = (EditText) findViewById(R.id.activity_add_edit_post_content_layout_rented_from_et);
        if (type == 2) {
            mToletTitleEditText.setText(data.getToletTitle());
            mToletAddressEditText.setText(data.getToletAddress());
            mRentedCostEditText.setText(data.getToletRent());
            mRentedSizeEditText.setText(data.getToletSqfeet());
            mBedroomEditText.setText(data.getToletBedRoom());
            mCarParkingEditText.setText(data.getToletCarParking());
            mBathroomEditText.setText(data.getToletBathRoom());
            mLivingRoomEditText.setText(data.getToletLivingRoom());
            mDiningRoomEditText.setText(data.getToletDyningRoom());
            mToletTypeEditText.setText(data.getToletType());
            mRentedFromEditText.setText(data.getToletFrom());
        }
    }

    @Override
    void initializeButtonComponents() {
        mRentedFromImageButton = (ImageButton) findViewById(R.id.activity_add_edit_post_content_layout_rented_from_picker_ib);
    }

    @Override
    void initializeTextViewComponents() {
        mAddEditTextView = (TextView) findViewById(R.id.activity_add_edit_post_content_layout_button_tv);
        if (type == 1) {
            mAddEditTextView.setText(R.string.add_post_title);
        } else if (type == 2) {
            mAddEditTextView.setText(R.string.edit_post_title);
        }
    }

    @Override
    void initializeImageViewComponents() {
        mMainImageView = (ImageView) findViewById(R.id.activity_add_edit_post_content_layout_image_iv_1);
        mOptionalImageView1 = (ImageView) findViewById(R.id.activity_add_edit_post_content_layout_image_iv_2);
        mOptionalImageView2 = (ImageView) findViewById(R.id.activity_add_edit_post_content_layout_image_iv_3);
        mOptionalImageView3 = (ImageView) findViewById(R.id.activity_add_edit_post_content_layout_image_iv_4);
        mOptionalImageView4 = (ImageView) findViewById(R.id.activity_add_edit_post_content_layout_image_iv_5);
        mOptionalImageView5 = (ImageView) findViewById(R.id.activity_add_edit_post_content_layout_image_iv_6);
    }

    @Override
    void initializeOtherViewComponents() {
        mImageChooseOptionDialog = new ImageChooseOptionDialog(activity);
        mImageChooseOptionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mImageChooseOptionDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(mImageChooseOptionDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mImageChooseOptionDialog.getWindow().setAttributes(layoutParams);

        mImageChooseOptionDialog
                .setImageChooseOptionDialogInterfaceListener(new ImageChooseOptionDialogInterface() {

                    @Override
                    public void onCameraOptionClicked() {
                        Intent intent = new Intent(activity, TakePhotoActivity.class);
                        intent.putExtra("action", "camera");
                        intent.putExtra("image_for", image_for);
                        int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
                        SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
                        startActivityForResult(intent, code);
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                    }

                    @Override
                    public void onGalleryOptionClicked() {
                        Intent intent = new Intent(activity, TakePhotoActivity.class);
                        intent.putExtra("action", "gallery");
                        intent.putExtra("image_for", image_for);
                        int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
                        SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
                        startActivityForResult(intent, code);
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                    }

                    @Override
                    public void onDefaultOptionClicked() {
                        // TODO Auto-generated method stub
                        //SharedPreferencesManager.setStringSetting(activity, PolyLifeConstants.KEY_WALLPAPER_IMAGE_PATH, "");
                    }
                });
    }

    @Override
    void initializeViewComponentsEventListeners() {
        mAddEditTextView.setOnClickListener(this);
        mRentedFromImageButton.setOnClickListener(this);
        mRentedFromEditText.setOnClickListener(this);

        mMainImageView.setOnClickListener(this);
        mOptionalImageView1.setOnClickListener(this);
        mOptionalImageView2.setOnClickListener(this);
        mOptionalImageView3.setOnClickListener(this);
        mOptionalImageView4.setOnClickListener(this);
        mOptionalImageView5.setOnClickListener(this);

        mToletTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToletTitleEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mToletAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToletAddressEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRentedCostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRentedCostEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRentedSizeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRentedSizeEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBedroomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBedroomEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCarParkingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCarParkingEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLivingRoomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLivingRoomEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDiningRoomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDiningRoomEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBathroomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBathroomEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mToletTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToletTypeEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRentedFromEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRentedFromEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCarParkingEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (keyEvent != null && id == EditorInfo.IME_ACTION_GO) {
                    // if shift key is down, then we want to insert the '\n' char in the TextView;
                    // otherwise, the default action is to send the message.
                    if (!keyEvent.isShiftPressed()) {
                        validateAddEditPost();
                        return true;
                    }
                    return false;
                } else {
                    validateAddEditPost();
                    return true;
                }
            }
        });
    }

    @Override
    void removeViewComponentsEventListeners() {
        mAddEditTextView.setOnClickListener(null);
        mRentedFromImageButton.setOnClickListener(null);
        mRentedFromEditText.setOnClickListener(null);

        mMainImageView.setOnClickListener(null);
        mOptionalImageView1.setOnClickListener(null);
        mOptionalImageView2.setOnClickListener(null);
        mOptionalImageView3.setOnClickListener(null);
        mOptionalImageView4.setOnClickListener(null);
        mOptionalImageView5.setOnClickListener(null);

        mToletTitleEditText.addTextChangedListener(null);
        mToletAddressEditText.addTextChangedListener(null);
        mRentedCostEditText.addTextChangedListener(null);
        mRentedSizeEditText.addTextChangedListener(null);
        mBedroomEditText.addTextChangedListener(null);
        mLivingRoomEditText.addTextChangedListener(null);
        mBathroomEditText.addTextChangedListener(null);
        mToletTypeEditText.addTextChangedListener(null);
        mRentedFromEditText.addTextChangedListener(null);
        mCarParkingEditText.setOnEditorActionListener(null);
        mCarParkingEditText.addTextChangedListener(null);
        mDiningRoomEditText.addTextChangedListener(null);
        mToletTypeEditText.setOnFocusChangeListener(null);
        mToletTypeEditText.setOnClickListener(null);
    }

    @Override
    void exitThisWithAnimation() {
        CommonUtils.hideSoftInputMode(activity, mAddEditTextView);
        finish();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    @Override
    void startNextWithAnimation(Intent intent, boolean isFinish) {
        CommonUtils.hideSoftInputMode(activity, mAddEditTextView);
        if (isFinish) {
            finish();
        }
        int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
        SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                exitThisWithAnimation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        exitThisWithAnimation();
    }

    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (type == 2) {
            if (view != null) {
                switch (view.getId()) {
                    case R.id.activity_add_edit_post_content_layout_rented_from_picker_ib:
                    case R.id.activity_add_edit_post_content_layout_rented_from_et:
                        if (!StringUtils.isNullOrEmpty(data.getToletFrom())) {
                            calendar.setTimeInMillis(DateTimeUtils.getMilliSecondsFromDateTime(data.getToletFrom(), DateTimeUtils.DATE_TIME_FORMAT_SP));
                        }
                        break;
                }
            }
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        if (view != null) {
            switch (view.getId()) {
                case R.id.activity_add_edit_post_content_layout_button_tv:
                    validateAddEditPost();
                    break;
                case R.id.activity_add_edit_post_content_layout_rented_from_picker_ib:
                    CommonUtils.requestFocus(activity, mRentedFromEditText);
                case R.id.activity_add_edit_post_content_layout_rented_from_et:
                    DateTimeUtils.setDateFromDatePicker(activity, year, month, day, DateTimeUtils.DATE_FORMAT, mRentedFromEditText);
                    break;
                case R.id.activity_add_edit_post_content_layout_image_iv_1:
                    image_for = ImagePickerConstants.KEY_MAIN_IMAGE;
                    code = 10;
                    mImageChooseOptionDialog.show();
                    break;
                case R.id.activity_add_edit_post_content_layout_image_iv_2:
                    image_for = ImagePickerConstants.KEY_OPTIONAL_IMAGE_1;
                    code = 11;
                    mImageChooseOptionDialog.show();
                    break;
                case R.id.activity_add_edit_post_content_layout_image_iv_3:
                    image_for = ImagePickerConstants.KEY_OPTIONAL_IMAGE_2;
                    code = 12;
                    mImageChooseOptionDialog.show();
                    break;
                case R.id.activity_add_edit_post_content_layout_image_iv_4:
                    image_for = ImagePickerConstants.KEY_OPTIONAL_IMAGE_3;
                    code = 13;
                    mImageChooseOptionDialog.show();
                    break;
                case R.id.activity_add_edit_post_content_layout_image_iv_5:
                    image_for = ImagePickerConstants.KEY_OPTIONAL_IMAGE_4;
                    code = 14;
                    mImageChooseOptionDialog.show();
                    break;
                case R.id.activity_add_edit_post_content_layout_image_iv_6:
                    image_for = ImagePickerConstants.KEY_OPTIONAL_IMAGE_5;
                    code = 15;
                    mImageChooseOptionDialog.show();
                    break;
            }
        }
    }

    private void validateAddEditPost() {
        CommonUtils.hideSoftInputMode(activity, mAddEditTextView);
        mToletTitleEditText.setError(null);
        mToletAddressEditText.setError(null);
        mRentedCostEditText.setError(null);
        mRentedSizeEditText.setError(null);
        mBedroomEditText.setError(null);
        mCarParkingEditText.setError(null);
        mBathroomEditText.setError(null);
        mLivingRoomEditText.setError(null);
        mDiningRoomEditText.setError(null);
        mToletTypeEditText.setError(null);
        mRentedFromEditText.setError(null);

        // Check for a valid full name.
        String toletTitle = mToletTitleEditText.getText().toString().trim();
        if (!CommonUtils.validateUserFullName(activity, toletTitle, mToletTitleEditText)) {
            return;
        }

        // Check for a valid email.
        String toletAddress = mToletAddressEditText.getText().toString().trim();
        if (!CommonUtils.validateUserAddress(activity, toletAddress, mToletAddressEditText)) {
            return;
        }

        // Check for a valid toletTypeList.
        String toletType = mToletTypeEditText.getText().toString().trim();
        if (!CommonUtils.validateUserType(activity, toletType, this.toletTypeList, mToletTypeEditText)) {
            return;
        }

        // Check for a valid rentedCost.
        String rentedCost = mRentedCostEditText.getText().toString().trim();
//        if (!CommonUtils.validateUserAddress(activity, rentedCost, mRentedCostEditText)) {
//            return;
//        }

        // Check for a valid rentedCost.
        String rentedSize = mRentedSizeEditText.getText().toString().trim();
//        if (!CommonUtils.validateUserAddress(activity, rentedSize, mRentedSizeEditText)) {
//            return;
//        }

        // Check for a valid email.
        String bedroom = mBedroomEditText.getText().toString().trim();
//        if (!CommonUtils.validateUserArea(activity, bedroom, mBedroomEditText)) {
//            return;
//        }

        // Check for a valid doctor city.
        String carParking = mCarParkingEditText.getText().toString().trim();
//        if (!CommonUtils.validateUserCity(activity, carParking, cities, mCityEditText)) {
//            return;
//        }

        // Check for a valid toletTypeList.
        String bathRoom = mBathroomEditText.getText().toString().trim();
//        if (!CommonUtils.validateNationalID(activity, bathRoom, mBathroomEditText)) {
//            return;
//        }

        // Check for a valid doctor city.
        String livingRoom = mLivingRoomEditText.getText().toString().trim();
//        if (!CommonUtils.validateUserCity(activity, carParking, cities, mCityEditText)) {
//            return;
//        }

        // Check for a valid toletTypeList.
        String diningRoom = mDiningRoomEditText.getText().toString().trim();
//        if (!CommonUtils.validateNationalID(activity, bathRoom, mBathroomEditText)) {
//            return;
//        }

        // Check for a valid rentedFrom.
        String rentedFrom = mRentedFromEditText.getText().toString().trim();
//        if (!CommonUtils.validateUserBirthDate(activity, rentedFrom, mRentedFromEditText)) {
//            return;
//        }
        if (!isUpdatable(toletTitle, toletAddress, rentedCost, rentedSize, bedroom, carParking, bathRoom, livingRoom, diningRoom, toletType, rentedFrom) && !isSetMainImage && !isSetOptionalImage1 && !isSetOptionalImage2 && !isSetOptionalImage3 && !isSetOptionalImage4 && !isSetOptionalImage5) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(AddEditPostActivity.this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Not Updated!!!");
            builder.setMessage("Nothing to Update.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.show();
            return;
        }
        postAddEditPost(toletTitle, toletAddress, rentedCost, rentedSize, bedroom, carParking, bathRoom, livingRoom, diningRoom, toletType, rentedFrom);
    }

    private boolean isUpdatable(String toletTitle, String toletAddress, String rentedCost, String rentedSize, String bedroom, String carParking, String bathRoom, String livingRoom, String diningRoom, String toletType, String rentedFrom) {
        if (!toletTitle.equals(data.getToletTitle())) {
            return true;
        }

        if (!toletAddress.equals(data.getToletAddress())) {
            return true;
        }

        if (!rentedCost.equals(data.getToletRent())) {
            return true;
        }

        if (!rentedSize.equals(data.getToletSqfeet())) {
            return true;
        }

        if (!bedroom.equals(data.getToletBedRoom())) {
            return true;
        }

        if (!carParking.equals(data.getToletCarParking())) {
            return true;
        }

        if (!bathRoom.equals(data.getToletBathRoom())) {
            return true;
        }

        if (!livingRoom.equals(data.getToletLivingRoom())) {
            return true;
        }

        if (!diningRoom.equals(data.getToletDyningRoom())) {
            return true;
        }

        if (!toletType.equals(data.getToletType())) {
            return true;
        }

        if (!rentedFrom.equals(data.getToletFrom())) {
            return true;
        }
        return false;
    }

    private void postAddEditPost(final String toletTitle, final String toletAddress, final String rentedCost, final String rentedSize, final String bedroom, final String carParking, final String bathRoom, final String livingRoom, final String diningRoom, final String toletType, final String rentedFrom) {

        Map<String, String> params = new HashMap<>();
        if (type == 1) {
            params.put(getString(R.string.api_param_61), AppConfig.INSERT_URL);
            pDialog.setMessage("Adding Post");
        } else if (type == 2) {
            pDialog.setMessage("Updating Post");
            params.put(getString(R.string.api_param_61), AppConfig.EDIT_URL);
            params.put(getString(R.string.api_param_12), SharedPreferencesManager.getStringSetting(AddEditPostActivity.this, AFCHealthConstants.LOGIN_USER_PHONE_NO, ""));
            params.put(getString(R.string.api_param_13), data.getId());
        }
        showDialog();
        params.put(getString(R.string.api_param_26), SharedPreferencesManager.getStringSetting(activity, LOGIN_USER_PHONE_NO, ""));
        params.put(getString(R.string.api_param_47), toletTitle);
        params.put(getString(R.string.api_param_27), toletAddress);
        params.put(getString(R.string.api_param_45), rentedCost);
        params.put(getString(R.string.api_param_67), rentedSize);
        params.put(getString(R.string.api_param_49), bedroom);
        params.put(getString(R.string.api_param_46), carParking);
        params.put(getString(R.string.api_param_87), bathRoom);
        params.put(getString(R.string.api_param_81), livingRoom);
        params.put(getString(R.string.api_param_84), diningRoom);
        params.put(getString(R.string.api_param_48), toletType);
        params.put(getString(R.string.api_param_44), rentedFrom);
        params.put(getString(R.string.api_param_94), bedroom);
        params.put(getString(R.string.api_param_69), carParking);
        params.put(getString(R.string.api_param_78), bathRoom);
        params.put(getString(R.string.api_param_18), livingRoom);
        params.put(getString(R.string.api_param_38), diningRoom);
        params.put(getString(R.string.api_param_83), toletType);

        if (isSetMainImage) {
            File image = new File(SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_MAIN_IMAGE_PATH));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            String imageInString = getStringImage(bitmap);
            params.put(getString(R.string.api_param_11), imageInString);
        } else {
            params.put(getString(R.string.api_param_11), "");
        }

        if (isSetOptionalImage1) {
            File image = new File(SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_1));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            String imageInString = getStringImage(bitmap);
            params.put(getString(R.string.api_param_14), imageInString);
        } else {
            params.put(getString(R.string.api_param_14), "");
        }

        if (isSetOptionalImage2) {
            File image = new File(SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_2));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            String imageInString = getStringImage(bitmap);
            params.put(getString(R.string.api_param_15), imageInString);
        } else {
            params.put(getString(R.string.api_param_15), "");
        }

        if (isSetOptionalImage3) {
            File image = new File(SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_3));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            String imageInString = getStringImage(bitmap);
            params.put(getString(R.string.api_param_16), imageInString);
        } else {
            params.put(getString(R.string.api_param_16), "");
        }

        if (isSetOptionalImage4) {
            File image = new File(SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_4));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            String imageInString = getStringImage(bitmap);
            params.put(getString(R.string.api_param_17), imageInString);
        } else {
            params.put(getString(R.string.api_param_17), "");
        }

        if (isSetOptionalImage5) {
            File image = new File(SharedPreferencesManager.getStringSetting(activity, ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_5));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            String imageInString = getStringImage(bitmap);
            params.put(getString(R.string.api_param_19), imageInString);
        } else {
            params.put(getString(R.string.api_param_19), "");
        }

        mAddEditPostObjectRequest = new ObjectRequest<>(Request.Method.POST, AppConfig.MAIN_URL, params,
                new Response.Listener<AddEditPostResponse>() {
                    @Override
                    public void onResponse(AddEditPostResponse response) {
                        ShowLog.e(TAG, response.toString());
                        hideDialog();
                        if (!response.isError()) {
                            mAddEditPostResponse = response;
                            mAddEditPostResponseData = mAddEditPostResponse.getData();
                            final Intent intent = new Intent();
                            if (type == 1) {
                                intent.putExtra("data", mAddEditPostResponseData);
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                                builder.setTitle("Successful!!!");
                                builder.setMessage("Your To-Let Post Created Successfully. Our support team will contact you soon to make it Verified, Thank You.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        setResult(Activity.RESULT_OK, intent);
                                        exitThisWithAnimation();
                                    }
                                });

                                builder.show();
                            } else if (type == 2) {
                                intent.putExtra("data", mAddEditPostResponseData);
                                intent.putExtra("pos", position);
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                                builder.setTitle("Successful!!!");
                                builder.setMessage("Your To-Let Post Updated Successfully. Our support team will contact you soon to make it Verified, Thank You.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        setResult(Activity.RESULT_OK, intent);
                                        exitThisWithAnimation();
                                    }
                                });

                                builder.show();
                            }

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Unsuccessful!!!");
                            builder.setMessage(response.getMessage());
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
//                        else if (response.getCode() == HttpCode.RESPONSE_UNAUTHORIZES) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
//                            builder.setTitle(response.getTitle());
//                            builder.setMessage(response.getMessage());
//
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            });
//                            builder.show();
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
//                            builder.setTitle(response.getTitle());
//                            builder.setMessage(response.getMessage());
//
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            });
//                            builder.show();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyUtils.showVolleyResponseError(activity, error, true);
                return;
            }
        }, AddEditPostResponse.class);

        mAddEditPostObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                AFCHealthConstants.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(mAddEditPostObjectRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case Activity.RESULT_OK:
                switch (requestCode) {
                    case 10:
                        isSetMainImage = true;
                        mMainImageView.setImageBitmap(getImageBitmap());
                        break;
                    case 11:
                        isSetOptionalImage1 = true;
                        mOptionalImageView1.setImageBitmap(getImageBitmap());
                        break;
                    case 12:
                        isSetOptionalImage2 = true;
                        mOptionalImageView2.setImageBitmap(getImageBitmap());
                        break;
                    case 13:
                        isSetOptionalImage3 = true;
                        mOptionalImageView3.setImageBitmap(getImageBitmap());
                        break;
                    case 14:
                        isSetOptionalImage4 = true;
                        mOptionalImageView4.setImageBitmap(getImageBitmap());
                        break;
                    case 15:
                        isSetOptionalImage5 = true;
                        mOptionalImageView5.setImageBitmap(getImageBitmap());
                        break;
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Bitmap getImageBitmap() {
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
        Bitmap bitmap = null;
        if (!StringUtils.isNullOrEmpty(imagePath)) {

            try {
                bitmap = ImageUtils.RotateBitmap(activity, imagePath);
            } catch (Exception e) {
                ShowLog.e(TAG, e.getMessage());
            }
        } else {
//            mProfileImageView.setImageResource(R.mipmap.ic_launcher);
        }
        return bitmap;
    }

    public String getStringImage(Bitmap bmp) {
        String encodedImage = null;
        try {
            int scaleToUse = 20; // this will be our percentage
            int width = (bmp.getWidth() - (bmp.getWidth() * (scaleToUse / 100)));
            int height = (bmp.getHeight() - (bmp.getHeight() * (scaleToUse / 100)));

            if (bmp.getHeight() > bmp.getWidth()) {
                width = 480;
                height = 720;
            } else {
                width = 720;
                height = 480;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();

            Bitmap b = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            b = Bitmap.createScaledBitmap(b, width, height, false);

            baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            imageBytes = baos.toByteArray();

            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        } catch (Exception ex) {
        }
        return encodedImage;
    }
}
