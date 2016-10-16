package com.maninder.skylarktest.data.remote.service;

import com.maninder.skylarktest.data.remote.model.Episode;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Maninder on 14/10/16.
 */

public interface EpisodeService {

    @GET
    Observable<Episode> getEpisodenfo(@Url String episodeRequest);
}
