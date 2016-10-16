package com.maninder.skylarktest.setcontents.usecase;

import android.support.annotation.NonNull;

import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.threading.UseCase;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * UseCase all wrote in Java. This is the business logic login code and allow to get SetContents from {@link SkylarkRepository}
 * Extend the UseCase in this way we can run in the background and communicate events to the upper layer using callback
 */
public class GetSetContents extends UseCase<GetSetContents.RequestValues, GetSetContents.ResponseValue> {


    private final SkylarkRepository mSkylarkRepository;

    /**
     * @param skylarkRepository the repository used to get data from Local, if available, or from Remote
     */
    public GetSetContents(@NonNull SkylarkRepository skylarkRepository) {
        mSkylarkRepository = checkNotNull(skylarkRepository);
    }

    /**
     * @param requestValues is the request to the {@link SkylarkRepository}
     */
    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mSkylarkRepository.getSets(new SkylarkDataSource.LoadSetsCallback() {
            @Override
            public void onSetsLoaded(List<Set> setList) {

                ResponseValue responseValue = new ResponseValue(setList);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        public RequestValues() {
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<Set> mSetList;

        public ResponseValue(@NonNull List<Set> setList) {
            mSetList = checkNotNull(setList);
        }

        public List<Set> getmSetList() {
            return mSetList;
        }
    }
}
