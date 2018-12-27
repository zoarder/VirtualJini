package com.technologies.virtualjini.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class SharedPreferencesManager {
    private static final int KEY_MODE_PRIVATE = 0;
    private static final String PREFERENCES_NAME = "virtual_jini";
    // LogCat tag
    private static String TAG = SharedPreferencesManager.class.getSimpleName();
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private Editor mSharedPreferencesEditor;

    public SharedPreferencesManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCES_NAME, KEY_MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }


    public static boolean clearAllSession(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.clear();
        mSharedPreferencesEditor.commit();

        return true;
    }

	/*
     * Setting for register and unregister user
	 */

    /**
     * /** Set long value in shared preference
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setLongSetting(Context context, String key, long value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.putLong(key, value);
        mSharedPreferencesEditor.commit();

    }

    public static double getDoubleSetting(Context context, String key,
                                          double defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getFloat(key, (float) defaultValue);

    }

    public static void setDoubleSetting(Context context, String key,
                                        double value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.putFloat(key, (float) value);
        mSharedPreferencesEditor.commit();

    }

    public static long getLongSetting(Context context, String key,
                                      long defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getLong(key, defaultValue);

    }

    public static void setStringSetting(Context context, String key,
                                        String value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.putString(key, value);
        mSharedPreferencesEditor.commit();
    }

    public static void setStringSettingSet(Context context, String key,
                                           Set<String> value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.putStringSet(key, value);
        mSharedPreferencesEditor.commit();
    }

    /**
     * Set boolean value in shared preference
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setBooleanSetting(Context context, String key,
                                         boolean value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.putBoolean(key, value);
        mSharedPreferencesEditor.commit();

    }

    /**
     * Set int value in shared preference
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setIntegerSetting(Context context, String key, int value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.putInt(key, value);
        mSharedPreferencesEditor.commit();

    }

    /**
     * get value from shared preference
     *
     * @param context
     * @param key
     * @return string
     */
    public static String getStringSetting(Context context, String key) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString(key, "");

    }

    public static Set<String> getStringSetSetting(Context context, String key) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getStringSet(key, new HashSet<String>());
    }

    public static Set<String> getStringSetSetting(Context context, String key, Set<String> defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getStringSet(key, defaultValue);
    }

    public static String getStringSetting(Context context, String key, String defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString(key, defaultValue);

    }

    /**
     * get value from shared preference if not found return given default value
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return string
     */
    public static String getDefaultSettingIfValueNotFound(Context context,
                                                          String key, String defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString(key, defaultValue);

    }

    /**
     * get boolean value from shared preference if not found return given
     * default value
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return boolean
     */
    public static boolean getBooleanSetting(Context context, String key,
                                            boolean defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getBoolean(key, defaultValue);

    }

    /**
     * get integer value from shared preference if not found return given
     * default value
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return boolean
     */
    public static int getIntegerSetting(Context context, String key, int defaultValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(key, defaultValue);

    }

    /**
     * remove item from shared preference
     *
     * @param context
     * @param key
     */
    public static void removeSetting(Context context, String key) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.remove(key);
        mSharedPreferencesEditor.commit();
    }

    /***
     * get shared preference mSharedPreferencesEditor
     *
     * @param context
     * @return
     */
    public static Editor getEditor(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.edit();
    }

    public static void addToCounter(Context context, String key, int value) {

        int myValue = getCounter(context, key, 0) + value;
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mSharedPreferencesEditor = sp.edit();
        mSharedPreferencesEditor.putInt(key, myValue);
        mSharedPreferencesEditor.commit();

    }

    public static int getCounter(Context context, String key, int defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getInt(key, defaultValue);

    }

    public static boolean checkingAccessPermission(Context context, String key) {

        if (SharedPreferencesManager.getCounter(context, key, 0) < 5) {
            return true;
        } else {
            return false;
        }
    }

}
