package com.maninder.skylarktest.episode.usecase;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.threading.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 14/10/16.
 */
/**
 * UseCase all wrote in Java. This is the business logic login code and allow to get info Set from {@link SkylarkRepository}
 * Extend the UseCase in this way we can run in the background and communicate events to the upper layer using callback
 */
public class SetInfo extends UseCase<SetInfo.RequestValue, SetInfo.ResponseValue> {

    private final SkylarkRepository mSkylarkRepository;

    public SetInfo(@NonNull SkylarkRepository skylarkRepository) {
        mSkylarkRepository = checkNotNull(skylarkRepository);
    }
    /**
     * @param requestValues is the request to the {@link SkylarkRepository}. Hold the requested value.
     */
    @Override
    protected void executeUseCase(RequestValue requestValues) {
        mSkylarkRepository.getSet(requestValues.getSelf(), new SkylarkDataSource.LoadSetCallback() {
            @Override
            public void onSetLoaded(Set set) {
                ResponseValue responseValue = new ResponseValue(set);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class ResponseValue implements UseCase.ResponseValue {
        private Set mSet;

        public ResponseValue(@NonNull Set set) {
            mSet = set;
        }

        public Set getSet() {
            return mSet;
        }
    }

    public static class RequestValue implements UseCase.RequestValues {
        private String mSelf;

        public RequestValue(@NonNull String self) {
            mSelf = self;
        }

        public String getSelf() {
            return mSelf;
        }
    }
}
