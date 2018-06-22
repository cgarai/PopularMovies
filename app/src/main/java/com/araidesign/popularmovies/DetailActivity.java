package com.araidesign.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.araidesign.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

import static java.lang.String.valueOf;

public class DetailActivity extends AppCompatActivity {

    public static final String  EXTRA_POSITION = "extra_position";
    public static final String MOVIE_DATA = "extra_moviedata";

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.iv_detail_poster_item);
        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }
        int position = 0;
        position = intent.getIntExtra(EXTRA_POSITION, 0);

        MovieData movieData = new MovieData();
        movieData = intent.getParcelableExtra(MOVIE_DATA);

        URL poster_path = NetworkUtils.buildImageURL(movieData.getPoster_path());
        Picasso.with(imageIv.getContext()).load(poster_path.toString()).into(imageIv);
        TextView titleTv = findViewById(R.id.tv_title);
        TextView releaseDateTv = findViewById(R.id.tv_release_date);
        TextView VoteCntTv = findViewById(R.id.tv_vote_count);
        TextView popularityTv = findViewById(R.id.tv_poplarity);
        TextView overviewTv = findViewById(R.id.tv_overview);

        titleTv.setText(movieData.getTitle());
        releaseDateTv.setText(movieData.getRelease_date_string());
        VoteCntTv.setText(valueOf(movieData.getVote_count()));
        popularityTv.setText(valueOf(movieData.getPopularity()));
        overviewTv.setText(movieData.getOverview());




    }

    private void closeOnError() {
    }
}
