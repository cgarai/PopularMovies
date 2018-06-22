package com.araidesign.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;

import java.util.*;

import com.araidesign.popularmovies.MainActivity;
import com.araidesign.popularmovies.R;

public class NetworkUtils {

    static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    static final String POSTER_IMAGE_WIDTH_W342 = "w342";
    static final String POSTER_IMAGE_WIDTH_W185 = "w185";
    static final String POSTER_IMAGE_WIDTH_W500 = "w500";
    static final String POSTER_IMAGE_WIDTH_W780 = "w780";

    static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    //    static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/discover/movie/";
    static final String API_KEY_KEY = "api_key";
    static final String SORT_BY_KEY = "sort_by";
    static final String PAGE_Key = "page";
    static final String LANGUAGE_KEY = "language";

    static final String SORT_POPULAR = "popular";
    static final String LANGUAGE_EN_US = "en-US";

    /**
     * Builds the URL for getting a poster image.
     *
     * @param posterPath is the path string for the poster image
     */
    public static URL buildImageURL(String posterPath) {

        //This uses the Uri.parse() to turn the base URL string to a Uri
        //which then uses the uri.buildUpon() method to turn the uri int uriBuilder
        //At that point the uriBuilder methods are used to append the pieces.
        //Lastly the uriBuilder.build() method returns a Uri
        Uri buildUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(POSTER_IMAGE_WIDTH_W780)
                .appendEncodedPath(posterPath)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Builds URL to get a page of TMDB data.
     *
     * @param api_key must be passed in.
     *                TODO: add @param pageNum to get more pages
     */
    public static URL buildTMDBQueryURL(String api_key, String endPoint ) {
        //This method uses only the Uri.Builder class. This allows for each
        //section to be added in a separate line.  This will allow arbitrary
        //Uri construction, as in the case where we have a menu of filter choices
        Uri.Builder buildUri;
        buildUri = Uri.parse(TMDB_BASE_URL).buildUpon();
        buildUri.appendPath(endPoint);
        buildUri.appendQueryParameter(PAGE_Key, "1");
        buildUri.appendQueryParameter(API_KEY_KEY, api_key);
        buildUri.appendQueryParameter(LANGUAGE_KEY, LANGUAGE_EN_US);
        buildUri.build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getMovieDBJSON(URL url) throws IOException {
        HttpURLConnection dbConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = dbConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A"); // \\A = beginning of input

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            dbConnection.disconnect();
        }
    }

}
