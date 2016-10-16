package com.maninder.skylarktest.setcontents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maninder.skylarktest.R;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.data.remote.model.SetItems;
import com.maninder.skylarktest.episode.EpisodeActivity;
import com.maninder.skylarktest.setcontents.adapter.SetContentAdapter;
import com.maninder.skylarktest.setcontents.model.ImageUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * This UI allow user to see the Main content of the Application
 * This View communicate with {@link SetContentsPresenter} to get the information
 */
public class SetContentsFragment extends Fragment implements SetContentsContract.View {

    private final String TAG = "SetContentsFragment";

    private static SetContentsFragment INSTANCE = null;
    /**
     * Presenter: Handle events from the UI
     */
    private SetContentsContract.Presenter mPresenter;
    private SetContentAdapter mSetContentAdapter;

    @BindView(R.id.setContentsRecyclerView)
    RecyclerView mSetContentsRecyclerView;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @BindView(R.id.errorText)
    TextView errorText;

    @BindView(R.id.swipeRefreshContent)
    SwipeRefreshLayout mSwipeRefreshContent;

    public static SetContentsFragment getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new SetContentsFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_contents_fragment, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
            }
        });
        mSetContentAdapter = new SetContentAdapter(this, getContext(), new ArrayList<Set>(0));
        mSetContentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSetContentsRecyclerView.setAdapter(mSetContentAdapter);

        return view;
    }

    /**
     * Retrieve the Contents
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(SetContentsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * This is called when we received the data from server
     *
     * @param setList List of Set contents passed into the Adapter
     */
    @Override
    public void showSetsContents(List<Set> setList) {
        Log.d(TAG, "Data received");
        mSetContentAdapter.replaceData(setList);
    }

    /**
     * Show the Episode
     * @param setItem define th item to show
     */
    @Override
    public void showEpisodeItem(SetItems setItem) {
        Intent intent = new Intent(getActivity(), EpisodeActivity.class);
        intent.putExtra(EpisodeActivity.EPISODE_URL_REQUEST, setItem.contentURL);
        intent.putExtra(EpisodeActivity.TYPE_REQUEST, false);
        getActivity().startActivity(intent);
    }

    /**
     * Show the Sert
     * @param self identifier of the set item
     */
    @Override
    public void showSetItem(String self) {
        Intent intent = new Intent(getActivity(), EpisodeActivity.class);
        intent.putExtra(EpisodeActivity.SET_REQUEST, self);
        intent.putExtra(EpisodeActivity.TYPE_REQUEST, true);
        getActivity().startActivity(intent);
    }

    /**
     * Request the Image Url
     * @param imageURLRequest the image reqfernce required
     */
    @Override
    public void getImageURL(@NonNull String imageURLRequest) {
        mPresenter.loadImageURL(imageURLRequest);
    }

    /**
     * Set the Image required
     * @param imageUrl the get from Server
     */
    @Override
    public void setImageURL(@NonNull ImageUrl imageUrl) {
        mSetContentAdapter.showImageListenerListener(imageUrl);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void stopRefresh() {
        mSwipeRefreshContent.setRefreshing(false);
    }

    @Override
    public void setLoadingError(boolean active) {
        if (getView() == null) {
            return;
        }

        if (active) {
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
