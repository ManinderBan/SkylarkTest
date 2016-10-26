package com.maninder.skylarktest.data;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.local.SkylarkLocalDataSource;
import com.maninder.skylarktest.data.remote.SkylarkRemoteDataSource;
import com.maninder.skylarktest.data.remote.model.AssetEpisode;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.setcontents.model.ImageUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Concrete implementation to load Set from the data sources into a mSetMap.
 * <p>
 * Maintains reference to both the repository {@link SkylarkRemoteDataSource} and {@link SkylarkLocalDataSource}
 */
@Singleton
public class SkylarkRepository implements SkylarkDataSource {

    /**
     * This variable is used to hold the Set reference, we don't need to required new remote information every time
     */
    Map<String, Set> mSetMap;

    private final SkylarkDataSource mSkylarkRemoteDataSource;

    private final SkylarkDataSource mSkylarkLocalDataSource;

    /**
     * @param skylarkLocalDataSource  the device storage data source
     * @param skylarkRemoteDataSource the backend data source
     */
    @Inject
    public SkylarkRepository(@Local @NonNull SkylarkDataSource skylarkLocalDataSource,
                              @Remote @NonNull SkylarkDataSource skylarkRemoteDataSource) {

        mSkylarkLocalDataSource = checkNotNull(skylarkLocalDataSource);
        mSkylarkRemoteDataSource = checkNotNull(skylarkRemoteDataSource);
        mSetMap = new LinkedHashMap<>();
    }

    /**
     * Get Sets from local, load data from remote, whichever is available first.
     *
     * @param loadSetsCallback callback to get the data required
     *                         NOTE: {@link LoadSetsCallback#onDataNotAvailable()} is fired when we fail to get data from all the repository
     */
    @Override
    public void getSets(@NonNull final LoadSetsCallback loadSetsCallback) {
        if (mSetMap != null && mSetMap.size() > 0) {
            loadSetsCallback.onSetsLoaded(new ArrayList<Set>(mSetMap.values()));
            return;
        }
        mSkylarkRemoteDataSource.getSets(new LoadSetsCallback() {
            @Override
            public void onSetsLoaded(List<Set> setList) {
                refreshSetMap(setList);
                refreshLocalDataSource(setList);
                loadSetsCallback.onSetsLoaded(setList);
            }

            @Override
            public void onDataNotAvailable() {
                getDataFromLocal(loadSetsCallback);
            }
        });
    }

    /**
     * Get data from Local if backend don't respond
     *
     * @param loadSetsCallback
     */
    private void getDataFromLocal(@NonNull final LoadSetsCallback loadSetsCallback) {
        mSkylarkLocalDataSource.getSets(new LoadSetsCallback() {
            @Override
            public void onSetsLoaded(List<Set> setList) {
                loadSetsCallback.onSetsLoaded(setList);
            }

            @Override
            public void onDataNotAvailable() {
                loadSetsCallback.onDataNotAvailable();
            }
        });

    }

    /**
     * Get the Image information from {@link SkylarkRemoteDataSource}
     *
     * @param imageURL     the info image requested
     * @param loadImageURL Call when we get the data from Server
     */
    @Override
    public void getImageURl(@NonNull final String imageURL, @NonNull final LoadImageURL loadImageURL) {
        mSkylarkRemoteDataSource.getImageURl(imageURL, new LoadImageURL() {
            @Override
            public void onImageInfoLoaded(ImageUrl imageUrl) {
                saveImageUrl(imageUrl.contentURL, imageUrl.imageURl);
                loadImageURL.onImageInfoLoaded(imageUrl);
            }

            @Override
            public void onDataNotAvailable() {
                loadImageURL.onDataNotAvailable();
            }
        });
    }

    /**
     * Save the Image information on local Map that contains Set
     *
     * @param imageReference The Key to retrieve the right Set
     * @param imageURL       Image URL downloaded to save
     */
    @Override
    public void saveImageUrl(@NonNull String imageReference, @NonNull String imageURL) {
        //save Image URL downloaded value
        checkNotNull(mSetMap);
        if (mSetMap.containsKey(imageReference)) {
            Set set = mSetMap.get(imageReference);
            if (set.imageDownloadUrl == null) {
                set.imageDownloadUrl = new ArrayList<>();
            }
            set.imageDownloadUrl.add(imageURL);
            mSetMap.put(imageReference, set);
        }
    }

    /**
     * Get the episode  from server
     *
     * @param episodeUrl          The episode URL requested
     * @param loadEpisodeCallback Call the callback when we have or don't have the data
     */
    @Override
    public void getEpisode(@NonNull String episodeUrl, @NonNull final LoadEpisodeCallback loadEpisodeCallback) {
        mSkylarkRemoteDataSource.getEpisode(episodeUrl, new LoadEpisodeCallback() {
            @Override
            public void onEpisodeLoaded(Episode episode) {
                loadEpisodeCallback.onEpisodeLoaded(episode);
            }

            @Override
            public void onDataNotAvailable() {
                loadEpisodeCallback.onDataNotAvailable();
            }
        });
    }

    /**
     * Get the set from Local
     *
     * @param self            To identify the Set in the MapSet
     * @param loadSetCallback Call when we have or don't have the data
     */
    @Override
    public void getSet(@NonNull String self, @NonNull LoadSetCallback loadSetCallback) {
        if (mSetMap.containsKey(self)) {
            loadSetCallback.onSetLoaded(mSetMap.get(self));
        } else {
            loadSetCallback.onDataNotAvailable();
        }

    }

    /**
     * Get the Asset Episode from Server
     *
     * @param assetURL                 the Episode Asset requested
     * @param loadAssetEpisodeCallback Callback to retrieve the data
     */
    @Override
    public void gettAssetEpisode(@NonNull String assetURL, @NonNull final LoadAssetEpisodeCallback loadAssetEpisodeCallback) {
        mSkylarkRemoteDataSource.gettAssetEpisode(assetURL, new LoadAssetEpisodeCallback() {
            @Override
            public void onAssetLoaded(AssetEpisode assetEpisode) {
                loadAssetEpisodeCallback.onAssetLoaded(assetEpisode);
            }

            @Override
            public void onDataNotAvailable() {
                loadAssetEpisodeCallback.onDataNotAvailable();
            }
        });
    }

    /**
     * Update the data with new data from server
     *
     * @param setList data retrived from server
     */
    private void refreshSetMap(@NonNull List<Set> setList) {
        if (mSetMap == null) {
            mSetMap = new LinkedHashMap<>();
        }
        mSetMap.clear();
        for (Set set : setList) {
            mSetMap.put(set.self, set);
        }
    }

    private void refreshLocalDataSource(@NonNull List<Set> setList) {
        mSkylarkLocalDataSource.saveSets(setList);
    }

    @Override
    public void saveSets(@NonNull List<Set> setList) {

    }

    @Override
    public void setEpisode(@NonNull Episode episode) {

    }

}
