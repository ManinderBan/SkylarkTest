package com.maninder.skylarktest.episode.injection;

import com.maninder.skylarktest.episode.EpisodeActivity;
import com.maninder.skylarktest.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Maninder on 26/10/16.
 */
@FragmentScoped
@Component(modules = EpisodeModule.class)
public interface EpisodeComponent {

    void inject(EpisodeActivity episodeActivity);

}
