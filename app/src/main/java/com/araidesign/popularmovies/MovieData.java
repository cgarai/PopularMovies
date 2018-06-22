package com.araidesign.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MovieData implements Parcelable {
    private String title;
    private String poster_path;
    private int id;
    private int vote_count;
    private double popularity;
    private ArrayList<Integer> genre_ids;
    private String overview;
    private Calendar release_date;
    private String release_date_string;
    private String  backdrop_path;

//  TODO Do I need a constructor?
    public void  MovieData(){
//        this.genre_ids = new ArrayList<>();
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVote_count(int vote_count){
        this.vote_count = vote_count;
    }
    public int getVote_count(){
        return vote_count;
    }

    public void setPopularity (double popularity){
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids){
        this.genre_ids = new ArrayList<>();
        this.genre_ids.addAll(genre_ids);
    }
    public ArrayList<Integer> getGenre_ids(){
        return genre_ids;
    }

    public void setOverview(String overview){
        this.overview= overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setRelease_date(String release_date){
        this.release_date_string = release_date;

        this.release_date = Calendar.getInstance();
        String [] dateBits = release_date.split("\\D");
        int [] dateChunks;
        dateChunks = new int[3];
        for(int i=0; i<dateBits.length; i++){
            dateChunks[i] = Integer.parseInt(dateBits[i]);
        }
        this.release_date.set(dateChunks[0],dateChunks[1]-1, dateChunks[2]);

    }

    public String getRelease_date_string() {
        return release_date_string;
    }


    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeInt(this.vote_count);
        dest.writeString(this.overview);
        dest.writeString(this.release_date_string);
        dest.writeSerializable(this.release_date);
        dest.writeInt(this.id);
        dest.writeDouble(this.popularity);
        dest.writeList(this.genre_ids);
    }

    public MovieData() {
    }

    protected MovieData(Parcel in) {
        this.title = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.vote_count = in.readInt();
        this.overview = in.readString();
        this.release_date_string = in.readString();
        this.release_date = (Calendar) in.readSerializable();
        this.id = in.readInt();
        this.popularity = in.readDouble();
        this.genre_ids = new ArrayList<>();
        in.readList(this.genre_ids, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
