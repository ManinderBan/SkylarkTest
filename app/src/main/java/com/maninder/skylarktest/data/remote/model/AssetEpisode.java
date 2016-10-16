package com.maninder.skylarktest.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maninder on 15/10/16.
 */

/**
 * Asset Episode Model
 */
public class AssetEpisode {

    @SerializedName("max_devices")
    public String maxDevices;

    @SerializedName("image_urls")
    public List<String> imageUrls;

    @SerializedName("slug")
    public String slug;

    @SerializedName("subtitles")
    public String subtitles;

    @SerializedName("sound")
    public String sound;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("duration_in_seconds")
    public String durationSecond;

    @SerializedName("parent_url")
    public String parentUrl;

    @SerializedName("self")
    public String self;

}
