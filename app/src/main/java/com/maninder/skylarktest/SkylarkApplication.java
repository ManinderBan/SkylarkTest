package com.maninder.skylarktest;

import android.app.Application;

import com.maninder.skylarktest.data.DaggerSkylarkRepositoryComponent;
import com.maninder.skylarktest.data.SkylarkRepositoryComponent;

/**
 * Created by Maninder on 26/10/16.
 */

/**
 * We create this class to create an singleton reference to the {@link SkylarkRepositoryComponent}
 * <p>
 * <p>
 * The Application is made of 3 Dagger Component, as follow:
 * {@link SkylarkRepositoryComponent}: the data (it encapsulate db and server data) <BR />
 * {@link com.maninder.skylarktest.setcontents.injection.SetContentsComponent}: showing the list of SetContents <BR />
 * {@link com.maninder.skylarktest.episode.injection.EpisodeComponent} showing the episode or Set information <BR />
 */
public class SkylarkApplication extends Application {

    private SkylarkRepositoryComponent mSkylarkRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mSkylarkRepositoryComponent = DaggerSkylarkRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public SkylarkRepositoryComponent getSkylarkRepositoryComponent() {
        return mSkylarkRepositoryComponent;
    }
}
