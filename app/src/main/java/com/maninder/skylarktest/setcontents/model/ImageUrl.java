package com.maninder.skylarktest.setcontents.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maninder on 13/10/16.
 */

/**
 * The Image URL model used to retrieve the Image information
 */
public class ImageUrl {

    @SerializedName("url")
    public String imageURl;

    @SerializedName("self")
    public String self;

    @SerializedName("content_url")
    public String contentURL;

    public int position;

    public ImageUrl(String url, String contentURL, String self) {
        this.imageURl = url;
        this.contentURL = contentURL;
        this.self = self;
    }

    public ImageUrl() {
    }
}
