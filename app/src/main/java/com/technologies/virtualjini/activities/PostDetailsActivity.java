package com.technologies.virtualjini.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.adapters.CustomAdapter;
import com.technologies.virtualjini.models.PostData;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.AppConfig;
import com.technologies.virtualjini.utils.AppController;
import com.technologies.virtualjini.utils.SharedPreferencesManager;
import com.technologies.virtualjini.utils.ShowLog;
import com.technologies.virtualjini.volley.VolleyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.technologies.virtualjini.utils.AFCHealthConstants.KEY_ERROR;
import static com.technologies.virtualjini.utils.AFCHealthConstants.KEY_MESSAGE;
import static com.technologies.virtualjini.utils.AFCHealthConstants.USER_IS_LOGIN;

/**
 * Created by ZOARDER AL MUKTADIR on 11/21/2016.
 */

public class PostDetailsActivity extends BaseActivity {

    private static final String TAG = PostDetailsActivity.class.getSimpleName();
    private CollapsingToolbarLayout mParentCollapsingToolbar;
    AdapterViewFlipper mHeaderImageAdapterViewFlipper;

    private LinearLayout activity_post_details_content_layout_button_panel_hll;
    private Button activity_post_details_content_layout_edit_bt;
    private Button activity_post_details_content_layout_delete_bt;
    private Button activity_post_details_content_layout_wishlist_bt;
    private TextView home_item_layout_title_tv;
    private TextView home_item_layout_type_tv;
    private TextView home_item_layout_bed_room_tv;
    private TextView home_item_layout_bath_room_tv;
    private TextView home_item_layout_car_parking_tv;
    private TextView home_item_layout_living_room_tv;
    private TextView home_item_layout_dyning_room_tv;
    private TextView home_item_layout_address_tv;
    private TextView home_item_layout_size_tv;
    private TextView home_item_layout_rent_tv;
    private TextView home_item_layout_available_from_tv;

    private PostData mPostItem;
    private String from;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void setContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_post_details);
        if (getIntent() != null) {
            mPostItem = getIntent().getParcelableExtra("post");
            from = getIntent().getStringExtra("from");
            position = getIntent().getIntExtra("position", 0);
        }
    }

    @Override
    void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_post_details_parent_tb);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    void initializeEditTextComponents() {
    }

    @Override
    void initializeButtonComponents() {
        activity_post_details_content_layout_edit_bt = (Button) findViewById(R.id.activity_post_details_content_layout_edit_bt);
        activity_post_details_content_layout_delete_bt = (Button) findViewById(R.id.activity_post_details_content_layout_delete_bt);
        activity_post_details_content_layout_wishlist_bt = (Button) findViewById(R.id.activity_post_details_content_layout_wishlist_bt);
        if (from.equals("my_wish_list")) {
            Drawable img = getResources().getDrawable(android.R.drawable.btn_star_big_on);
            img.setBounds(0, 0, 60, 60);
            activity_post_details_content_layout_wishlist_bt.setCompoundDrawables(img, null, null, null);
        } else if (from.equals("all_post")) {
            Drawable img = getResources().getDrawable(android.R.drawable.btn_star_big_off);
            img.setBounds(0, 0, 60, 60);
            activity_post_details_content_layout_wishlist_bt.setCompoundDrawables(img, null, null, null);
        }
    }

    @Override
    void initializeTextViewComponents() {
        home_item_layout_title_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_post_title_value_tv);
        home_item_layout_type_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_post_type_value_tv);
        home_item_layout_bed_room_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_bed_room_value_tv);
        home_item_layout_bath_room_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_bath_room_value_tv);
        home_item_layout_car_parking_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_car_parking_value_tv);
        home_item_layout_living_room_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_living_room_value_tv);
        home_item_layout_dyning_room_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_dining_room_value_tv);
        home_item_layout_address_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_address_value_tv);
        home_item_layout_size_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_size_value_tv);
        home_item_layout_rent_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_rent_value_tv);
        home_item_layout_available_from_tv = (TextView) findViewById(R.id.activity_post_details_content_layout_rented_from_value_tv);

        loadData();
    }

    private void loadData() {
        home_item_layout_title_tv.setText(mPostItem.getToletTitle());
        home_item_layout_type_tv.setText(mPostItem.getToletType());
        home_item_layout_address_tv.setText(mPostItem.getToletAddress());
        home_item_layout_available_from_tv.setText(mPostItem.getToletFrom());
        home_item_layout_size_tv.setText(mPostItem.getToletSqfeet() + " SQFT");
        home_item_layout_rent_tv.setText("BDT " + mPostItem.getToletRent() + "/Month");

        if (Integer.parseInt(mPostItem.getToletBedRoom()) > 0) {
            home_item_layout_bed_room_tv.setText(mPostItem.getToletBedRoom() + "");
        } else {
            home_item_layout_bed_room_tv.setText("0");
        }

        if (Integer.parseInt(mPostItem.getToletBathRoom()) > 0) {
            home_item_layout_bath_room_tv.setText(mPostItem.getToletBathRoom() + "");
        } else {
            home_item_layout_bath_room_tv.setText("0");
        }

        if (Integer.parseInt(mPostItem.getToletCarParking()) > 0) {
            home_item_layout_car_parking_tv.setText(mPostItem.getToletCarParking() + "");
        } else {
            home_item_layout_car_parking_tv.setText("0");
        }

        if (Integer.parseInt(mPostItem.getToletLivingRoom()) > 0) {
            home_item_layout_living_room_tv.setText(mPostItem.getToletLivingRoom() + "");
        } else {
            home_item_layout_living_room_tv.setText("0");
        }

        if (Integer.parseInt(mPostItem.getToletDyningRoom()) > 0) {
            home_item_layout_dyning_room_tv.setText(mPostItem.getToletDyningRoom() + "");
        } else {
            home_item_layout_dyning_room_tv.setText("0");
        }
    }

    @Override
    void initializeImageViewComponents() {
    }

    @Override
    void initializeOtherViewComponents() {
        mParentCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.activity_post_details_parent_ctl);
