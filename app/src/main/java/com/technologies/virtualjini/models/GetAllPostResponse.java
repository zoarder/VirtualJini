package com.technologies.virtualjini.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetAllPostResponse implements Parcelable {

    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetAllPostResponseData data;

    /**
     * No args constructor for use in serialization
     */
    public GetAllPostResponse() {
    }

    /**
     * @param message
     * @param error
     * @param data
     */
    public GetAllPostResponse(boolean error, String message, GetAllPostResponseData data) {
        super();
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public GetAllPostResponse withError(boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetAllPostResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public GetAllPostResponseData getData() {
        return data;
    }

    public void setData(GetAllPostResponseData data) {
        this.data = data;
    }

    public GetAllPostResponse withData(GetAllPostResponseData data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("error", error).append("message", message).append("data", data).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(message).append(error).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetAllPostResponse) == false) {
            return false;
        }
        GetAllPostResponse rhs = ((GetAllPostResponse) other);
        return new EqualsBuilder().append(message, rhs.message).append(error, rhs.error).append(data, rhs.data).isEquals();
    }


    protected GetAllPostResponse(Parcel in) {
        error = in.readByte() != 0x00;
        message = in.readString();
        data = (GetAllPostResponseData) in.readValue(GetAllPostResponseData.class.getClassLoader());
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
    public static final Parcelable.Creator<GetAllPostResponse> CREATOR = new Parcelable.Creator<GetAllPostResponse>() {
        @Override
        public GetAllPostResponse createFromParcel(Parcel in) {
            return new GetAllPostResponse(in);
        }

        @Override
        public GetAllPostResponse[] newArray(int size) {
            return new GetAllPostResponse[size];
        }
    };
}