package com.araidesign.popularmovies.utils;

import android.util.Log;

import com.araidesign.popularmovies.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class JsonUtils {

    private static String TAG = "JsonUtils";

    /**
     * Parse the JSON data returned from the TMDB query
     */
    public static ArrayList<MovieData> parseTMDBRequest(String json) throws JSONException {
        ArrayList<MovieData> movieDataArrayList =new ArrayList<>();
        JSONObject dbRequest;
        JSONArray movieArrayJSON;
        MovieData movieData;

        dbRequest = new JSONObject(json);

        movieArrayJSON = dbRequest.getJSONArray("results");

        for (int i=0 ; i < movieArrayJSON.length(); i++){
            movieData = new MovieData();
            JSONObject movieJson = movieArrayJSON.getJSONObject(i);

            movieData.setId(movieJson.getInt("id"));
            movieData.setVote_count(movieJson.getInt("vote_count"));
            movieData.setTitle(movieJson.getString("title"));
            movieData.setPopularity(movieJson.getDouble("popularity"));
            movieData.setPoster_path(movieJson.getString("poster_path"));
            movieData.setBackdrop_path(movieJson.getString("backdrop_path"));
            movieData.setOverview(movieJson.getString("overview"));
            movieData.setRelease_date(movieJson.getString("release_date"));

            // TODO: Could this array parsing be more efficient??

            JSONArray genreArrayJson = movieJson.getJSONArray("genre_ids");
            ArrayList<Integer> genreArray;
            genreArray = new ArrayList<>();
            for(int j=0; j<genreArrayJson.length(); j++){
                try {
                    Integer genre = genreArrayJson.getInt(j);
                    genreArray.add(genre);
                } catch(JSONException e) {

                    Log.i(TAG,"Genre_id integer conversion error" + e);
                }
            }
            movieData.setGenre_ids(genreArray);
            movieDataArrayList.add(movieData);
        }

        return movieDataArrayList;
    }
}
