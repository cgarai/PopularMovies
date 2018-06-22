package com.araidesign.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.araidesign.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PosterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<MovieData> movieDataArrayList;
    private Context context;
    final private ItemClickListener mOnClickListener;

    /**
     * Constructor for MovieAdapter
     * TODO: I'm not sure if context is needed.  I need to understand context!!
     *
     * @param context
     * @param movieDataAL
     */
    MovieAdapter(Context context, ArrayList<MovieData> movieDataAL) {
        this.context = context;
        this.movieDataArrayList = movieDataAL;
        mOnClickListener = (ItemClickListener) context;
    }

    public interface ItemClickListener {
        void OnItemClick(int clickedItemIndex);

    }

    public class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView gridItemPosterView;

        /**
         * Constructor for PosterViewHolder
         * @param iv
         */
        PosterViewHolder(View iv) {
            super(iv);
            gridItemPosterView =  iv.findViewById(R.id.iv_poster_item);
            iv.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {

            mOnClickListener.OnItemClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        Context context = parentViewGroup.getContext();
        int layoutIDForPosterItem = R.layout.poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view =  inflater.inflate(layoutIDForPosterItem, parentViewGroup, attachToParentImmediately);

//        PosterViewHolder viewHolder = new PosterViewHolder(view);
        return new PosterViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
//        TODO: Should this network image request be done with AsyncTask?
        URL poster_path = NetworkUtils.buildImageURL(movieDataArrayList.get(position).getPoster_path());
        Picasso.with(context).load(poster_path.toString()).into(holder.gridItemPosterView);

    }

    @Override
    public int getItemCount() {
        return movieDataArrayList.size();
    }
}
