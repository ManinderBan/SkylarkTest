package com.maninder.skylarktest.setcontents.listeners;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.remote.model.SetItems;
import com.maninder.skylarktest.setcontents.model.ImageUrl;

/**
 * Created by Maninder on 13/10/16.
 */

/**
 * Listen the Adapter Click
 */
public interface SetViewHolderClickListener {
    interface ImageInfoListener {

        void onImageInfoLoaded(ImageUrl imageUrl);

        void onDataNotAvailable();
    }

    void onClickItem(String self);

    void onClickItemEpisode(SetItems setItem);

    void onImageURLRequest(@NonNull String imageURLRequest, ImageInfoListener imageView);
}
