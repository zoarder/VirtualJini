package com.technologies.virtualjini.volley;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.utils.ShowLog;
import com.technologies.virtualjini.utils.StringUtils;

import org.json.JSONObject;

/**
 * Created by Suleiman on 30-04-2015.
 */
public class VolleyUtils {

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }

    public static boolean showVolleyResponseError(final Activity activity, VolleyError volleyError, final boolean isFinishActivity) {
        try {
            NetworkResponse networkResponse = volleyError.networkResponse;
            if (networkResponse != null) {
                ShowLog.e("Error", "Error. HTTP Status Code:" + networkResponse.statusCode);
            }
            String title = "", errorMsg = "";
            if (volleyError instanceof TimeoutError) {
                ShowLog.e("Error", "TimeoutError");
                title = activity.getResources().getString(R.string.network_connection_timeout_error);
                errorMsg = activity.getResources().getString(R.string.network_connection_timeout_error_msg);
            } else if (volleyError instanceof NoConnectionError) {
                ShowLog.e("Error", "NoConnectionError");
                title = activity.getResources().getString(R.string.network_connection_disabled_title);
                errorMsg = activity.getResources().getString(R.string.network_connection_disabled_content);
            } else if (volleyError instanceof AuthFailureError) {
                ShowLog.e("Error", "AuthFailureError");
                title = activity.getResources().getString(R.string.network_connection_auth_error);
            } else if (volleyError instanceof ServerError) {
                ShowLog.e("Error", "ServerError");
                title = activity.getResources().getString(R.string.network_connection_server_error);
                errorMsg = activity.getResources().getString(R.string.network_connection_server_error_msg);
            } else if (volleyError instanceof NetworkError) {
                ShowLog.e("Error", "NetworkError");
                title = activity.getResources().getString(R.string.network_connection_network_error);
            } else if (volleyError instanceof ParseError) {
                ShowLog.e("Error", "ParseError");
                title = activity.getResources().getString(R.string.network_connection_parse_error);
            } else {
                ShowLog.e("Error", "UnknownError");
                title = activity.getResources().getString(R.string.network_connection_unknown_error);
            }


            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(title);
            builder.setCancelable(false);
            try {
                if (!StringUtils.isNullOrEmpty(errorMsg)) {
                    builder.setMessage(errorMsg);
                } else {
                    builder.setMessage(volleyError.getMessage());
                }
            } catch (NullPointerException ex) {
                builder.setMessage(volleyError.getMessage());
            }

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    if (isFinishActivity) {
                        dialog.dismiss();
                        activity.finish();
                        activity.overridePendingTransition(R.anim.trans_right_in,
                                R.anim.trans_right_out);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.show();

            //NotificationDialog notificationDialog = new NotificationDialog(activity, title, errorMsg, "Ok", true);
            //notificationDialog.show();

            return true;
        } catch (Exception ex) {
            ShowLog.e("Volley Error", ex.getMessage());
            return false;
        }
    }

    private static void finish(Activity activity, boolean isFinishActivity) {
        if (isFinishActivity) {
            activity.finish();
            activity.overridePendingTransition(R.anim.trans_right_in,
                    R.anim.trans_right_out);
        }
    }
}
