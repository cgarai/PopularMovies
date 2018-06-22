package com.araidesign.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.araidesign.popularmovies.utils.JsonUtils;
import com.araidesign.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private  MovieAdapter mMovieAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<MovieData> allMovieData = new ArrayList<>();

    private  String api_key;

    private static final int NUM_MOVIES = 20;
    private Toast mToast;
    ProgressBar mLoadingIndicator;
    private static boolean onAppStart = true;

//    public MainActivity(){
//
//        String api_key = getString(R.string.my_API_key);
////        makePopularMovieSearch();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api_key = getString(R.string.my_API_key);

        mRecyclerView = findViewById(R.id.movie_recycler_view);

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMovieAdapter = new MovieAdapter(this, allMovieData);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator =  findViewById(R.id.pb_load_indicator);

        if (onAppStart){
            onAppStart = false;
            makePopularMovieSearch();

        }
//        TODO 2: Having trouble getting the posters to display upon opening and device rotation
//        Device rotation was fixed when I converted movieSearchTask to static, per warnings.
//        Tried using scrollBy with null values to get a redraw
//        mMovieAdapter.notifyDataSetChanged();
//        mRecyclerView.scrollBy(0,0);

    }


    private void launchDetailActivity(int position){
        String movieJson;

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION,position);
        intent.putExtra(DetailActivity.MOVIE_DATA, allMovieData.get(position));
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemClicked = item.getItemId();
        switch (item.getItemId()){
            case R.id.action_popular:
                makePopularMovieSearch();
                break;
            case R.id.action_top_rated:
                makeTopRatedMovieSearch();
                break;
            default:
                if (mToast != null) {
                    mToast.cancel();
                }
                String toastMessage = "What?! Nothing Clicked?";
                mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
                mToast.show();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void OnItemClick(int clickedItemIndex) {

        launchDetailActivity(clickedItemIndex);


//
//        if (mToast != null) {
//            mToast.cancel();
//        }
//        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
//        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
//
//        mToast.show();
    }

    void makePopularMovieSearch() {
        allMovieData.clear();
        URL url = NetworkUtils.buildTMDBQueryURL(api_key,getString(R.string.popular_movie_search));

        new MovieSearchTask().execute(url);
    }

    void makeTopRatedMovieSearch(){
        allMovieData.clear();
        URL url = NetworkUtils.buildTMDBQueryURL(api_key,getString(R.string.top_rated_movie_search));
        new MovieSearchTask().execute(url);

    }

//    TODO 3:  I get a warning that AsyncTask should be static, but  then mLoadingIndicator and mMovieAdapter need to be static, but that causes warnings as well
    public  class MovieSearchTask extends AsyncTask<URL, Void, ArrayList<MovieData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected  ArrayList<MovieData> doInBackground(URL... url) {
            String movieListJSON;
            ArrayList<MovieData> movieDataFromJson = new ArrayList<>();
            try {
                //Go get the json string from the network
                movieListJSON = NetworkUtils.getMovieDBJSON(url[0]);

                try {
                    //Parse that json string
                    movieDataFromJson = JsonUtils.parseTMDBRequest(movieListJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieDataFromJson;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieData){
            mLoadingIndicator.setVisibility(View.INVISIBLE);

// TODO: Is this the best method to copy ArrayList ??
            allMovieData.addAll(movieData);

//    TODO 2: MainActivity is not consistently getting the screen refresshed when selecting Popular or Top Rated
            mMovieAdapter.notifyDataSetChanged();

        }
    }
}

