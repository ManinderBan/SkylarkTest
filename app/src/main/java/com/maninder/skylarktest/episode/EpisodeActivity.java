package com.maninder.skylarktest.episode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.maninder.skylarktest.Injection;
import com.maninder.skylarktest.R;

/**
 * Created by Maninder on 14/10/16.
 */

/**
 * This class hold {@link EpisodePresenter} that is the Presenter for this Activity
 */
public class EpisodeActivity extends AppCompatActivity {

    public static final String EPISODE_URL_REQUEST = "episodeUrl";
    public static final String SET_REQUEST = "setRequest";
    public static final String TYPE_REQUEST = "bundleType";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_activity);

        //False -->Episode
        //True --> Set
        boolean isSet = getIntent().getExtras().getBoolean(TYPE_REQUEST, false);
        String value;
        if (!isSet) {
            value = getIntent().getExtras().getString(EPISODE_URL_REQUEST);
        } else {
            value = getIntent().getExtras().getString(SET_REQUEST);
        }
        EpisodeFragment episodeFragment = (EpisodeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameEpisode);
        if (episodeFragment == null) {
            episodeFragment = EpisodeFragment.getINSTANCE(value, isSet);
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrameEpisode, episodeFragment).commit();
        }
        new EpisodePresenter(
                episodeFragment,
                Injection.provideUseCaseHandler(),
                Injection.provideGetEpisodeInfo(getApplicationContext()),
                Injection.provideGetAssetInfo(getApplicationContext()),
                Injection.provideSetInfo(getApplicationContext()));
    }
}
