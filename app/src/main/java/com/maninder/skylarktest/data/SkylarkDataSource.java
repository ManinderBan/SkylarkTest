package com.maninder.skylarktest.data;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.remote.model.AssetEpisode;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.setcontents.model.ImageUrl;

import java.util.List;
import java.util.Map;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Main entry point for accessing tasks data.
 */
public interface SkylarkDataSource {

    interface LoadSetsCallback {
        void onSetsLoaded(List<Set> setList);

        void onDataNotAvailable();
    }

    interface LoadEpisodeCallback {
        void onEpisodeLoaded(Episode episode);

        void onDataNotAvailable();
    }

    interface LoadImageURL {
        void onImageInfoLoaded(ImageUrl imageUrl);

        void onDataNotAvailable();
    }

    interface LoadSetCallback {
        void onSetLoaded(Set set);

        void onDataNotAvailable();
    }

    interface LoadAssetEpisodeCallback {
        void onAssetLoaded(AssetEpisode assetEpisode);

        void onDataNotAvailable();
    }

    void saveSets(@NonNull List<Set> setList);

    void getSets(@NonNull LoadSetsCallback loadSetsCallback);

    void getSet(@NonNull String self, @NonNull LoadSetCallback loadSetCallback);

    void saveImageUrl(@NonNull String imageReference, @NonNull String imageURL);

    void getEpisode(@NonNull String episodeUrl, @NonNull LoadEpisodeCallback loadEpisodeCallback);

    void gettAssetEpisode(@NonNull String assetURL, @NonNull LoadAssetEpisodeCallback loadAssetEpisodeCallback);

    void setEpisode(@NonNull Episode episode);

    void getImageURl(@NonNull String imageURL, @NonNull LoadImageURL loadImageURL);

}

