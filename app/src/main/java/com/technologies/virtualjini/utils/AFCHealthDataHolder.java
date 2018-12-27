/**
 * BJIT CONFIDENTIAL                                                 *
 * Copyright 2014,2015 BJIT Group                                *
 *
 * @file KyunkuruDataHolder.java
 * <p>
 * This is the class which contains all the values which we need in app.
 * @file KyunkuruDataHolder.java
 * <p>
 * This is the class which contains all the values which we need in app.
 * @file KyunkuruDataHolder.java
 * <p>
 * This is the class which contains all the values which we need in app.
 */
/**
 * @file KyunkuruDataHolder.java
 *
 *       This is the class which contains all the values which we need in app.
 */

package com.technologies.virtualjini.utils;

import android.content.Context;

public class AFCHealthDataHolder {

    public static AFCHealthDataHolder mAFCHealthDataHolder;
    /** The m context. */
    private Context mProfileImageContext;
    /** The m last iamge id. */
    private int mLastIamgeId = -1;

    /**
     * Instantiates a new kyunkuru data holder.
     */
    private AFCHealthDataHolder() {
    }

    /**
     * Gets the single instance of KyunkuruDataHolder.
     *
     * @return single instance of KyunkuruDataHolder
     */
    public static AFCHealthDataHolder getInstance() {
        if (mAFCHealthDataHolder == null) {
            mAFCHealthDataHolder = new AFCHealthDataHolder();
        }
        return mAFCHealthDataHolder;
    }

    /**
     * Destroy instance.
     */
    public static void destroyInstance() {
        mAFCHealthDataHolder = null;
    }

    /**
     * @return the mContext
     */
    public Context getProfileImageContext() {
        return mProfileImageContext;
    }

    /**
     * @param mContext
     *            the mContext to set
     */
    public void setProfileImageContext(Context mContext) {
        this.mProfileImageContext = mContext;
    }

    /**
     * @return the mLastIamgeId
     */
    public int getLastIamgeId() {
        return mLastIamgeId;
    }

    /**
     * @param mLastIamgeId
     *            the mLastIamgeId to set
     */
    public void setLastIamgeId(int mLastIamgeId) {
        this.mLastIamgeId = mLastIamgeId;
    }
}
