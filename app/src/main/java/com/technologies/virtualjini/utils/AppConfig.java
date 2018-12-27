package com.technologies.virtualjini.utils;

import android.content.Context;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.volley.CheckBaseURL;

/**
 * Created by Arup on 8/29/2016.
 */
public class AppConfig {
    private static final Context context = AppController.getContext();
    public static final String BASE_URL = new CheckBaseURL().URL();
    public static final String BASE_URL_API = context.getString(R.string.base_api);
    public static final String MAIN_URL = BASE_URL + "/" + BASE_URL_API + "/" ;
    public static final String LOGIN_URL = context.getString(R.string.api_route_22);
    public static final String REGISTRATION_URL = context.getString(R.string.api_route_12);
//    public static final String FORGOT_PASSWORD_URL = MAIN_URL + context.getString(R.string.api_route_11);
//    public static final String RESEND_VERIFICATION_PIN = MAIN_URL + context.getString(R.string.api_route_34);                 //no token
//    public static final String RESET_PASSWORD_URL = MAIN_URL + context.getString(R.string.api_route_23);                   //no token
    public static final String GET_ALL_POST = context.getString(R.string.api_route_51);                                    //no token
    public static final String INSERT_URL = context.getString(R.string.api_route_15);
    public static final String EDIT_URL = context.getString(R.string.api_route_16);
    public static final String GET_MY_POST = context.getString(R.string.api_route_17);
    public static final String GET_MY_WISH_LIST = context.getString(R.string.api_route_21);
    public static final String ADD_TO_WISH_LIST = context.getString(R.string.api_route_18);
    public static final String REMOVE_FROM_WISH_LIST = context.getString(R.string.api_route_19);
    public static final String DELETE_MY_POST = context.getString(R.string.api_route_20);
}
