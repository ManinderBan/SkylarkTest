package com.maninder.skylarktest;

import android.content.Context;

import com.maninder.skylarktest.data.Local;
import com.maninder.skylarktest.data.Remote;
import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.local.SkylarkLocalDataSource;
import com.maninder.skylarktest.data.remote.SkylarkRemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maninder on 26/10/16.
 */

/**
 * This is used by Dagger to inject the required arguments into the {@link com.maninder.skylarktest.data.SkylarkRepository}.
 */
@Module
public class SkylarkRepositoryModule {

    @Singleton
    @Provides
    @Remote
    SkylarkDataSource provideSkylarkRemoteDataSource() {
//        return new FakeSkylarkRemoteDataSource();
        return new SkylarkRemoteDataSource();
    }

    @Singleton
    @Provides
    @Local
    SkylarkDataSource provideSkylarkLocalDataSource(Context context) {
        return new SkylarkLocalDataSource(context);
    }


}
