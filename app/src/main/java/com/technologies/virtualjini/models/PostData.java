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

public class PostData implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tolet_id")
    @Expose
    private String toletId;
    @SerializedName("tolet_title")
    @Expose
    private String toletTitle;
    @SerializedName("tolet_uploader_id")
    @Expose
    private String toletUploaderId;
    @SerializedName("title_image")
    @Expose
    private String titleImage;
    @SerializedName("tolet_address")
    @Expose
    private String toletAddress;
    @SerializedName("tolet_rent")
    @Expose
    private String toletRent;
    @SerializedName("tolet_sqfeet")
    @Expose
    private String toletSqfeet;
    @SerializedName("tolet_bed_room")
    @Expose
    private String toletBedRoom;
    @SerializedName("tolet_bath_room")
    @Expose
    private String toletBathRoom;
    @SerializedName("tolet_car_parking")
    @Expose
    private String toletCarParking;
    @SerializedName("tolet_living_room")
    @Expose
    private String toletLivingRoom;
    @SerializedName("tolet_dyning_room")
    @Expose
    private String toletDyningRoom;
    @SerializedName("tolet_status")
    @Expose
    private String toletStatus;
    @SerializedName("tolet_type")
    @Expose
    private String toletType;
    @SerializedName("tolet_from")
    @Expose
    private String toletFrom;
    @SerializedName("tolet_detail_images")
    @Expose
    private List<ToletDetailImage> toletDetailImages = new ArrayList<ToletDetailImage>();

    /**
     * No args constructor for use in serialization
     *
     */
    public PostData() {
    }

    /**
     *
     * @param toletFrom
     * @param toletStatus
     * @param toletDyningRoom
     * @param toletSqfeet
     * @param toletBathRoom
     * @param toletRent
     * @param toletId
     * @param toletAddress
     * @param toletType
     * @param toletTitle
     * @param toletUploaderId
     * @param toletCarParking
     * @param id
     * @param toletBedRoom
     * @param toletLivingRoom
     * @param toletDetailImages
     * @param titleImage
     */
    public PostData(String id, String toletId, String toletTitle, String toletUploaderId, String titleImage, String toletAddress, String toletRent, String toletSqfeet, String toletBedRoom, String toletBathRoom, String toletCarParking, String toletLivingRoom, String toletDyningRoom, String toletStatus, String toletType, String toletFrom, List<ToletDetailImage> toletDetailImages) {
        super();
        this.id = id;
        this.toletId = toletId;
        this.toletTitle = toletTitle;
        this.toletUploaderId = toletUploaderId;
        this.titleImage = titleImage;
        this.toletAddress = toletAddress;
        this.toletRent = toletRent;
        this.toletSqfeet = toletSqfeet;
        this.toletBedRoom = toletBedRoom;
        this.toletBathRoom = toletBathRoom;
        this.toletCarParking = toletCarParking;
        this.toletLivingRoom = toletLivingRoom;
        this.toletDyningRoom = toletDyningRoom;
        this.toletStatus = toletStatus;
        this.toletType = toletType;
        this.toletFrom = toletFrom;
        this.toletDetailImages = toletDetailImages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostData withId(String id) {
        this.id = id;
        return this;
    }

    public String getToletId() {
        return toletId;
    }

    public void setToletId(String toletId) {
        this.toletId = toletId;
    }

    public PostData withToletId(String toletId) {
        this.toletId = toletId;
        return this;
    }

    public String getToletTitle() {
        return toletTitle;
    }

    public void setToletTitle(String toletTitle) {
        this.toletTitle = toletTitle;
    }

    public PostData withToletTitle(String toletTitle) {
        this.toletTitle = toletTitle;
        return this;
    }

    public String getToletUploaderId() {
        return toletUploaderId;
    }

    public void setToletUploaderId(String toletUploaderId) {
        this.toletUploaderId = toletUploaderId;
    }

    public PostData withToletUploaderId(String toletUploaderId) {
        this.toletUploaderId = toletUploaderId;
        return this;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public PostData withTitleImage(String titleImage) {
        this.titleImage = titleImage;
        return this;
    }

    public String getToletAddress() {
        return toletAddress;
    }

    public void setToletAddress(String toletAddress) {
        this.toletAddress = toletAddress;
    }

    public PostData withToletAddress(String toletAddress) {
        this.toletAddress = toletAddress;
        return this;
    }

    public String getToletRent() {
        return toletRent;
    }

    public void setToletRent(String toletRent) {
        this.toletRent = toletRent;
    }

    public PostData withToletRent(String toletRent) {
        this.toletRent = toletRent;
        return this;
    }

    public String getToletSqfeet() {
        return toletSqfeet;
    }

    public void setToletSqfeet(String toletSqfeet) {
        this.toletSqfeet = toletSqfeet;
    }

    public PostData withToletSqfeet(String toletSqfeet) {
        this.toletSqfeet = toletSqfeet;
        return this;
    }

    public String getToletBedRoom() {
        return toletBedRoom;
    }

    public void setToletBedRoom(String toletBedRoom) {
        this.toletBedRoom = toletBedRoom;
    }

    public PostData withToletBedRoom(String toletBedRoom) {
        this.toletBedRoom = toletBedRoom;
        return this;
    }

    public String getToletBathRoom() {
        return toletBathRoom;
    }

    public void setToletBathRoom(String toletBathRoom) {
        this.toletBathRoom = toletBathRoom;
    }

    public PostData withToletBathRoom(String toletBathRoom) {
        this.toletBathRoom = toletBathRoom;
        return this;
    }

    public String getToletCarParking() {
        return toletCarParking;
    }

    public void setToletCarParking(String toletCarParking) {
        this.toletCarParking = toletCarParking;
    }

    public PostData withToletCarParking(String toletCarParking) {
        this.toletCarParking = toletCarParking;
        return this;
    }

    public String getToletLivingRoom() {
        return toletLivingRoom;
    }

    public void setToletLivingRoom(String toletLivingRoom) {
        this.toletLivingRoom = toletLivingRoom;
    }

    public PostData withToletLivingRoom(String toletLivingRoom) {
        this.toletLivingRoom = toletLivingRoom;
        return this;
    }

    public String getToletDyningRoom() {
        return toletDyningRoom;
    }

    public void setToletDyningRoom(String toletDyningRoom) {
        this.toletDyningRoom = toletDyningRoom;
    }

    public PostData withToletDyningRoom(String toletDyningRoom) {
        this.toletDyningRoom = toletDyningRoom;
        return this;
    }

    public String getToletStatus() {
        return toletStatus;
    }

    public void setToletStatus(String toletStatus) {
        this.toletStatus = toletStatus;
    }

    public PostData withToletStatus(String toletStatus) {
        this.toletStatus = toletStatus;
        return this;
    }

    public String getToletType() {
        return toletType;
    }

    public void setToletType(String toletType) {
        this.toletType = toletType;
    }

    public PostData withToletType(String toletType) {
        this.toletType = toletType;
        return this;
    }

    public String getToletFrom() {
        return toletFrom;
    }

    public void setToletFrom(String toletFrom) {
        this.toletFrom = toletFrom;
    }

    public PostData withToletFrom(String toletFrom) {
        this.toletFrom = toletFrom;
        return this;
    }

    public List<ToletDetailImage> getToletDetailImages() {
        return toletDetailImages;
    }

    public void setToletDetailImages(List<ToletDetailImage> toletDetailImages) {
        this.toletDetailImages = toletDetailImages;
    }

    public PostData withToletDetailImages(List<ToletDetailImage> toletDetailImages) {
        this.toletDetailImages = toletDetailImages;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("toletId", toletId).append("toletTitle", toletTitle).append("toletUploaderId", toletUploaderId).append("titleImage", titleImage).append("toletAddress", toletAddress).append("toletRent", toletRent).append("toletSqfeet", toletSqfeet).append("toletBedRoom", toletBedRoom).append("toletBathRoom", toletBathRoom).append("toletCarParking", toletCarParking).append("toletLivingRoom", toletLivingRoom).append("toletDyningRoom", toletDyningRoom).append("toletStatus", toletStatus).append("toletType", toletType).append("toletFrom", toletFrom).append("toletDetailImages", toletDetailImages).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(toletFrom).append(toletStatus).append(toletDyningRoom).append(toletBathRoom).append(toletSqfeet).append(toletRent).append(toletId).append(toletAddress).append(toletType).append(toletUploaderId).append(toletTitle).append(toletCarParking).append(toletBedRoom).append(id).append(toletLivingRoom).append(toletDetailImages).append(titleImage).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PostData) == false) {
            return false;
        }
        PostData rhs = ((PostData) other);
        return new EqualsBuilder().append(toletFrom, rhs.toletFrom).append(toletStatus, rhs.toletStatus).append(toletDyningRoom, rhs.toletDyningRoom).append(toletBathRoom, rhs.toletBathRoom).append(toletSqfeet, rhs.toletSqfeet).append(toletRent, rhs.toletRent).append(toletId, rhs.toletId).append(toletAddress, rhs.toletAddress).append(toletType, rhs.toletType).append(toletUploaderId, rhs.toletUploaderId).append(toletTitle, rhs.toletTitle).append(toletCarParking, rhs.toletCarParking).append(toletBedRoom, rhs.toletBedRoom).append(id, rhs.id).append(toletLivingRoom, rhs.toletLivingRoom).append(toletDetailImages, rhs.toletDetailImages).append(titleImage, rhs.titleImage).isEquals();
    }


    protected PostData(Parcel in) {
        id = in.readString();
        toletId = in.readString();
        toletTitle = in.readString();
        toletUploaderId = in.readString();
        titleImage = in.readString();
        toletAddress = in.readString();
        toletRent = in.readString();
        toletSqfeet = in.readString();
        toletBedRoom = in.readString();
        toletBathRoom = in.readString();
        toletCarParking = in.readString();
        toletLivingRoom = in.readString();
        toletDyningRoom = in.readString();
        toletStatus = in.readString();
        toletType = in.readString();
        toletFrom = in.readString();
        if (in.readByte() == 0x01) {
            toletDetailImages = new ArrayList<ToletDetailImage>();
            in.readList(toletDetailImages, ToletDetailImage.class.getClassLoader());
        } else {
            toletDetailImages = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(toletId);
        dest.writeString(toletTitle);
        dest.writeString(toletUploaderId);
        dest.writeString(titleImage);
        dest.writeString(toletAddress);
        dest.writeString(toletRent);
        dest.writeString(toletSqfeet);
        dest.writeString(toletBedRoom);
        dest.writeString(toletBathRoom);
        dest.writeString(toletCarParking);
        dest.writeString(toletLivingRoom);
        dest.writeString(toletDyningRoom);
        dest.writeString(toletStatus);
        dest.writeString(toletType);
        dest.writeString(toletFrom);
        if (toletDetailImages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(toletDetailImages);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostData> CREATOR = new Parcelable.Creator<PostData>() {
        @Override
        public PostData createFromParcel(Parcel in) {
            return new PostData(in);
        }

        @Override
        public PostData[] newArray(int size) {
            return new PostData[size];
        }
    };
}