package com.maninder.skylarktest.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Set Item Model
 */
public class Set implements Parcelable {
    @SerializedName("uid")
    public String uid;
    @SerializedName("schedule_urls")
    public List<String> scheduleUrls;
    @SerializedName("quoter")
    public String quoter;
    @SerializedName("featured")
    public boolean featured;
    @SerializedName("cached_film_count")
    public int cachedFilmCount;
    @SerializedName("modified_by")
    public String modifiedBy;
    @SerializedName("title")
    public String title;
    @SerializedName("self")
    public String self;
    @SerializedName("created_by")
    public String createdBy;
    @SerializedName("has_price")
    public String hasPrice;
    @SerializedName("film_count")
    public int filmCount;
    @SerializedName("body")
    public String body;
    @SerializedName("quote")
    public String quote;
    @SerializedName("formatted_body")
    public String formattedBody;
    @SerializedName("image_urls")
    public List<String> imageUrls;
    @SerializedName("schedule_url")
    public String scheduleUrl;
    @SerializedName("version_number")
    public int versionNumber;
    @SerializedName("created")
    public String created;
    @SerializedName("items")
    public List<SetItems> items;
    @SerializedName("modified")
    public String modified;
    @SerializedName("summary")
    public String summary;
    @SerializedName("ends_on")
    public String endsOn;

    public List<String> imageDownloadUrl;

    /**
     * Parcelable used to save and load data
     */
    @Override
    public int describeContents() {
        return 0;
    }

    public Set() {
    }

    public Set(String self) {
        this.self = self;
    }

    @SuppressWarnings("unchecked")
    private Set(Parcel in) {
        uid = in.readString();
        in.readStringList(scheduleUrls);
        quoter = in.readString();
        featured = in.readByte() != 0;
        cachedFilmCount = in.readInt();
        modifiedBy = in.readString();
        title = in.readString();
        self = in.readString();
        createdBy = in.readString();
        hasPrice = in.readString();
        filmCount = in.readInt();
        body = in.readString();
        quote = in.readString();
        formattedBody = in.readString();
        in.readStringList(imageUrls);
        scheduleUrl = in.readString();
        versionNumber = in.readInt();
        created = in.readString();
        in.readTypedList(items, SetItems.CREATOR);
        modified = in.readString();
        summary = in.readString();
        endsOn = in.readString();
        in.readStringList(imageDownloadUrl);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeStringList(scheduleUrls);
        dest.writeString(quoter);
        dest.writeByte((byte) (featured ? 1 : 0));
        dest.writeInt(cachedFilmCount);
        dest.writeString(modifiedBy);
        dest.writeString(title);
        dest.writeString(self);
        dest.writeString(createdBy);
        dest.writeString(hasPrice);
        dest.writeInt(filmCount);
        dest.writeString(body);
        dest.writeString(quote);
        dest.writeString(formattedBody);
        dest.writeStringList(imageUrls);
        dest.writeString(scheduleUrl);
        dest.writeInt(versionNumber);
        dest.writeString(created);
        dest.writeTypedList(items);
        dest.writeString(modified);
        dest.writeString(summary);
        dest.writeString(endsOn);
        dest.writeStringList(imageDownloadUrl);

    }

    public static final Parcelable.Creator<Set> CREATOR
            = new Parcelable.Creator<Set>() {
        public Set createFromParcel(Parcel in) {
            return new Set(in);
        }

        public Set[] newArray(int size) {
            return new Set[size];
        }
    };

}
