/**
 * BJIT CONFIDENTIAL                                                 *
 * Copyright 2014,2015 BJIT Group                                *
 *
 * @file Utils.java
 * <p>
 * This is the Utils class of gallery media.
 * @file Utils.java
 * <p>
 * This is the Utils class of gallery media.
 * @file Utils.java
 * <p>
 * This is the Utils class of gallery media.
 */
/**
 * @file Utils.java
 *
 *       This is the Utils class of gallery media.
 */
package com.technologies.virtualjini.imagepicker;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import java.util.ArrayList;
import java.util.List;


/**
 * Class containing some static utility methods.
 */
public class Utils {
    private Utils() {
    }

    ;

    @TargetApi(VERSION_CODES.HONEYCOMB)
    public static void enableStrictMode() {
        /*if (Utils.hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyLog();
			StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyLog();

			if (Utils.hasHoneycomb()) {
				threadPolicyBuilder.penaltyFlashScreen();
				vmPolicyBuilder.setClassInstanceLimit(
						ImagePickerActivity.class, 1);
			}
			StrictMode.setThreadPolicy(threadPolicyBuilder.build());
			StrictMode.setVmPolicy(vmPolicyBuilder.build());
		}*/
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }

    public static List<MediaObject> extractMediaList(Cursor cursor,
                                                     String mediaType) {
        List<MediaObject> list = new ArrayList<MediaObject>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String filePath = cursor.getString(1);
                MediaObject mediaObject = new MediaObject(cursor.getInt(0),
                        filePath);
                list.add(mediaObject);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
