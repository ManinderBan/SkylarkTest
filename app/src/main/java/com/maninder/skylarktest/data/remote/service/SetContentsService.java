package com.maninder.skylarktest.data.remote.service;

import com.maninder.skylarktest.data.remote.model.SetList;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Maninder on 12/10/16.
 */

public interface SetContentsService {

    @GET("/api/sets/")
    Observable<SetList> getSetContents();
}
