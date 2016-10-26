package com.maninder.skylarktest.episode.injection;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.episode.EpisodeContract;
import com.maninder.skylarktest.episode.usecase.GetAssetsInfo;
import com.maninder.skylarktest.episode.usecase.GetEpisodeInfo;
import com.maninder.skylarktest.episode.usecase.SetInfo;
import com.maninder.skylarktest.threading.UseCaseHandler;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maninder on 26/10/16.
 */

@Module
public class EpisodeModule {
    private final EpisodeContract.View mView;
    private final SkylarkRepository mSkylarkRepository;

    public EpisodeModule(@NonNull EpisodeContract.View view, @NonNull SkylarkRepository skylarkRepository) {
        mView = view;
        mSkylarkRepository = skylarkRepository;
    }

    @Provides
    EpisodeContract.View provideEpisodeView() {
        return mView;
    }

    @Provides
    UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    @Provides
    GetEpisodeInfo provideGetEpisodeInfo() {
        return new GetEpisodeInfo(mSkylarkRepository);
    }

    @Provides
    GetAssetsInfo provideGetAssetInfo() {
        return new GetAssetsInfo(mSkylarkRepository);
    }

    @Provides
    SetInfo provideSetInfo() {
        return new SetInfo(mSkylarkRepository);
    }
}
