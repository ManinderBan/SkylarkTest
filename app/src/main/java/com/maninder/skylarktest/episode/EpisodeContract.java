package com.maninder.skylarktest.episode;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.BasePresenter;
import com.maninder.skylarktest.BaseView;
import com.maninder.skylarktest.data.remote.model.AssetEpisode;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.data.remote.model.Set;

/**
 * Created by Maninder on 14/10/16.
 */
/**
 * This specifies the contract between the view and the presenter.
 */
public interface EpisodeContract {

    interface View extends BaseView<Presenter> {
        void showEpisode(Episode episode);

        void showAssetEpisode(AssetEpisode assetEpisode);

        void showSet(Set set);

        boolean isActive();

        void setLoadingIndicator(boolean active);

        void setLoadingError(boolean active);

    }

    interface Presenter extends BasePresenter {
        void loadEpisode(@NonNull String episodeURL);

        void loadAssetEpisode(@NonNull String episodeURL);

        void loadSet(@NonNull String self);
    }

}
