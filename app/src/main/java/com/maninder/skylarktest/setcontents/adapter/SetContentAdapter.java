package com.maninder.skylarktest.setcontents.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maninder.skylarktest.R;
import com.maninder.skylarktest.data.remote.model.Set;
import com.maninder.skylarktest.data.remote.model.SetItems;
import com.maninder.skylarktest.setcontents.SetContentsContract;
import com.maninder.skylarktest.setcontents.customviews.ItemsViewHolder;
import com.maninder.skylarktest.setcontents.listeners.SetViewHolderClickListener;
import com.maninder.skylarktest.setcontents.model.ImageUrl;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 13/10/16.
 */

/**
 * Contents Adapter that show episode and set
 */
public class SetContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SetViewHolderClickListener {

    /**
     * Three type of viewholder
     * CONTENT_VIEW --> show Image and content Set
     * TITLE_VIEW --> show the title of the content
     * HOME_VIEW --> show the episode
     */
    private enum ViewType {
        CONTENT_VIEW, TITLE_VIEW, HOME_VIEW
    }

    public final SetContentsContract.View mView;
    private Context mContext;

    private List<Set> mSetList;
    private Map<String, String> imageURLValueMap;
    private Map<String, ImageInfoListener> imageListenerMap;

    /**
     * @param view    the view in which the Adapter live. Allow to handle the item click
     * @param context context
     * @param setList list of Set Object to populate the List Adapter
     */
    public SetContentAdapter(@NonNull SetContentsContract.View view, @NonNull Context context, List<Set> setList) {
        mView = checkNotNull(view);
        mContext = checkNotNull(context);
        mSetList = setList;
        imageURLValueMap = new HashMap<>();
        imageListenerMap = new HashMap<>();
    }

    /**
     * This method is called when User click on some Set
     *
     * @param self is the Set URL that allow to get the content from {@link com.maninder.skylarktest.data.SkylarkRepository}
     */
    @Override
    public void onClickItem(String self) {
        mView.showSetItem(self);
    }

    /**
     * This mehod is called when user click on some Episode on Home root
     *
     * @param setItem si the Episode item selected by User
     */
    @Override
    public void onClickItemEpisode(SetItems setItem) {
        mView.showEpisodeItem(setItem);
    }

    /**
     * Request to the server to give the Image info
     *
     * @param imageURLRequest the image requested
     * @param imageView       listener for every Image how do this request
     */
    @Override
    public void onImageURLRequest(@NonNull String imageURLRequest, ImageInfoListener imageView) {
        checkNotNull(imageURLRequest);
        if (!imageListenerMap.containsKey(imageURLRequest)) {
            imageListenerMap.put(imageURLRequest, imageView);
            mView.getImageURL(imageURLRequest);
        }
    }

    /**
     * Is called when the View recieved the Image download Url from server
     *
     * @param imageUrl represent the Imgae info requested to the Server
     */
    public void showImageListenerListener(@NonNull ImageUrl imageUrl) {
        checkNotNull(imageUrl);
        if (imageListenerMap.containsKey(imageUrl.self)) {
            ImageInfoListener imageInfoListener = imageListenerMap.get(imageUrl.self);
            imageInfoListener.onImageInfoLoaded(imageUrl);
            imageURLValueMap.put(imageUrl.self, imageUrl.imageURl);
            imageListenerMap.remove(imageUrl.self);
        }

    }

    /**
     * Populate the Adapter with this new List
     *
     * @param setList new Set list
     */
    public void replaceData(List<Set> setList) {
        mSetList = checkNotNull(setList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mSetList.get(position).items.size() > 0) {
            return ViewType.HOME_VIEW.ordinal();
        } else if (mSetList.get(position).body.isEmpty()) {
            return ViewType.TITLE_VIEW.ordinal();
        }
        return ViewType.CONTENT_VIEW.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ViewType.CONTENT_VIEW.ordinal()) {
            View view = inflater.inflate(R.layout.set_contents_item, parent, false);
            return new SetViewHolder(view, this);
        } else if (viewType == ViewType.HOME_VIEW.ordinal()) {
            View view = inflater.inflate(R.layout.items_viewholder_item, parent, false);
            return new ItemsViewHolder(view, mContext, this);
        }
        View view = inflater.inflate(R.layout.set_contents_title_item, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Set set = mSetList.get(position);
        if (holder instanceof SetViewHolder) {
            ((SetViewHolder) holder).setup(set);
        } else if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).setup(set.title);
        } else if (holder instanceof ItemsViewHolder) {
            ((ItemsViewHolder) holder).setup(set.items, set.title);
        }

    }


    @Override
    public int getItemCount() {
        return mSetList.size();
    }

    /**
     * view holder to hold the Set information
     */
    public class SetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SetViewHolderClickListener mListener;

        @BindView(R.id.imageSet)
        ImageView imageSet;
        @BindView(R.id.titleSet)
        TextView titleSet;
        @BindView(R.id.releaseSet)
        TextView releaseSet;
        @BindView(R.id.countMovie)
        TextView countMovie;

        public SetViewHolder(View itemView, SetViewHolderClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClickItem(mSetList.get(getAdapterPosition()).self);
        }

        public void setup(Set set) {

            checkNotNull(set.title);
            titleSet.setText(set.title);
            String count = Integer.toString(set.filmCount) + " " + mContext.getString(R.string.films);
            countMovie.setText(count);

            if (set.imageUrls != null && set.imageUrls.size() > 0) {
                if (!imageURLValueMap.containsKey(set.imageUrls.get(0))) {
                    imageSet.setTag(set.imageUrls.get(0));
                    imageSet.setImageBitmap(null);
                    getImageURL(set.imageUrls.get(0));
                } else {
                    Picasso.with(mContext).load(imageURLValueMap.get(set.imageUrls.get(0))).fit().centerCrop().into(imageSet);
                }
            } else {
                imageSet.setImageBitmap(null);
                imageSet.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryBlackDark));
            }
            releaseSet.setVisibility(View.GONE);
        }

        public void getImageURL(final String imageURLRequest) {
            final ImageInfoListener imageInfoListener = new ImageInfoListener() {
                @Override
                public void onImageInfoLoaded(ImageUrl imageUrl) {
                    if (imageUrl.self.equals(imageSet.getTag())) {
                        Picasso.with(mContext).load(imageUrl.imageURl).fit().centerCrop().into(imageSet);
                    }
                }

                @Override
                public void onDataNotAvailable() {

                }
            };
            mListener.onImageURLRequest(imageURLRequest, imageInfoListener);
        }
    }

    /**
     * View holder to hold the Title information
     */
    public class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleContent)
        TextView titleContent;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setup(String title) {
            checkNotNull(title);
            titleContent.setText(title);

        }
    }
}
