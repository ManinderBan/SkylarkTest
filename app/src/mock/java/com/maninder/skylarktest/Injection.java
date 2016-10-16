package com.maninder.skylarktest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.FakeSkylarkRemoteDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.data.local.SkylarkLocalDataSource;
import com.maninder.skylarktest.data.remote.SkylarkRemoteDataSource;
import com.maninder.skylarktest.episode.usecase.GetAssetsInfo;
import com.maninder.skylarktest.episode.usecase.GetEpisodeInfo;
import com.maninder.skylarktest.episode.usecase.SetInfo;
import com.maninder.skylarktest.setcontents.usecase.GetSetContents;
import com.maninder.skylarktest.setcontents.usecase.ImageURLRequest;
import com.maninder.skylarktest.threading.UseCaseHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Enables injection of mock implementations for
 * {@link SkylarkRepository} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    /**
     * NOTE: Inject the {@link FakeSkylarkRemoteDataSource} before to test the Application
     *
     * @param context
     * @return
     */
    public static SkylarkRepository provideSkylarkRepository(@NonNull Context context) {
        checkNotNull(context);
        return SkylarkRepository.getINSTANCE(SkylarkLocalDataSource.getINSTANCE(context),
                SkylarkRemoteDataSource.getINSTANCE());
//        return SkylarkRepository.getINSTANCE(SkylarkLocalDataSource.getINSTANCE(context),
//                FakeSkylarkRemoteDataSource.getINSTANCE());
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static GetSetContents provideGetSetContents(@NonNull Context context) {
        return new GetSetContents(Injection.provideSkylarkRepository(context));
    }

    public static ImageURLRequest provideImageUrlRequest(@NonNull Context context) {
        return new ImageURLRequest(Injection.provideSkylarkRepository(context));
    }

    public static GetEpisodeInfo provideGetEpisodeInfo(@NonNull Context context) {
        return new GetEpisodeInfo(Injection.provideSkylarkRepository(context));
    }

    public static GetAssetsInfo provideGetAssetInfo(@NonNull Context context) {
        return new GetAssetsInfo(Injection.provideSkylarkRepository(context));
    }

    public static SetInfo provideSetInfo(@NonNull Context context) {
        return new SetInfo(Injection.provideSkylarkRepository(context));
    }


}
