package com.maninder.skylarktest;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maninder on 26/10/16.
 */

/**
 * Used to Pass the Context dependency to the
 * {@link com.maninder.skylarktest.data.SkylarkRepositoryComponent}.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
