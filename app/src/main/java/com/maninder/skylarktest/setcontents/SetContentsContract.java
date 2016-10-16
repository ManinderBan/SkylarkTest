package com.maninder.skylarktest.setcontents;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.BasePresenter;
import com.maninder.skylarktest.BaseView;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.data.remote.model.SetItems;
import com.maninder.skylarktest.setcontents.adapter.SetContentAdapter;
import com.maninder.skylarktest.setcontents.listeners.SetViewHolderClickListener;
import com.maninder.skylarktest.setcontents.model.ImageUrl;

import java.util.List;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * This specifies the contract between the view and the presenter.
 */
public interface SetContentsContract {

    interface View extends BaseView<Presenter> {

        void showSetsContents(List<Set> setList);

        boolean isActive();

        void showSetItem(String self);

        void showEpisodeItem(SetItems setItem);

        void getImageURL(@NonNull String imageURL);

        void setImageURL(@NonNull ImageUrl imageUrl);

        void stopRefresh();

        void setLoadingIndicator(boolean active);

        void setLoadingError(boolean active);

    }

    interface Presenter extends BasePresenter {
        void loadSetContents();

        void loadImageURL(@NonNull String imageUrlRequest);
    }
}
