package com.example.tyler.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tyler on 9/17/2015.
 */
public class Movie implements Parcelable {

    public static final String PARCEL_TAG = "movie_tag";

    private String title;
    private String poster_url;
    private String overview;
    private float popularity;
    private float vote_avg;
    private String release_date;

    public Movie () {

    }

    public Movie(Parcel in) {
        this.title = in.readString();
        this.poster_url = in.readString();
        this.overview = in.readString();
        this.popularity = in.readFloat();
        this.vote_avg = in.readFloat();
        this.release_date = in.readString();
    }

    public Movie(String title, String poster_url, String overview, float popularity, float vote_avg, String release_date) {
        this.title = title;
        this.poster_url = poster_url;
        this.overview = overview;
        this.popularity = popularity;
        this.vote_avg = vote_avg;
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getVote_avg() {
        return vote_avg;
    }

    public void setVote_avg(float vote_avg) {
        this.vote_avg = vote_avg;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(poster_url);
        out.writeString(overview);
        out.writeFloat(popularity);
        out.writeFloat(vote_avg);
        out.writeString(release_date);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }


    };
}
