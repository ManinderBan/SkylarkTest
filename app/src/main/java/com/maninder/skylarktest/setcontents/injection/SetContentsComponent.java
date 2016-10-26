package com.maninder.skylarktest.setcontents.injection;

import com.maninder.skylarktest.setcontents.MainActivity;
import com.maninder.skylarktest.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Maninder on 26/10/16.
 */
@FragmentScoped
@Component(modules = SetContentsModule.class)
public interface SetContentsComponent {

    void inject(MainActivity activity);
}
