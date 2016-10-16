package com.maninder.skylarktest.setcontents.customviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maninder.skylarktest.R;
import com.maninder.skylarktest.data.remote.model.SetItems;
import com.maninder.skylarktest.setcontents.listeners.SetViewHolderClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 14/10/16.
 */

/**
 * This View holder hold an RecyclerView to show the Episode in Horizontal
 */
public class ItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemsViewHolderRecycler)
    RecyclerView itemsViewHolderRecycler;
    @BindView(R.id.titleContent)
    TextView titleContent;
    private ItemsRecycleViewAdapter itemsRecycleViewAdapter;
    private Context mContext;
    private SetViewHolderClickListener mSetViewHolderClickListener;

    /**
     * Get and RecyclerView if the Set has Items.
     *
     * @param itemView                   the view
     * @param context                    context
     * @param setViewHolderClickListener to get the listen the Click for every episode
     */
    public ItemsViewHolder(View itemView, Context context, SetViewHolderClickListener setViewHolderClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mSetViewHolderClickListener = setViewHolderClickListener;
    }

    public void setup(List<SetItems> setItems, String title) {
        checkNotNull(title);
        titleContent.setText(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        itemsRecycleViewAdapter = new ItemsRecycleViewAdapter(setItems);
        itemsViewHolderRecycler.setLayoutManager(layoutManager);
        itemsViewHolderRecycler.setAdapter(itemsRecycleViewAdapter);
    }


    public class ItemsRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<SetItems> mSetItems;

        public ItemsRecycleViewAdapter(List<SetItems> setItems) {
            super();
            mSetItems = setItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.episode_item, parent, false);
            return new EpisodeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SetItems setItems = mSetItems.get(position);
            ((EpisodeViewHolder) holder).setup(setItems, position);
        }

        @Override
        public int getItemCount() {
            return mSetItems.size();
        }

        public class EpisodeViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.episodeTitle)
            TextView episodeTitle;

            public EpisodeViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setup(SetItems setItems, int position) {
                if (setItems.heading != null && !setItems.heading.isEmpty()) {
                    episodeTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    episodeTitle.setText(setItems.heading);
                } else {
                    episodeTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryRed));
                    String episode = mContext.getString(R.string.episode) + " " + Integer.toString(position);
                    episodeTitle.setText(episode);
                    episodeTitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSetViewHolderClickListener.onClickItemEpisode(mSetItems.get(getAdapterPosition()));
                        }
                    });
                }

            }

        }

    }
}
