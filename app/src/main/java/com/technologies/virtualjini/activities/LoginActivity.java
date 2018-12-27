package com.technologies.virtualjini.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ParentActivity {

    private static final String TAG = "LoginActivity";
    public static String emailAddress = null;

    // UI references.
    private EditText activity_login_atv_phone;
    private EditText activity_login_et_password;
    private TextView activity_login_tv_forgot_password;
    private TextView activity_login_tv_sign_up;
    private Button activity_login_btn_sign_in;

    private Activity activity;
    private Context context;
    private String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        context = getApplicationContext();

//        AppController application = (AppController) getApplication();


       /* // if the user already login then he will navigate to Main screen
        if (SharedPreferencesManager.getBooleanSetting(activity, AFCHealthConstants.USER_IS_LOGIN, false)) {
            Intent intent = new Intent(activity, NavDrawerActivity.class);
            intent.putExtra("orderInvoiceNo", pendingOrderId);
            intent.putExtra("isExceeded24H", isPendingOrderExceeds24h);
            intent.putExtra("deliveryContact", orderDeliveryContact);
            intent.putExtra("orderStatus", orderStatusDetailsArrayList);
            startActivity(intent);
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            finish();
        }
*/
        // Set up the login form.
        activity_login_atv_phone = (EditText) findViewById(R.id.activity_login_atv_phone);
        userId = SharedPreferencesManager.getStringSetting(activity, AFCHealthConstants.LOGIN_USER_PHONE_NO);
        if (!userId.equals(null)) {
            activity_login_atv_phone.setText(SharedPreferencesManager.getStringSetting(activity, AFCHealthConstants.LOGIN_USER_PHONE_NO));
        }
        activity_login_et_password = (EditText) findViewById(R.id.activity_login_et_password);
        activity_login_et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_ACTION_GO) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        activity_login_btn_sign_in = (Button) findViewById(R.id.activity_login_btn_sign_in);
        activity_login_btn_sign_in.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        activity_login_tv_forgot_password = (TextView) findViewById(R.id.activity_login_tv_forgot_password);
        activity_login_tv_sign_up = (TextView) findViewById(R.id.activity_login_tv_sign_up);
        activity_login_tv_sign_up.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int adCounter = SharedPreferencesManager.getIntegerSetting(LoginActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                SharedPreferencesManager.setIntegerSetting(LoginActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                startActivityForResult(new Intent(activity, RegistrationActivity.class), 10);
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
            }
        });

        activity_login_tv_forgot_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.hideSoftInputMode(LoginActivity.this, activity_login_btn_sign_in);
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(getResources().getString(R.string.forgot_password_text_title));
                builder.setMessage(getResources().getString(R.string.forgot_password_text_description));
                builder.setPositiveButton("OK", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
//                        attemptPasswordReset();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case Activity.RESULT_OK:
                switch (requestCode) {
                    case 10:
                        setResult(Activity.RESULT_OK);
                        exitThisWithAnimation();
                        break;
                }
                break;
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        CommonUtils.hideSoftInputMode(LoginActivity.this, activity_login_btn_sign_in);
        // Reset errors.
        activity_login_atv_phone.setError(null);
        activity_login_et_password.setError(null);

        // Check for a valid phone number.
        String userPhoneNo = activity_login_atv_phone.getText().toString().trim();
        String phoneNoWithCode = userPhoneNo.contains("+88") ? userPhoneNo : "+88" + userPhoneNo;
        if (!CommonUtils.validateUserPhoneNo(LoginActivity.this, userPhoneNo, phoneNoWithCode, activity_login_atv_phone)) {
            return;
        }

        // Check for a valid password.
        String userPassword = activity_login_et_password.getText().toString().trim();
        if (!CommonUtils.validateUserPassword(LoginActivity.this, userPassword, activity_login_et_password)) {
            return;
        }

        requestLogin(userPhoneNo, userPassword);
    }

    /**
     * function to verify login details in mysql db
     */
    private void requestLogin(final String phoneNo, final String password) {
        // Tag used to cancel the request
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.MAIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ShowLog.e(TAG, "Login Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean error = jObj.getBoolean(KEY_ERROR);
                    String message = jObj.getString(KEY_MESSAGE);
                    if (error) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle(getResources().getString(R.string.login_error));
                        builder.setMessage(message);
                        builder.setPositiveButton("OK", null);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    } else {
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_FULL_NAME, jObj.getJSONObject("data").getString(LOGIN_USER_FULL_NAME));
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_PHONE_NO, jObj.getJSONObject("data").getString(LOGIN_USER_PHONE_NO));
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_GENDER, jObj.getJSONObject("data").getString(LOGIN_USER_GENDER));
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_PASSWORD, password);
                        SharedPreferencesManager.setStringSetting(activity, LOGIN_USER_ADDRESS, jObj.getJSONObject("data").getString(LOGIN_USER_ADDRESS));
                        SharedPreferencesManager.setBooleanSetting(activity, USER_IS_LOGIN, true);
                        setResult(Activity.RESULT_OK);
                        exitThisWithAnimation();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    ShowLog.d("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }

                if (error instanceof TimeoutError) {
                    ShowLog.e("Volley", "TimeoutError");
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
                    ShowLog.e("Volley", "ServerError");
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
                params.put(getString(R.string.api_param_62), AppConfig.LOGIN_URL);
                params.put(getString(R.string.api_param_63), phoneNo);
                params.put(getString(R.string.api_param_64), password);
                return params;
            }
           /* @Override
            public RetryPolicy getRetryPolicy() {
                RetryPolicy retryPolicy = new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return retryPolicy;
            }*/
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                AFCHealthConstants.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        exitThisWithAnimation();
    }

