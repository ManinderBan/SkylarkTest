package com.maninder.skylarktest.setcontents;

import com.google.common.collect.Lists;
import com.maninder.skylarktest.TestUseCaseScheduler;
import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.setcontents.model.ImageUrl;
import com.maninder.skylarktest.setcontents.usecase.GetSetContents;
import com.maninder.skylarktest.setcontents.usecase.ImageURLRequest;
import com.maninder.skylarktest.threading.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maninder on 16/10/16.
 */

/**
 * Test the Set Contents features into the Application
 * NOTE: Before to test this class you need to Inject the {@link com.maninder.skylarktest.data.FakeSkylarkRemoteDataSource}
 */
public class SetContentsPresenterTest {

    private static List<Set> SETS;

    private static String imageRequest;
    private static ImageUrl imageResponse;

    @Mock
    private SkylarkRepository mSkylarkRepository;

    @Mock
    private SetContentsFragment mSetContentsFragment;

    @Captor
    private ArgumentCaptor<SkylarkDataSource.LoadSetsCallback> mLoadSetsCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<SkylarkDataSource.LoadImageURL> mLoadImageURLArgumentCaptor;

    private SetContentsPresenter mSetContentsPresenter;

    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mSetContentsPresenter = givenSetsPresenter();

        // The presenter won't update the view unless it's active.
        when(mSetContentsFragment.isActive()).thenReturn(true);

        // We start the sets to 3
        SETS = Lists.newArrayList(
                new Set(),
                new Set(),
                new Set());
        imageRequest = "/test/getImage/";
        imageResponse = new ImageUrl("", imageRequest, "");
    }

    private SetContentsPresenter givenSetsPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetSetContents getSetContents = new GetSetContents(mSkylarkRepository);
        ImageURLRequest imageURLRequest = new ImageURLRequest(mSkylarkRepository);

        return new SetContentsPresenter(mSetContentsFragment, useCaseHandler, getSetContents, imageURLRequest);
    }


    @Test
    public void loadAllSetsFromRepositoryAndLoadIntoView() {
        // When loading of Sets is requested
        mSetContentsPresenter.loadSetContents();

        // Callback is captured and invoked with stubbed sets
        verify(mSkylarkRepository).getSets(mLoadSetsCallbackArgumentCaptor.capture());
        mLoadSetsCallbackArgumentCaptor.getValue().onSetsLoaded(SETS);

        // Then progress indicator is hidden and all sets are shown in UI
        verify(mSetContentsFragment).setLoadingIndicator(false);
        ArgumentCaptor<List> showTasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mSetContentsFragment).showSetsContents(showTasksArgumentCaptor.capture());
        assertTrue(showTasksArgumentCaptor.getValue().size() == 3);
    }

    @Test
    public void loadImageURlFromRepositoryAndLoadIntoView() {
        mSetContentsPresenter.loadImageURL(imageRequest);

        verify(mSkylarkRepository).getImageURl(eq(imageRequest), mLoadImageURLArgumentCaptor.capture());
        mLoadImageURLArgumentCaptor.getValue().onImageInfoLoaded(imageResponse);

        ArgumentCaptor<ImageUrl> showImageUrlArgumentCaptor = ArgumentCaptor.forClass(ImageUrl.class);
        verify(mSetContentsFragment).setImageURL(showImageUrlArgumentCaptor.capture());
        assertTrue(showImageUrlArgumentCaptor.getValue().contentURL.equals(imageRequest));
    }
}
