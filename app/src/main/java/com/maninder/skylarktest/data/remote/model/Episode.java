package com.maninder.skylarktest.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Episode Model
 */
public class Episode {
    //Important--> title and Items[0]

    @SerializedName("subtitle")
    public String subtitle;

    @SerializedName("uid")
    public String uid;

    @SerializedName("schedule_urls")
    public String scheduleUrls;

    @SerializedName("image_urls")
    public List<String> imageUrls;

    @SerializedName("publish_on")
    public String publishOn;

    @SerializedName("schedule_url")
    public String scheduleUrl;

    @SerializedName("episode_number")
    public int episodeNumber;

    @SerializedName("slug")
    public String slug;

    @SerializedName("version_number")
    public String versionNumber;

    @SerializedName("modified_by")
    public String modifiedBy;

    @SerializedName("title")
    public String title;

    @SerializedName("items")
    public List<String> items;

    @SerializedName("self")
    public String self;

    @SerializedName("created")
    public String created;

    @SerializedName("modified")
    public String modified;

    @SerializedName("created_by")
    public String createdBy;

    @SerializedName("synopsis")
    public String synopsis;

    public Episode() {
    }

    public Episode(String created) {
        this.created = created;

    }
}
