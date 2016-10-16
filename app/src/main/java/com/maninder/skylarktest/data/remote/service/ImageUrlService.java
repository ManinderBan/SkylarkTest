package com.maninder.skylarktest.data.remote.service;

import com.maninder.skylarktest.setcontents.model.ImageUrl;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Maninder on 13/10/16.
 */

public interface ImageUrlService {

    @GET
    Observable<ImageUrl> getImageUrl(@Url String imageRequest);
}
