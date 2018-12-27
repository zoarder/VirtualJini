package com.technologies.virtualjini.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddEditPostResponse implements Parcelable {

    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private PostData data;

    /**
     * No args constructor for use in serialization
     */
    public AddEditPostResponse() {
    }

    /**
     * @param error
     * @param data
     */
    public AddEditPostResponse(boolean error, String message, PostData data) {
        super();
        this.error = error;
        this.data = data;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public AddEditPostResponse withError(boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AddEditPostResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public PostData getData() {
        return data;
    }

    public void setData(PostData data) {
        this.data = data;
    }

    public AddEditPostResponse withData(PostData data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("error", error).append("message", message).append("data", data).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(error).append(message).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AddEditPostResponse) == false) {
            return false;
        }
        AddEditPostResponse rhs = ((AddEditPostResponse) other);
        return new EqualsBuilder().append(error, rhs.error).append(data, rhs.data).isEquals();
    }


    protected AddEditPostResponse(Parcel in) {
        error = in.readByte() != 0x00;
        message = in.readString();
        this.data = ((PostData) in.readValue((PostData.class.getClassLoader())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (error ? 0x01 : 0x00));
        dest.writeString(message);
        dest.writeValue(data);
    }

    @SuppressWarnings("unused")
    public static final Creator<AddEditPostResponse> CREATOR = new Creator<AddEditPostResponse>() {
        @Override
        public AddEditPostResponse createFromParcel(Parcel in) {
            return new AddEditPostResponse(in);
        }

        @Override
        public AddEditPostResponse[] newArray(int size) {
            return new AddEditPostResponse[size];
        }
    };
}