package com.maninder.skylarktest.episode;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.episode.usecase.GetAssetsInfo;
import com.maninder.skylarktest.episode.usecase.GetEpisodeInfo;
import com.maninder.skylarktest.episode.usecase.SetInfo;
import com.maninder.skylarktest.threading.UseCase;
import com.maninder.skylarktest.threading.UseCaseHandler;

/**
 * Created by Maninder on 14/10/16.
 */

/**
 * This is the Presenter and create an communication between UseCase and View.
 * This class listen the user actions from the UI {@link EpisodeFragment}, retrieves the data from UseCase
 * and updates the UI as required
 */
public class EpisodePresenter implements EpisodeContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;
    private final EpisodeContract.View mView;
    private final GetEpisodeInfo mGetEpisodeInfo;
    private final GetAssetsInfo mGetAssetsInfo;
    private final SetInfo mSetInfo;

    /**
     * @param view           Represent the current UI and is required to communicate with UseCase
     * @param useCaseHandler Is required if we want to run the UseCase in different Thread, in this way we don't impact user usability
     * @param getEpisodeInfo Usecase that allow to retrieve the Episode Info from {@link com.maninder.skylarktest.data.SkylarkRepository}
     * @param getAssetsInfo  Usecase that allow to retrieve the Episode asset from {@link com.maninder.skylarktest.data.SkylarkRepository}
     * @param setInfo        UseCase that allow to retrieve the Set information form {@link com.maninder.skylarktest.data.SkylarkRepository}
     */
    public EpisodePresenter(@NonNull EpisodeContract.View view,
                            @NonNull UseCaseHandler useCaseHandler, @NonNull GetEpisodeInfo getEpisodeInfo,
                            @NonNull GetAssetsInfo getAssetsInfo, @NonNull SetInfo setInfo) {
        mUseCaseHandler = useCaseHandler;
        mGetEpisodeInfo = getEpisodeInfo;
        mGetAssetsInfo = getAssetsInfo;
        mSetInfo = setInfo;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    /**
     * Load the Episode Info from repository
     * @param episodeURL URL Episode required to get the right information
     */

    @Override
    public void loadEpisode(@NonNull String episodeURL) {
        GetEpisodeInfo.RequestValue requestValue = new GetEpisodeInfo.RequestValue(episodeURL);
        mUseCaseHandler.execute(mGetEpisodeInfo, requestValue, new UseCase.UseCaseCallback<GetEpisodeInfo.ResponseValue>() {
            @Override
            public void onSuccess(GetEpisodeInfo.ResponseValue response) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showEpisode(response.getmEpisode());
            }

            @Override
            public void onError() {
                processUI(false, true);
            }
        });
    }

    /**
     * Get the Episode information
     * @param episodeURL  required to get the Episode info
     */
    @Override
    public void loadAssetEpisode(@NonNull String episodeURL) {
        GetAssetsInfo.RequestValue requestValue = new GetAssetsInfo.RequestValue(episodeURL);
        mUseCaseHandler.execute(mGetAssetsInfo, requestValue, new UseCase.UseCaseCallback<GetAssetsInfo.ResponseValue>() {
            @Override
            public void onSuccess(GetAssetsInfo.ResponseValue response) {
                if (!mView.isActive()) {
                    return;
                }
                processUI(false, false);
                mView.showAssetEpisode(response.getAssetEpisode());
            }

            @Override
            public void onError() {
                processUI(false, true);
            }
        });
    }

    /**
     * Get the Set information
     * @param self rquired to identify the right Set
     */
    @Override
    public void loadSet(String self) {
        SetInfo.RequestValue requestValue = new SetInfo.RequestValue(self);
        mUseCaseHandler.execute(mSetInfo, requestValue, new UseCase.UseCaseCallback<SetInfo.ResponseValue>() {
            @Override
            public void onSuccess(SetInfo.ResponseValue response) {
                if (!mView.isActive()) {
                    return;
                }
                processUI(false, false);
                mView.showSet(response.getSet());
            }

            @Override
            public void onError() {
                processUI(false, true);
            }
        });
    }

    private void processUI(boolean indicator, boolean error) {
        mView.setLoadingIndicator(indicator);
        mView.setLoadingError(error);
    }
}
