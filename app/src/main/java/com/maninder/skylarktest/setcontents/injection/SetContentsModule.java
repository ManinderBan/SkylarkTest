package com.maninder.skylarktest.setcontents.injection;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.setcontents.SetContentsContract;
import com.maninder.skylarktest.setcontents.usecase.GetSetContents;
import com.maninder.skylarktest.setcontents.usecase.ImageURLRequest;
import com.maninder.skylarktest.threading.UseCaseHandler;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maninder on 26/10/16.
 */

@Module
public class SetContentsModule {

    private final SetContentsContract.View mView;
    private final SkylarkRepository mSkylarkRepository;

    public SetContentsModule(@NonNull SetContentsContract.View view, @NonNull SkylarkRepository skylarkRepository) {
        mView = view;
        mSkylarkRepository = skylarkRepository;
    }

    @Provides
    SetContentsContract.View provideSetContentsView() {
        return mView;
    }

    @Provides
    UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    @Provides
    GetSetContents provideGetSetContents() {
        return new GetSetContents(mSkylarkRepository);
    }

    @Provides
    ImageURLRequest provideImageURLRequest() {
        return new ImageURLRequest(mSkylarkRepository);
    }
}
