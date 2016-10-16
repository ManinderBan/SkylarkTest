package com.maninder.skylarktest.data.remote.service;

import com.maninder.skylarktest.data.remote.model.AssetEpisode;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Maninder on 15/10/16.
 */

public interface AssetEpisodeService {

    @GET
    Observable<AssetEpisode> getAssetEpisode(@Url String episodeURL);
}
