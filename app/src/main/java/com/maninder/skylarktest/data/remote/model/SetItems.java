package com.maninder.skylarktest.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maninder on 14/10/16.
 */

public class SetItems implements Parcelable {

    @SerializedName("uid")
    public String uid;

    @SerializedName("content_url")
    public String contentURL;

    @SerializedName("content_type")
    public String contentType;

    @SerializedName("heading")
    public String heading;

    /**
     * Parcelable used to save and load data
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    private SetItems(Parcel in) {
        uid = in.readString();
        contentURL = in.readString();
        contentType = in.readString();
        heading = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(contentURL);
        dest.writeString(contentType);
        dest.writeString(heading);
    }

    public static final Parcelable.Creator<SetItems> CREATOR
            = new Parcelable.Creator<SetItems>() {
        public SetItems createFromParcel(Parcel in) {
            return new SetItems(in);
        }

        public SetItems[] newArray(int size) {
            return new SetItems[size];
        }
    };

}
