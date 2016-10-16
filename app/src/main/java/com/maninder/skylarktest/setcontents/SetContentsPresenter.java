package com.maninder.skylarktest.setcontents;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.setcontents.model.ImageUrl;
import com.maninder.skylarktest.setcontents.usecase.GetSetContents;
import com.maninder.skylarktest.setcontents.usecase.ImageURLRequest;
import com.maninder.skylarktest.threading.UseCase;
import com.maninder.skylarktest.threading.UseCaseHandler;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * This is the Presenter and create an communication between UseCase and View.
 * This class listen the user actions from the UI {@link SetContentsFragment}, retrieves the data from UseCase
 * and updates the UI as required
 */
public class SetContentsPresenter implements SetContentsContract.Presenter {


    private final SetContentsContract.View mContentView;
    private final GetSetContents mGetSetContents;
    private final ImageURLRequest mImageURLRequest;
    private final UseCaseHandler mUseCaseHandler;

    /**
     * @param view            Represent the current UI and is required to communicate with UseCase
     * @param useCaseHandler  Is required if we want to run the UseCase in different Thread, in this way we don't impact user usability
     * @param getSetContents  Usecase that allow to retrieve the Set Contents from {@link com.maninder.skylarktest.data.SkylarkRepository}
     * @param imageURLRequest UseCase that allow to retrieve Image ddownload url form {@link com.maninder.skylarktest.data.remote.SkylarkRemoteDataSource]}
     */
    public SetContentsPresenter(@NonNull SetContentsContract.View view,
                                @NonNull UseCaseHandler useCaseHandler,
                                @NonNull GetSetContents getSetContents,
                                @NonNull ImageURLRequest imageURLRequest) {
        mContentView = checkNotNull(view);
        mUseCaseHandler = checkNotNull(useCaseHandler);
        mGetSetContents = checkNotNull(getSetContents);
        mImageURLRequest = checkNotNull(imageURLRequest);

        mContentView.setPresenter(this);
    }

    @Override
    public void start() {
        loadSetContents();
    }

    /**
     * Get all the Sets from {@link com.maninder.skylarktest.data.remote.SkylarkRemoteDataSource} and pass to the View if exist
     * Update the UI in base of the Response from Remote Data Source
     * <p>
     * {@link UseCaseHandler} --> this allow to handle the request in another thread to retrieve data from {@link com.maninder.skylarktest.data.SkylarkRepository}
     */
    @Override
    public void loadSetContents() {
        final GetSetContents.RequestValues requestValues = new GetSetContents.RequestValues();
        mUseCaseHandler.execute(mGetSetContents, requestValues, new UseCase.UseCaseCallback<GetSetContents.ResponseValue>() {
            @Override
            public void onSuccess(GetSetContents.ResponseValue response) {

                List<Set> setList = response.getmSetList();

                if (!mContentView.isActive()) {
                    return;
                }
                processUI(false, false);
                processSetContents(setList);
            }

            @Override
            public void onError() {
                if (!mContentView.isActive()) {
                    return;
                }
                processUI(false, true);
                mContentView.setLoadingIndicator(false);
            }
        });
    }

    /**
     * Get Image download URL from remote data source {@link com.maninder.skylarktest.data.remote.SkylarkRemoteDataSource}
     *
     * @param imageUrlRequest This is necessary required to get the right Image download URL
     */
    @Override
    public void loadImageURL(@NonNull final String imageUrlRequest) {
        final ImageURLRequest.RequestValues requestValues = new ImageURLRequest.RequestValues(imageUrlRequest);
        mUseCaseHandler.execute(mImageURLRequest, requestValues, new UseCase.UseCaseCallback<ImageURLRequest.ResponseValue>() {
            @Override
            public void onSuccess(ImageURLRequest.ResponseValue response) {
                if (!mContentView.isActive()) {
                    return;
                }
                proccessImageURLResponse(response.getmImageUrl());
            }

            @Override
            public void onError() {

            }
        });

    }

    /**
     * Update the UI
     *
     * @param indicator enable or disable progressbar
     * @param error     enable and disable error message
     */
    private void processUI(boolean indicator, boolean error) {
        mContentView.stopRefresh();
        mContentView.setLoadingIndicator(indicator);
        mContentView.setLoadingError(error);
    }

    private void processSetContents(List<Set> setList) {
        mContentView.showSetsContents(setList);
    }

    private void proccessImageURLResponse(ImageUrl imageUrl) {
        mContentView.setImageURL(imageUrl);
    }
}