//        if (!hasExpandedTitle) {
        mParentCollapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.activity_post_details_parent_abl);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mParentCollapsingToolbar.setTitle("Post Details");
                    isShow = true;
                } else if (isShow) {
                    mParentCollapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
//        } else {
//            mParentCollapsingToolbar.setTitle(title);
//        }

        mHeaderImageAdapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.activity_post_details_header_image_avf);
        CustomAdapter customAdapter = new CustomAdapter(PostDetailsActivity.this, mPostItem.getToletDetailImages());
        mHeaderImageAdapterViewFlipper.setAdapter(customAdapter);
        mHeaderImageAdapterViewFlipper.startFlipping();
        mHeaderImageAdapterViewFlipper.setFlipInterval(3000);

        activity_post_details_content_layout_button_panel_hll = (LinearLayout) findViewById(R.id.activity_post_details_content_layout_button_panel_hll);
        if (from.equals("my_post")) {
            activity_post_details_content_layout_button_panel_hll.setVisibility(View.VISIBLE);
            activity_post_details_content_layout_wishlist_bt.setVisibility(View.GONE);
        } else if (from.equals("my_wish_list")) {
            activity_post_details_content_layout_button_panel_hll.setVisibility(View.GONE);
            activity_post_details_content_layout_wishlist_bt.setVisibility(View.VISIBLE);
            activity_post_details_content_layout_wishlist_bt.setText(getString(R.string.remove_wishlist));
        } else if (from.equals("all_post")) {
            activity_post_details_content_layout_button_panel_hll.setVisibility(View.GONE);
            activity_post_details_content_layout_wishlist_bt.setVisibility(View.VISIBLE);
            activity_post_details_content_layout_wishlist_bt.setText(getString(R.string.add_wishlist));
        }
    }

    @Override
    void initializeViewComponentsEventListeners() {
        if (from.equals("my_post")) {
            activity_post_details_content_layout_edit_bt.setOnClickListener(this);
            activity_post_details_content_layout_delete_bt.setOnClickListener(this);
        } else {
            activity_post_details_content_layout_wishlist_bt.setOnClickListener(this);
        }
    }

    @Override
    void removeViewComponentsEventListeners() {
    }

    @Override
    void exitThisWithAnimation() {
        finish();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    @Override
    void startNextWithAnimation(Intent intent, boolean isFinish) {
        if (isFinish) {
            finish();
        }
        int adCounter = SharedPreferencesManager.getIntegerSetting(PostDetailsActivity.this, AFCHealthConstants.AD_COUNTER, 0);
        SharedPreferencesManager.setIntegerSetting(PostDetailsActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }

    private void addToWishList(final String id) {
        pDialog.setMessage("Adding to Wish List...");
        showDialog();
        String userId = SharedPreferencesManager.getStringSetting(PostDetailsActivity.this, AFCHealthConstants.LOGIN_USER_PHONE_NO, "");
        final Map<String, String> params = new HashMap<>();
        params.put(getString(R.string.api_param_12), userId);
        params.put(getString(R.string.api_param_13), id);
        params.put(getString(R.string.api_param_62), AppConfig.ADD_TO_WISH_LIST);
        ShowLog.e(TAG, "Post ID: " + id);
        ShowLog.e(TAG, "User ID: " + SharedPreferencesManager.getStringSetting(PostDetailsActivity.this, AFCHealthConstants.LOGIN_USER_PHONE_NO, ""));
        ShowLog.e(TAG, "Tag: " + AppConfig.ADD_TO_WISH_LIST);

        final Map<String, String> headers = new HashMap<>();
//        headers.put(getString(R.string.api_param_1), apiToken);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.MAIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ShowLog.e(TAG, response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean code = jObj.getBoolean(KEY_ERROR);
                    String message = jObj.getString(KEY_MESSAGE);
//                    if (!code) {
//                        mDataArrayList.remove(pos);
//                        mAdapter.notifyDataSetChanged();
//                        mListener.onEducationDataDeleted(pos);
//                    } else {
//
//                    }
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle(message);
                    builder.setMessage(message);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.show();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyUtils.showVolleyResponseError(PostDetailsActivity.this, error, false);
                return;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }

            @Override
            public Map<String, String> getParams() {

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                AFCHealthConstants.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void removeFromWishList(final String id) {
        pDialog.setMessage("Removing from Wish List...");
        showDialog();
        String userId = SharedPreferencesManager.getStringSetting(PostDetailsActivity.this, AFCHealthConstants.LOGIN_USER_PHONE_NO, "");
        final Map<String, String> params = new HashMap<>();
        params.put(getString(R.string.api_param_12), userId);
        params.put(getString(R.string.api_param_13), id);
        params.put(getString(R.string.api_param_62), AppConfig.REMOVE_FROM_WISH_LIST);
        ShowLog.e(TAG, "Post ID: " + id);
        ShowLog.e(TAG, "User ID: " + SharedPreferencesManager.getStringSetting(PostDetailsActivity.this, AFCHealthConstants.LOGIN_USER_PHONE_NO, ""));
        ShowLog.e(TAG, "Tag: " + AppConfig.REMOVE_FROM_WISH_LIST);

        final Map<String, String> headers = new HashMap<>();
//        headers.put(getString(R.string.api_param_1), apiToken);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.MAIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ShowLog.e(TAG, response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean code = jObj.getBoolean(KEY_ERROR);
                    String message = jObj.getString(KEY_MESSAGE);
                    if (!code) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(message);
                        builder.setMessage(message);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("data", mPostItem);
                                intent.putExtra("type", "delete");
                                intent.putExtra("position", position);
                                setResult(Activity.RESULT_OK, intent);
                                exitThisWithAnimation();
                            }
                        });
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.show();

                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(message);
                        builder.setMessage(message);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyUtils.showVolleyResponseError(PostDetailsActivity.this, error, false);
                return;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }

            @Override
            public Map<String, String> getParams() {

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                AFCHealthConstants.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void deleteMyPost(final String id) {
        pDialog.setMessage("Deleting My Post...");
        showDialog();
        String userId = SharedPreferencesManager.getStringSetting(PostDetailsActivity.this, AFCHealthConstants.LOGIN_USER_PHONE_NO, "");
        final Map<String, String> params = new HashMap<>();
        params.put(getString(R.string.api_param_12), userId);
        params.put(getString(R.string.api_param_13), id);
        params.put(getString(R.string.api_param_62), AppConfig.DELETE_MY_POST);
        ShowLog.e(TAG, "Post ID: " + id);
        ShowLog.e(TAG, "User ID: " + SharedPreferencesManager.getStringSetting(PostDetailsActivity.this, AFCHealthConstants.LOGIN_USER_PHONE_NO, ""));
        ShowLog.e(TAG, "Tag: " + AppConfig.DELETE_MY_POST);

        final Map<String, String> headers = new HashMap<>();
//        headers.put(getString(R.string.api_param_1), apiToken);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.MAIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ShowLog.e(TAG, response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean code = jObj.getBoolean(KEY_ERROR);
                    String message = jObj.getString(KEY_MESSAGE);
                    if (!code) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(message);
                        builder.setMessage(message);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("data", mPostItem);
                                intent.putExtra("type", "delete");
                                intent.putExtra("position", position);
                                setResult(Activity.RESULT_OK, intent);
                                exitThisWithAnimation();
                            }
                        });
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.show();

                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(message);
                        builder.setMessage(message);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyUtils.showVolleyResponseError(PostDetailsActivity.this, error, false);
                return;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }

            @Override
            public Map<String, String> getParams() {

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                AFCHealthConstants.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.activity_post_details_content_layout_edit_bt:
                    if (from.equals("my_post")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle("Edit My Post!!!");
                        builder.setMessage("Are you sure that want to Edit your Post?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PostDetailsActivity.this, AddEditPostActivity.class);
                                intent.putExtra("type", 2);
                                intent.putExtra("position", position);
                                intent.putExtra("data", mPostItem);
                                int adCounter = SharedPreferencesManager.getIntegerSetting(PostDetailsActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                                SharedPreferencesManager.setIntegerSetting(PostDetailsActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                                startActivityForResult(intent, 402);
                                overridePendingTransition(R.anim.trans_left_in,
                                        R.anim.trans_left_out);
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    break;
                case R.id.activity_post_details_content_layout_delete_bt:
                    if (from.equals("my_post")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle("Delete My Post!!!");
                        builder.setMessage("Are you sure that want to Delete your Post?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteMyPost(mPostItem.getId());
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    break;
                case R.id.activity_post_details_content_layout_wishlist_bt:
                    if (from.equals("all_post")) {
                        if (SharedPreferencesManager.getBooleanSetting(PostDetailsActivity.this, USER_IS_LOGIN, false)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Add To Wish List!!!");
                            builder.setMessage("Are you sure that want to Add this post to your wish list?");
                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    addToWishList(mPostItem.getId());
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Log In Required!!!");
                            builder.setMessage("You Have to Log In First before Add this post to your wish list. Do you want to go Log In Now?");

                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PostDetailsActivity.this, LoginActivity.class);
                                    int adCounter = SharedPreferencesManager.getIntegerSetting(PostDetailsActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                                    SharedPreferencesManager.setIntegerSetting(PostDetailsActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                                    startActivityForResult(intent, 101);
                                    overridePendingTransition(R.anim.trans_left_in,
                                            R.anim.trans_left_out);
                                }
                            });
                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    } else if (from.equals("my_wish_list")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailsActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle("Remove From Wish List!!!");
                        builder.setMessage("Are you sure that want to Remove this post from your wish list?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeFromWishList(mPostItem.getId());
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        GetAllProfessionalResponseData data = null;
        switch (resultCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case Activity.RESULT_OK:
                switch (requestCode) {
                    case 401:
                        if (from.equals("all_post")) {
                            addToWishList(mPostItem.getId());
                        }
                        break;
                    case 402:
                        if (from.equals("my_post")) {
                            mPostItem = data.getExtras().getParcelable("data");
                            position = data.getExtras().getInt("pos");
                            loadData();
                            Intent intent = new Intent();
                            intent.putExtra("data", mPostItem);
                            intent.putExtra("type", "edit");
                            intent.putExtra("position", position);
                            setResult(Activity.RESULT_OK, intent);
                            exitThisWithAnimation();
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                exitThisWithAnimation();
                break;
            case R.id.action_share:
                Toast.makeText(PostDetailsActivity.this, "Process Under Development", Toast.LENGTH_SHORT).show();
//                List<Intent> targetShareIntents = new ArrayList<Intent>();
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                List<ResolveInfo> resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);
//                String encodedTitle = Base64.encodeToString((mBlogModelItem.getBlogID() + "").getBytes(), Base64.DEFAULT);
//                Log.e("encodedTitle", encodedTitle);
//                String uri = "http://scirf.com/scirfposts/share.php?q=" + encodedTitle;
////                uri = uri.replaceAll(" ", "%20");
//                if (!resInfos.isEmpty()) {
//                    Log.e("Package Name", "Have package");
//                    for (ResolveInfo resInfo : resInfos) {
//                        String packageName = resInfo.activityInfo.packageName;
//                        Log.e("Package Name", packageName);
//
//                        Intent intent = new Intent();
//                        intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
//                        intent.setAction(Intent.ACTION_SEND);
//                        intent.setPackage(packageName);
//
//                        // Messengers
//                        if (packageName.contains("com.android.mms")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.google.android.talk")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.google.android.apps.messaging")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.viber.voip")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.whatsapp")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.facebook.orca")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.imo.android.imoim")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.skype.raider")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        // Social Media
//                        if (packageName.contains("com.twitter.android")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.facebook.katana")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.google.android.apps.plus")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            targetShareIntents.add(intent);
//                        }
//
//                        // Mail Client
//                        if (packageName.contains("com.yahoo.mobile.client.android")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            intent.putExtra(Intent.EXTRA_SUBJECT, mBlogModelItem.getBlogTitle());
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.microsoft.office.outlook")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            intent.putExtra(Intent.EXTRA_SUBJECT, mBlogModelItem.getBlogTitle());
//                            targetShareIntents.add(intent);
//                        }
//
//                        if (packageName.contains("com.google.android.gm")) {
//                            intent.setType("text/plain");
//                            intent.putExtra(Intent.EXTRA_TEXT, uri);
//                            intent.putExtra(Intent.EXTRA_SUBJECT, mBlogModelItem.getBlogTitle());
//                            targetShareIntents.add(intent);
//                        }
//                    }
//                    if (!targetShareIntents.isEmpty()) {
//                        System.out.println("Have Intent");
//                        Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), getString(R.string.share_title));
//                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
//                        startActivity(chooserIntent);
//                    } else {
//                        Log.e("Package Name", "Do not Have Intent");
//                    }
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        exitThisWithAnimation();
    }
}