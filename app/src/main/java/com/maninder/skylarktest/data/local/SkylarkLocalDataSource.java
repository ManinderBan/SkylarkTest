package com.maninder.skylarktest.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.data.remote.model.Episode;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Implementation of the Local Data source with {@link SharedPreferences}
 */
@Singleton
public class SkylarkLocalDataSource implements SkylarkDataSource {


    private Context mContext;

    public SkylarkLocalDataSource(@NonNull Context context) {
        mContext = checkNotNull(context);
    }

    /**
     * save the Set list in {@link SharedPreferences}
     * NOTE: SET is a Parcelable
     *
     * @param setList Set of list to save
     */
    @Override
    public void saveSets(@NonNull List<Set> setList) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(setList);
        editor.putString(LocalDataConsCons.SETS, json);
        editor.apply();
    }

    /**
     * Get the Set List saved in {@link SharedPreferences}, otherwise get return {@link LoadSetsCallback#onDataNotAvailable()}
     *
     * @param loadSetsCallback Callback to get the set List or error
     */
    @Override
    public void getSets(@NonNull LoadSetsCallback loadSetsCallback) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (sharedPreferences.contains(LocalDataConsCons.SETS)) {
            String json = sharedPreferences.getString(LocalDataConsCons.SETS, "");
            Type type = new TypeToken<List<Set>>() {
            }.getType();
            List<Set> setLists = new Gson().fromJson(json, type);
            loadSetsCallback.onSetsLoaded(setLists);
        } else {
            loadSetsCallback.onDataNotAvailable();
        }
    }

    @Override
    public void saveImageUrl(@NonNull String imageReference, @NonNull String imageURL) {

    }

    @Override
    public void getEpisode(@NonNull String episodeUrl, @NonNull LoadEpisodeCallback loadEpisodeCallback) {

    }

    @Override
    public void setEpisode(@NonNull Episode episode) {

    }

    @Override
    public void getImageURl(@NonNull String imageURL, @NonNull LoadImageURL loadImageURL) {

    }

    @Override
    public void gettAssetEpisode(@NonNull String assetURL, @NonNull LoadAssetEpisodeCallback loadAssetEpisodeCallback) {

    }

    @Override
    public void getSet(@NonNull String self, @NonNull LoadSetCallback loadSetCallback) {

    }
}
