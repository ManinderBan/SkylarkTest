package com.maninder.skylarktest.episode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maninder.skylarktest.R;
import com.maninder.skylarktest.data.remote.model.AssetEpisode;
import com.maninder.skylarktest.data.remote.model.Episode;
import com.maninder.skylarktest.data.remote.model.Set;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 14/10/16.
 */

/**
 * This UI allow user to see the Set or Episode information in layout
 * This View communicate with {@link EpisodePresenter} to get the information
 */
public class EpisodeFragment extends Fragment implements EpisodeContract.View {

    private static EpisodeFragment INSTANCE = null;

    private EpisodeContract.Presenter mPresenter;

    /**
     * @param episodeUrl Information about the content of the Items
     * @param isSet      false --> Episode ;  true --> Set
     * @return Return an instance of the fragment
     */
    public static EpisodeFragment getINSTANCE(@NonNull String episodeUrl, @NonNull boolean isSet) {
        if (INSTANCE == null) {
            INSTANCE = new EpisodeFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("value", episodeUrl);
        bundle.putBoolean("isSet", isSet);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    @BindView(R.id.episodeImage)
    ImageView episodeImage;

    @BindView(R.id.episodeInfoLayout)
    LinearLayout episodeInfoLayout;

    @BindView(R.id.bodyEpisode)
    TextView bodyEpisode;

    @BindView(R.id.toolbarEpisode)
    Toolbar toolbarEpisode;

    @BindView(R.id.fabPlayContent)
    FloatingActionButton fabPlayContent;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @BindView(R.id.errorText)
    TextView errorText;

    public String value;
    public boolean isSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episode_fragment, container, false);
        ButterKnife.bind(this, view);
        ((EpisodeActivity) getActivity()).setSupportActionBar(toolbarEpisode);
        toolbarEpisode.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbarEpisode.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        if (getArguments() != null) {
            isSet = getArguments().getBoolean("isSet");
            value = getArguments().getString("value");
        } else {
            isSet = false;
            value = "";
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Get the right information fo the content
        if (isSet) {
            mPresenter.loadSet(value);
        } else {
            mPresenter.loadEpisode(value);
        }
    }

    @Override
    public void setPresenter(EpisodeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * Show the Episode information
     * @param episode contain the episode info
     */
    @Override
    public void showEpisode(Episode episode) {
        checkNotNull(episode.title);
        toolbarEpisode.setTitle(episode.title);

        if (episode.synopsis != null && !episode.synopsis.isEmpty()) {
            bodyEpisode.setText(episode.synopsis);
        } else {
            bodyEpisode.setVisibility(View.GONE);
        }
        if (episode.items != null && episode.items.size() > 0) {
            mPresenter.loadAssetEpisode(episode.items.get(0));
        }
    }

    /**
     * Show the Episode Asset retrieved from server
     * @param assetEpisode contain the information about the asset
     */
    @Override
    public void showAssetEpisode(AssetEpisode assetEpisode) {
        if (assetEpisode.releaseDate != null && !assetEpisode.releaseDate.isEmpty()) {
            populateView(getResources().getString(R.string.release_date), assetEpisode.releaseDate, false);
        }
        if (assetEpisode.durationSecond != null && !assetEpisode.durationSecond.isEmpty()) {
            populateView(getResources().getString(R.string.duration), assetEpisode.durationSecond, false);
        }
        if (assetEpisode.subtitles != null && !assetEpisode.subtitles.isEmpty()) {
            populateView(getResources().getString(R.string.subtitle), assetEpisode.subtitles, false);
        }
        if (assetEpisode.sound != null && !assetEpisode.sound.isEmpty()) {
            populateView(getResources().getString(R.string.sound), assetEpisode.sound, false);
        }
    }

    /**
     * Populate Dynamically the layout when requeired
     * @param info First information to show
     * @param nameValue Second inforamtion to show
     * @param orientation Orientation true --> vertical ; false --> horizontal
     */
    public void populateView(@NonNull String info, @NonNull String nameValue, @NonNull boolean orientation) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.episode_info, null);
        if (orientation) {
            ((LinearLayout) view).setOrientation(LinearLayout.VERTICAL);
        } else {
            ((LinearLayout) view).setOrientation(LinearLayout.HORIZONTAL);
        }
        TextView nameInfo = (TextView) view.findViewById(R.id.nameInfo);
        nameInfo.setText(info);
        TextView infoEpisode = (TextView) view.findViewById(R.id.infoEpisode);
        infoEpisode.setText(nameValue);
        episodeInfoLayout.addView(view);
    }

    /**
     * Show the Set information into the layout
     * @param set Contain the set inforamtion
     */
    @Override
    public void showSet(Set set) {
        checkNotNull(set.title);
        toolbarEpisode.setTitle(set.title);

        Spanned result;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(set.body, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(set.body);
        }
        bodyEpisode.setText(result);
        if (set.imageDownloadUrl != null && set.imageDownloadUrl.size() > 0) {
            showImage(set.imageDownloadUrl.get(0));
        }
        checkSet(set);
    }

    public void showImage(@NonNull String imageURL) {
        checkNotNull(imageURL);
        Picasso.with(getContext()).load(imageURL).fit().centerCrop().into(episodeImage);
    }

    public void checkSet(Set set) {
        if (set.cachedFilmCount > 0) {
            populateView(getResources().getString(R.string.film_count), Integer.toString(set.cachedFilmCount), false);
        }
        if (set.summary != null && !set.summary.isEmpty()) {
            populateView(getResources().getString(R.string.summary), set.summary, true);
        }
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
    public void setLoadingError(boolean active) {
        if (getView() == null) {
            return;
        }

        if (active) {
            errorText.setVisibility(View.VISIBLE);
            bodyEpisode.setVisibility(View.INVISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
