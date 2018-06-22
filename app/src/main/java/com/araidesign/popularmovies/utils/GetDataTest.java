package com.araidesign.popularmovies.utils;

import com.araidesign.popularmovies.MovieAdapter;
import com.araidesign.popularmovies.MovieData;

import java.util.ArrayList;

public class GetDataTest {

    private static String[] posters = {
            "/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg", "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            "/uxzzxijgPIY7slzFvMotPv8wjKA.jpg", "/r70GGoZ5PqqokDDRnVfTN7PPDtJ.jpg",
            "/cGhdduplj8YdAwg7iPCeGjO1YvZ.jpg", "/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg",
            "/sM33SANp9z6rXW8Itn7NnG1GOEs.jpg", "/30oXQKwibh0uANGMs0Sytw3uN22.jpg",
            "/vLCogyfQGxVLDC1gqUdNAIkc29L.jpg", "/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg"};

    public GetDataTest() {
    }

    /**
     * This returns an array of MovieData class types with the poster_path members initialized
     *
     * @return ArrayList<MovieData>
     */
    public static ArrayList<MovieData> loadSamplePosters () {

        ArrayList<MovieData> movieDataList = new ArrayList<>();


        for (int i = 0; i < posters.length; i++)   {
            MovieData movieData = new MovieData();
            movieData.setPoster_path(posters[i]);
            movieDataList.add(movieData);
        }

        return movieDataList;
    }


    }
