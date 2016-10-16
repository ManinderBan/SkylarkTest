package com.maninder.skylarktest.episode;

import com.maninder.skylarktest.TestUseCaseScheduler;
import com.maninder.skylarktest.data.SkylarkDataSource;
import com.maninder.skylarktest.data.SkylarkRepository;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.episode.usecase.GetAssetsInfo;
import com.maninder.skylarktest.episode.usecase.GetEpisodeInfo;
import com.maninder.skylarktest.episode.usecase.SetInfo;
import com.maninder.skylarktest.threading.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maninder on 16/10/16.
 */

/**
 * Test the Episode and Set features into the Application
 * NOTE: Before to test this class you need to Inject the {@link com.maninder.skylarktest.data.FakeSkylarkRemoteDataSource}
 */
public class EpisodePresenterTest {

    private Episode episode;
    private Set set;

    @Mock
    private SkylarkRepository mSkylarkRepository;

    @Mock
    private EpisodeFragment mEpisodeFragment;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<SkylarkDataSource.LoadEpisodeCallback> mLoadEpisodeCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<SkylarkDataSource.LoadSetCallback> mLoadSetCallbackArgumentCaptor;

    private EpisodePresenter mEpisodePresenter;

    private String request;

    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        mEpisodeFragment.isSet = false;
        mEpisodeFragment.value = "/test/epsiode/";
        // Get a reference to the class under test
        mEpisodePresenter = givenEpisodePresenter();

        // The presenter won't update the view unless it's active.
        when(mEpisodeFragment.isActive()).thenReturn(true);

        request = "/test/epsiode/";
        episode = new Episode(request);
        set = new Set(request);
    }

    private EpisodePresenter givenEpisodePresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetEpisodeInfo getEpisodeInfo = new GetEpisodeInfo(mSkylarkRepository);
        GetAssetsInfo getAssetsInfo = new GetAssetsInfo(mSkylarkRepository);
        SetInfo setInfo = new SetInfo(mSkylarkRepository);

        return new EpisodePresenter(mEpisodeFragment, useCaseHandler, getEpisodeInfo, getAssetsInfo, setInfo);
    }


    @Test
    public void loadEpisodeFromRepositoryAndLoadIntoView() {
        // When loading of episode is requested
        mEpisodePresenter.loadEpisode(request);

        // Callback is captured and invoked with stubbed episode
        verify(mSkylarkRepository).getEpisode(eq(request), mLoadEpisodeCallbackArgumentCaptor.capture());
        mLoadEpisodeCallbackArgumentCaptor.getValue().onEpisodeLoaded(episode);

        // Then progress indicator is hidden and the episode are shown in UI
        ArgumentCaptor<Episode> showEpisodeArgumentCaptor = ArgumentCaptor.forClass(Episode.class);
        verify(mEpisodeFragment).showEpisode(showEpisodeArgumentCaptor.capture());
        assertTrue(showEpisodeArgumentCaptor.getValue().created.equals(request));
    }

    @Test
    public void loadSetFromRepositoryAndLoadIntoView() {
        // When loading of Set is requested
        mEpisodePresenter.loadSet(request);

        // Callback is captured and invoked with stubbed Set
        verify(mSkylarkRepository).getSet(eq(request), mLoadSetCallbackArgumentCaptor.capture());
        mLoadSetCallbackArgumentCaptor.getValue().onSetLoaded(set);

        verify(mEpisodeFragment).setLoadingIndicator(false);
        // Then progress indicator is hidden and a Set are shown in UI
        ArgumentCaptor<Set> showSetArgumentCaptor = ArgumentCaptor.forClass(Set.class);
        verify(mEpisodeFragment).showSet(showSetArgumentCaptor.capture());
        assertTrue(showSetArgumentCaptor.getValue().self.equals(request));
    }
}
