package com.maninder.skylarktest.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.remote.model.AssetEpisode;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.data.remote.model.SetList;
import com.maninder.skylarktest.data.remote.service.AssetEpisodeService;
import com.maninder.skylarktest.data.remote.service.EpisodeService;
import com.maninder.skylarktest.data.remote.service.SetContentsService;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.setcontents.model.ImageUrl;
import com.maninder.skylarktest.data.remote.service.ImageUrlService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class SkylarkRemoteDataSource implements SkylarkDataSource {

    private static final String LOG = "SkylarkRemoteDataSource";

    private static SkylarkRemoteDataSource INSTANCE;

    public static SkylarkRemoteDataSource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new SkylarkRemoteDataSource();
        }
        return INSTANCE;
    }

    private SkylarkRemoteDataSource() {
    }

    /**
     * THis method used an Observable (RxJava) to get Sets information from server in Async
     * Timeouts --> 200 second, after that time the this method get an Error
     *
     * @param loadSetsCallback This is called when we egt data fro server, in case of error we call {@link LoadSetsCallback#onDataNotAvailable()}
     */
    @Override
    public void getSets(@NonNull final LoadSetsCallback loadSetsCallback) {
        SetContentsService setContentsService = RestClient.getService(SetContentsService.class);

        // getNewGuess --> Where we can get the new guess
        Observable<SetList> setListObservable = setContentsService.getSetContents();
        setListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<SetList>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "On Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, e.getMessage());
                        loadSetsCallback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(SetList s) {
                        List<Set> restSetList = s.restSets;
                        loadSetsCallback.onSetsLoaded(restSetList);
                    }
                });
    }

    /**
     * Get the Image download URL from server
     *
     * @param imageURL     Endpoint to retrieve the Image data
     * @param loadImageURL
     */
    @Override
    public void getImageURl(@NonNull String imageURL, @NonNull final LoadImageURL loadImageURL) {
        ImageUrlService imageUrlService = RestClient.getService(ImageUrlService.class);
        Observable<ImageUrl> imageUrlObservable = imageUrlService.getImageUrl(imageURL);
        imageUrlObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ImageUrl>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "On Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, e.getMessage());
                        loadImageURL.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(ImageUrl imageUrl) {
                        loadImageURL.onImageInfoLoaded(imageUrl);
                    }
                });
    }

    /**
     * Get a Single Episode information
     *
     * @param episodeUrl          The Endpoint required to get the right Episode
     * @param loadEpisodeCallback
     */
    @Override
    public void getEpisode(@NonNull String episodeUrl, @NonNull final LoadEpisodeCallback loadEpisodeCallback) {
        EpisodeService episodeService = RestClient.getService(EpisodeService.class);
        Observable<Episode> episodeObservable = episodeService.getEpisodenfo(episodeUrl);
        episodeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Episode>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "On Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, e.getMessage());
                        loadEpisodeCallback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(Episode episode) {
                        loadEpisodeCallback.onEpisodeLoaded(episode);
                    }
                });
    }

    /**
     * Get the Asset episode from server
     *
     * @param assetURL                 The Endpoint required to get EpisodeAsset
     * @param loadAssetEpisodeCallback
     */
    @Override
    public void gettAssetEpisode(@NonNull String assetURL, @NonNull final LoadAssetEpisodeCallback loadAssetEpisodeCallback) {
        AssetEpisodeService assetEpisodeService = RestClient.getService(AssetEpisodeService.class);
        Observable<AssetEpisode> assetEpisodeObservable = assetEpisodeService.getAssetEpisode(assetURL);
        assetEpisodeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AssetEpisode>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "On Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, e.getMessage());
                        loadAssetEpisodeCallback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(AssetEpisode assetEpisode) {
                        loadAssetEpisodeCallback.onAssetLoaded(assetEpisode);
                    }
                });
    }

    @Override
    public void setEpisode(@NonNull Episode episode) {

    }

    @Override
    public void saveImageUrl(@NonNull String imageReference, @NonNull String imageURL) {

    }

    @Override
    public void getSet(@NonNull String self, @NonNull LoadSetCallback loadSetCallback) {

    }

    @Override
    public void saveSets(@NonNull List<Set> setList) {

    }

}
