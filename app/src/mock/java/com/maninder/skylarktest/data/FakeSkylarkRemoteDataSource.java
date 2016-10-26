package com.maninder.skylarktest.data;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.setcontents.model.ImageUrl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maninder on 16/10/16.
 */

/**
 * Implementation of a remote data source. This Class is only for Test the Application with Mockito
 * NOTE: Remember to inject this class before to test the Application
 */
public class FakeSkylarkRemoteDataSource implements SkylarkDataSource {


    private static final Map<String, Set> SETS_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    public FakeSkylarkRemoteDataSource() {
    }

    @Override
    public void saveSets(@NonNull List<Set> setList) {

    }

    @Override
    public void getSets(@NonNull LoadSetsCallback loadSetsCallback) {
        loadSetsCallback.onSetsLoaded(Lists.newArrayList(SETS_SERVICE_DATA.values()));
    }

    @Override
    public void getImageURl(@NonNull String imageURL, @NonNull LoadImageURL loadImageURL) {
        ImageUrl imageUrl = new ImageUrl();
        loadImageURL.onImageInfoLoaded(imageUrl);
    }

    @Override
    public void getEpisode(@NonNull String episodeUrl, @NonNull LoadEpisodeCallback loadEpisodeCallback) {
        loadEpisodeCallback.onEpisodeLoaded(new Episode());
    }

    @Override
    public void getSet(@NonNull String self, @NonNull LoadSetCallback loadSetCallback) {

    }

    @Override
    public void saveImageUrl(@NonNull String imageReference, @NonNull String imageURL) {

    }


    @Override
    public void gettAssetEpisode(@NonNull String assetURL, @NonNull LoadAssetEpisodeCallback loadAssetEpisodeCallback) {

    }

    @Override
    public void setEpisode(@NonNull Episode episode) {

    }

}
