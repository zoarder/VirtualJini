/**
 * BJIT CONFIDENTIAL                                                 *
 * Copyright 2014,2015 BJIT Group                                *
 *
 * @file MediaObject.java
 * <p>
 * This is the model class of gallery media.
 * @file MediaObject.java
 * <p>
 * This is the model class of gallery media.
 * @file MediaObject.java
 * <p>
 * This is the model class of gallery media.
 */
/**
 * @file MediaObject.java
 *
 *       This is the model class of gallery media.
 */
package com.technologies.virtualjini.imagepicker;

import android.net.Uri;

/**
 * The Class MediaObject.
 *
 * @author Chinmoy Debnath
 */
public class MediaObject implements Comparable<MediaObject> {

    private int id;
    private String path;
    private String thumbPath;

    public MediaObject(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getMediaUri() {
        return Uri.parse(path);
    }

    /**
     * @return the thumbPath
     */
    public String getThumbPath() {
        return thumbPath;
    }

    /**
     * @param thumbPath
     *            the thumbPath to set
     */
    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(MediaObject arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

}
