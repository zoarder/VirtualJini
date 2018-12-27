package com.technologies.virtualjini.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.AppConfig;
import com.technologies.virtualjini.utils.AppController;
import com.technologies.virtualjini.utils.CommonUtils;
import com.technologies.virtualjini.utils.SharedPreferencesManager;
import com.technologies.virtualjini.utils.ShowLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.technologies.virtualjini.utils.AFCHealthConstants.KEY_ERROR;
import static com.technologies.virtualjini.utils.AFCHealthConstants.KEY_MESSAGE;
import static com.technologies.virtualjini.utils.AFCHealthConstants.LOGIN_USER_ADDRESS;
import static com.technologies.virtualjini.utils.AFCHealthConstants.LOGIN_USER_FULL_NAME;
import static com.technologies.virtualjini.utils.AFCHealthConstants.LOGIN_USER_GENDER;
import static com.technologies.virtualjini.utils.AFCHealthConstants.LOGIN_USER_PASSWORD;
import static com.technologies.virtualjini.utils.AFCHealthConstants.LOGIN_USER_PHONE_NO;
import static com.technologies.virtualjini.utils.AFCHealthConstants.USER_IS_LOGIN;

/**
 * Created by AFC on 9/4/2016.
 */
public class RegistrationActivity extends ParentActivity {
    private static final String TAG = "RegistrationActivity";
    private String[] gender = {"Male", "Female"};
    private AutoCompleteTextView activity_registration_atv_city;
    private EditText activity_registration_atv_full_name;
    private EditText activity_registration_atv_phone;
    private AutoCompleteTextView activity_registration_atv_gender;
    private EditText activity_registration_atv_password;
    private EditText activity_registration_atv_confirm_password;
    private Button activity_registration_btn_register;
    private String[] cities;

    private Activity activity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_registration_parent_tb);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        activity = this;
        context = getApplicationContext();
        // Obtain the shared Tracker instance.
//        AppController application = (AppController) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName(TAG);
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        activity_registration_atv_full_name = (EditText) findViewById(R.id.activity_registration_atv_full_name);
        activity_registration_atv_city = (AutoCompleteTextView) findViewById(R.id.activity_registration_atv_city);
        activity_registration_atv_phone = (EditText) findViewById(R.id.activity_registration_atv_phone);
        activity_registration_atv_gender = (AutoCompleteTextView) findViewById(R.id.activity_registration_atv_gender);
        activity_registration_atv_password = (EditText) findViewById(R.id.activity_registration_atv_password);
        activity_registration_atv_confirm_password = (EditText) findViewById(R.id.activity_registration_atv_confirm_password);
        activity_registration_btn_register = (Button) findViewById(R.id.activity_registration_btn_register);

