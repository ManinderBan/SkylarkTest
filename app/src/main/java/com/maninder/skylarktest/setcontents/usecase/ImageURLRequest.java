package com.maninder.skylarktest.setcontents.usecase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.setcontents.listeners.SetViewHolderClickListener;
import com.maninder.skylarktest.setcontents.model.ImageUrl;
import com.maninder.skylarktest.threading.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 13/10/16.
 */

/**
 * UseCase all wrote in Java. This is the business logic login code and allow to get SetContents from {@link SkylarkRepository}
 * Extend the UseCase in this way we can run in the background and communicate events to the upper layer using callback
 */
public class ImageURLRequest extends UseCase<ImageURLRequest.RequestValues, ImageURLRequest.ResponseValue> {

    private final SkylarkRepository mSkylarkRepository;

    public ImageURLRequest(@NonNull SkylarkRepository skylarkRepository) {
        mSkylarkRepository = checkNotNull(skylarkRepository);
    }

    /**
     * Allow to get the URL to download the image
     *
     * @param requestValues value of the image request
     */
    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mSkylarkRepository.getImageURl(requestValues.getmImageURL(), new SkylarkDataSource.LoadImageURL() {
            @Override
            public void onImageInfoLoaded(ImageUrl imageUrl) {
                ResponseValue responseValue = new ResponseValue(imageUrl);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String mImageURL;

        public RequestValues(@NonNull String imageURL) {
            mImageURL = checkNotNull(imageURL);
        }

        public String getmImageURL() {
            return mImageURL;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final ImageUrl mImageUrl;

        public ResponseValue(@NonNull ImageUrl imageUrl) {
            mImageUrl = checkNotNull(imageUrl);

        }

        public ImageUrl getmImageUrl() {
            return mImageUrl;
        }

    }
}
