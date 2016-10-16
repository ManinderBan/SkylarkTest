package com.maninder.skylarktest.episode.usecase;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.threading.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 14/10/16.
 */
/**
 * UseCase all wrote in Java. This is the business logic login code and allow to get Episode info from {@link SkylarkRepository}
 * Extend the UseCase in this way we can run in the background and communicate events to the upper layer using callback
 */
public class GetEpisodeInfo extends UseCase<GetEpisodeInfo.RequestValue, GetEpisodeInfo.ResponseValue> {

    private final SkylarkRepository mSkylarkRepository;

    public GetEpisodeInfo(@NonNull SkylarkRepository skylarkRepository) {
        mSkylarkRepository = checkNotNull(skylarkRepository);
    }
    /**
     * @param requestValues is the request to the {@link SkylarkRepository}. Hold the requested value.
     */
    @Override
    protected void executeUseCase(RequestValue requestValues) {
        mSkylarkRepository.getEpisode(requestValues.getEpiodeUrl(), new SkylarkDataSource.LoadEpisodeCallback() {
            @Override
            public void onEpisodeLoaded(Episode episode) {
                ResponseValue responseValue = new ResponseValue(episode);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class ResponseValue implements UseCase.ResponseValue {
        private Episode mEpisode;

        public ResponseValue(@NonNull Episode episode) {
            mEpisode = episode;
        }

        public Episode getmEpisode() {
            return mEpisode;
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