//        showTermsDialog();

        cities = getResources().getStringArray(R.array.list_of_cities);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        activity_registration_atv_city.setThreshold(1);
        activity_registration_atv_city.setAdapter(adapter1);
        activity_registration_atv_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    activity_registration_atv_city.showDropDown();
                }
            }
        });

        activity_registration_atv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                activity_registration_atv_city.showDropDown();
            }
        });

        activity_registration_atv_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity_registration_atv_city.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_registration_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptRegister();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, gender);
        activity_registration_atv_gender.setThreshold(0);
        activity_registration_atv_gender.setAdapter(adapter);

        activity_registration_atv_gender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    activity_registration_atv_gender.showDropDown();
                }
            }
        });

        activity_registration_atv_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                activity_registration_atv_gender.showDropDown();
            }
        });

        activity_registration_atv_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity_registration_atv_city.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_registration_atv_full_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity_registration_atv_full_name.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_registration_atv_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity_registration_atv_phone.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_registration_atv_gender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity_registration_atv_gender.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_registration_atv_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity_registration_atv_password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_registration_atv_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity_registration_atv_confirm_password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        CommonUtils.hideSoftInputMode(activity, activity_registration_btn_register);
        // Reset errors.
        activity_registration_atv_full_name.setError(null);
        activity_registration_atv_phone.setError(null);
        activity_registration_atv_password.setError(null);
        activity_registration_atv_confirm_password.setError(null);
        activity_registration_atv_city.setError(null);
        activity_registration_atv_gender.setError(null);

        // Check for a valid full name.
        String userFullName = activity_registration_atv_full_name.getText().toString().trim();
        if (!CommonUtils.validateUserFullName(activity, userFullName, activity_registration_atv_full_name)) {
            return;
        }

        // Check for a valid phone number.
        String userPhoneNo = activity_registration_atv_phone.getText().toString().trim();
        String phoneNoWithCode = userPhoneNo.contains("+88") ? userPhoneNo : "+88" + userPhoneNo;
        if (!CommonUtils.validateUserPhoneNo(activity, userPhoneNo, phoneNoWithCode, activity_registration_atv_phone)) {
            return;
        }

        // Check for a valid gender.
        String userGender = activity_registration_atv_gender.getText().toString().trim();
        if (!CommonUtils.validateUserGender(activity, userGender, gender, activity_registration_atv_gender)) {
            return;
        }

        // Check for a valid doctor city.
        String userCity = activity_registration_atv_city.getText().toString().trim();
        if (!CommonUtils.validateUserCity(activity, userCity, cities, activity_registration_atv_city)) {
            return;
        }

        // Check for a valid password.
        String userPassword = activity_registration_atv_password.getText().toString().trim();
        if (!CommonUtils.validateUserPassword(activity, userPassword, activity_registration_atv_password)) {
            return;
        }

        // Check for a valid confirmed password.
        String userConfirmedPassword = activity_registration_atv_confirm_password.getText().toString().trim();
        if (!CommonUtils.validateUserConfirmPassword(activity, userPassword, userConfirmedPassword, activity_registration_atv_confirm_password)) {
            return;
        }

        String g = "1";
        if (userGender.equals(gender[1])) {
            g = "2";
        }

        signUp(userFullName, g, userPhoneNo, userCity, userPassword);
    }


    /**
     * function to verify login details in mysql db
     */
    private void signUp(final String userFullName, final String userGender, final String userPhoneNo, final String userCity, final String userPassword) {
        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.MAIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ShowLog.d(TAG, "Registration Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean error = jObj.getBoolean(KEY_ERROR);
                    String message = jObj.getString(KEY_MESSAGE);
                    // Check for error of response
                    if (error) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(getResources().getString(R.string.title_not_success));
                        builder.setMessage(message);
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
                    } else {
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_FULL_NAME, jObj.getJSONObject("data").getString(LOGIN_USER_FULL_NAME));
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_PHONE_NO, jObj.getJSONObject("data").getString(LOGIN_USER_PHONE_NO));
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_GENDER, jObj.getJSONObject("data").getString(LOGIN_USER_GENDER));
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_PASSWORD, userPassword);
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_ADDRESS, jObj.getJSONObject("data").getString(LOGIN_USER_ADDRESS));
                        SharedPreferencesManager.setBooleanSetting(activity, USER_IS_LOGIN, true);
                        setResult(Activity.RESULT_OK);
                        exitThisWithAnimation();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowLog.d(TAG, "Error: " + error.getMessage());
                hideDialog();
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    ShowLog.d("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }

                if (error instanceof TimeoutError) {
                    ShowLog.d("Volley", "TimeoutError");
                    return;
                } else if (error instanceof NoConnectionError) {
                    ShowLog.e("Volley", "NoConnectionError");
                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
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
                } else if (error instanceof AuthFailureError) {
                    ShowLog.e("Volley", "AuthFailureError");
                    return;
                } else if (error instanceof ServerError) {
                    // ShowLog.e("Volley", "ServerError");
                    return;
                } else if (error instanceof NetworkError) {
                    // ShowLog.e("Volley", "NetworkError");
                    return;
                } else if (error instanceof ParseError) {
                    ShowLog.e("Volley", "ParseError");
                    return;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(getString(R.string.api_param_62), AppConfig.REGISTRATION_URL);
                params.put(getString(R.string.api_param_72), userFullName);
                params.put(getString(R.string.api_param_75), userCity);
                params.put(getString(R.string.api_param_76), userGender);
                params.put(getString(R.string.api_param_63), userPhoneNo);
                params.put(getString(R.string.api_param_64), userPassword);
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                AFCHealthConstants.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq);
    }

//    private void showTermsDialog() {
//
//        String text = "<html><head>"
//                + "<style type=\"text/css\">body{color: #fff; background-color: #07872A;}"
//                + "</style></head>"
//                + "<body>"
//                + "<p align=\"justify\">"
//                + getString(R.string.reg_text)
//                + "</p> "
//                + "</body></html>";
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.qms_disclaimer);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//
//        TextView qms_disclaimer_tv_disclaimer = (TextView) dialog.findViewById(R.id.qms_disclaimer_tv_disclaimer);
//        WebView qms_disclaimer_wv_text = (WebView) dialog.findViewById(R.id.qms_disclaimer_wv_text);
//        final CheckBox qms_disclaimer_cb_disclaimer = (CheckBox) dialog.findViewById(R.id.qms_disclaimer_cb_disclaimer);
//        Button qms_disclaimer_btn_cancel = (Button) dialog.findViewById(R.id.qms_disclaimer_btn_cancel);
//        Button qms_disclaimer_btn_agree = (Button) dialog.findViewById(R.id.qms_disclaimer_btn_agree);
//        qms_disclaimer_cb_disclaimer.setVisibility(View.GONE);
//
//        qms_disclaimer_tv_disclaimer.setText("Attention!");
//
//        qms_disclaimer_wv_text.loadData(text, "text/html", "utf-8");
//        qms_disclaimer_cb_disclaimer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        qms_disclaimer_btn_agree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        qms_disclaimer_btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                overridePendingTransition(R.anim.trans_right_in,
//                        R.anim.trans_right_out);
//            }
//        });
//        dialog.show();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CommonUtils.hideSoftInputMode(activity, activity_registration_btn_register);
                setResult(Activity.RESULT_CANCELED);
                exitThisWithAnimation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        CommonUtils.hideSoftInputMode(activity, activity_registration_btn_register);
        setResult(Activity.RESULT_CANCELED);
        exitThisWithAnimation();
    }
}
