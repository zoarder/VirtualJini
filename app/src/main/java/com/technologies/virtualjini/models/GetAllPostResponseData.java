package com.technologies.virtualjini.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetAllPostResponseData implements Parcelable {

    @SerializedName("total_tolet")
    @Expose
    private int totalTolet;
    @SerializedName("all_posts")
    @Expose
    private List<PostData> allPosts = new ArrayList<PostData>();

    /**
     * No args constructor for use in serialization
     *
     */
    public GetAllPostResponseData() {
    }

    /**
     *
     * @param allPosts
     * @param totalTolet
     */
    public GetAllPostResponseData(int totalTolet, List<PostData> allPosts) {
        super();
        this.totalTolet = totalTolet;
        this.allPosts = allPosts;
    }

    public int getTotalTolet() {
        return totalTolet;
    }

    public void setTotalTolet(int totalTolet) {
        this.totalTolet = totalTolet;
    }

    public GetAllPostResponseData withTotalTolet(int totalTolet) {
        this.totalTolet = totalTolet;
        return this;
    }

    public List<PostData> getAllPosts() {
        return allPosts;
    }

    public void setAllPosts(List<PostData> allPosts) {
        this.allPosts = allPosts;
    }

    public GetAllPostResponseData withAllPosts(List<PostData> allPosts) {
        this.allPosts = allPosts;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("totalTolet", totalTolet).append("allPosts", allPosts).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(allPosts).append(totalTolet).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetAllPostResponseData) == false) {
            return false;
        }
        GetAllPostResponseData rhs = ((GetAllPostResponseData) other);
        return new EqualsBuilder().append(allPosts, rhs.allPosts).append(totalTolet, rhs.totalTolet).isEquals();
    }


    protected GetAllPostResponseData(Parcel in) {
        totalTolet = in.readInt();
        if (in.readByte() == 0x01) {
            allPosts = new ArrayList<PostData>();
            in.readList(allPosts, PostData.class.getClassLoader());
        } else {
            allPosts = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalTolet);
        if (allPosts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(allPosts);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GetAllPostResponseData> CREATOR = new Parcelable.Creator<GetAllPostResponseData>() {
        @Override
        public GetAllPostResponseData createFromParcel(Parcel in) {
            return new GetAllPostResponseData(in);
        }

        @Override
        public GetAllPostResponseData[] newArray(int size) {
            return new GetAllPostResponseData[size];
        }
    };
}