/**
 * Attempts to sign in or register the account specified by the login form.
 * If there are form errors (invalid email, missing fields, etc.), the
 * errors are presented and no actual login attempt is made.
 */
//    private void attemptPasswordReset() {
//        CommonUtils.hideSoftInputMode(LoginActivity.this, activity_login_btn_sign_in);
//        activity_login_atv_phone.setError(null);
//
//        // Store values at the time of the login attempt.
//        String phoneNo = activity_login_atv_phone.getText().toString();
//        String phoneNoWithCode = "+88" + phoneNo;
//        emailAddress = phoneNo;
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid phone number.
//        if (TextUtils.isEmpty(phoneNo)) {
//            activity_login_atv_phone.setError(getString(R.string.error_field_required));
//            activity_login_atv_phone.requestFocus();
//            focusView = activity_login_atv_phone;
//            cancel = true;
//        } else if (!CommonUtils.isPhoneNoValid(phoneNoWithCode)) {
//            activity_login_atv_phone.setError(getString(R.string.error_invalid_phone));
//            activity_login_atv_phone.requestFocus();
//            focusView = activity_login_atv_phone;
//            cancel = true;
//        }
//
//        if (cancel) {
//            focusView.requestFocus();
//        } else {
//            checkPasswordReset(phoneNo);
//        }
//    }
//
//    /**
//     * function to verify login details in mysql db
//     */
//    private void checkPasswordReset(final String userId) {
//        // Tag used to cancel the request
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.FORGOT_PASSWORD_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                ShowLog.d(TAG, "Response: " + response.toString());
//                try {
//                    JSONObject jObj = new JSONObject(response.toString());
//                    int code = jObj.getInt(KEY_CODE);
//                    String message = jObj.getString(KEY_MESSAGE);
//                    if (code == HttpCode.RESPONSE_OK) {
//                        if (jObj.has(KEY_DATA)) {
//                            JSONObject jsonObject = jObj.getJSONObject(KEY_DATA);
//                            String userTempPin = jsonObject.getString(KEY_DOCTOR_PIN);
//                            SharedPreferencesManager.setStringSetting(activity, AFCHealthConstants.USER_PIN, userTempPin);
//                            SharedPreferencesManager.setStringSetting(activity, AFCHealthConstants.LOGIN_USER_ID, userId);
//                            Intent i = new Intent(activity, PinVerifyActivity.class);
//                            startActivity(i);
//                            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                        }
//                    } else {
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
//                        builder.setTitle(getResources().getString(R.string.title_not_success));
//                        builder.setMessage(message);
//                        builder.setPositiveButton("OK", null);
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        builder.show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // ShowLog.e(TAG, "Error: " + error.getMessage());
//                if (error instanceof NoConnectionError) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
//                    builder.setTitle(getResources().getString(R.string.network_connection_disabled_title));
//                    builder.setMessage(getResources().getString(R.string.network_connection_disabled_content));
//                    builder.setPositiveButton("OK", null);
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//                    builder.show();
//                    return;
//                }
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put(getString(R.string.api_param_57), userId);
//                return params;
//            }
//        };
//        strReq.setRetryPolicy(new DefaultRetryPolicy(
//                LaunchScreenActivity.SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(strReq);
//    }
}

