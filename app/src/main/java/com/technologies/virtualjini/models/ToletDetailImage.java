package com.technologies.virtualjini.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ToletDetailImage implements Parcelable {

    @SerializedName("imge_url")
    @Expose
    private String imgeUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public ToletDetailImage() {
    }

    /**
     *
     * @param imgeUrl
     */
    public ToletDetailImage(String imgeUrl) {
        super();
        this.imgeUrl = imgeUrl;
    }

    public String getImgeUrl() {
        return imgeUrl;
    }

    public void setImgeUrl(String imgeUrl) {
        this.imgeUrl = imgeUrl;
    }

    public ToletDetailImage withImgeUrl(String imgeUrl) {
        this.imgeUrl = imgeUrl;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("imgeUrl", imgeUrl).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(imgeUrl).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ToletDetailImage) == false) {
            return false;
        }
        ToletDetailImage rhs = ((ToletDetailImage) other);
        return new EqualsBuilder().append(imgeUrl, rhs.imgeUrl).isEquals();
    }


    protected ToletDetailImage(Parcel in) {
        imgeUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgeUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ToletDetailImage> CREATOR = new Parcelable.Creator<ToletDetailImage>() {
        @Override
        public ToletDetailImage createFromParcel(Parcel in) {
            return new ToletDetailImage(in);
        }

        @Override
        public ToletDetailImage[] newArray(int size) {
            return new ToletDetailImage[size];
        }
    };
}