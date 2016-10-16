package com.maninder.skylarktest.episode.usecase;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.data.remote.model.AssetEpisode;
import com.maninder.skylarktest.threading.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 15/10/16.
 */

/**
 * UseCase all wrote in Java. This is the business logic login code and allow to get Asset episode info from {@link SkylarkRepository}
 * Extend the UseCase in this way we can run in the background and communicate events to the upper layer using callback
 */
public class GetAssetsInfo extends UseCase<GetAssetsInfo.RequestValue, GetAssetsInfo.ResponseValue> {

    private final SkylarkRepository mSkylarkRepository;

    public GetAssetsInfo(@NonNull SkylarkRepository skylarkRepository) {
        mSkylarkRepository = checkNotNull(skylarkRepository);
    }

    /**
     * @param requestValues is the request to the {@link SkylarkRepository}. Hold the requested value.
     */
    @Override
    protected void executeUseCase(RequestValue requestValues) {
        mSkylarkRepository.gettAssetEpisode(requestValues.getEpiodeUrl(), new SkylarkDataSource.LoadAssetEpisodeCallback() {
            @Override
            public void onAssetLoaded(AssetEpisode assetEpisode) {
                ResponseValue responseValue = new ResponseValue(assetEpisode);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class ResponseValue implements UseCase.ResponseValue {
        private AssetEpisode mAssetEpisode;

        public ResponseValue(@NonNull AssetEpisode assetEpisode) {
            mAssetEpisode = assetEpisode;
        }

        public AssetEpisode getAssetEpisode() {
            return mAssetEpisode;
        }
    }

    public static class RequestValue implements UseCase.RequestValues {
        private String mEpiodeUrl;

        public RequestValue(@NonNull String epiodeUrl) {
            mEpiodeUrl = epiodeUrl;
        }

        public String getEpiodeUrl() {
            return mEpiodeUrl;
        }
    }

}
