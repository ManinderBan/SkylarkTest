package com.maninder.skylarktest.data;

import com.maninder.skylarktest.ApplicationModule;
import com.maninder.skylarktest.SkylarkRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Maninder on 26/10/16.
 */

/**
 * This is a Dagger component. Refer to {@link com.maninder.skylarktest.SkylarkApplication} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * com.maninder.skylarktest.SkylarkApplication}.
 */
@Singleton
@Component(modules = {SkylarkRepositoryModule.class, ApplicationModule.class})
public interface SkylarkRepositoryComponent {

    SkylarkRepository getSkylarkRepository();

}
