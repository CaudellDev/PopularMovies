package com.example.tyler.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    // Constant String to ensure that they're all the same.
    public static final String PARCEL_TAG = "movie_tag";

    private String id;
    private String title;
    private String poster_url;
    private String overview;
    private float vote_avg;
    private String release_date;

    public Movie () {

    }

    public Movie(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.poster_url = in.readString();
        this.overview = in.readString();
        this.vote_avg = in.readFloat();
        this.release_date = in.readString();
    }

    public Movie(String id, String title, String poster_url, String overview, float vote_avg, String release_date) {
        this.id = id;
        this.title = title;
        this.poster_url = poster_url;
        this.overview = overview;
        this.vote_avg = vote_avg;
        this.release_date = release_date;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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

    public float getVote_avg() {
        return vote_avg;
    }

    public void setVote_avg(float vote_avg) {
        this.vote_avg = vote_avg;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getReleaseYear() {
        // Release date format is YYYY-MM-DD, so just split the year from the rest of the string.
        return release_date.split("-", 1)[0];
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
        out.writeString(id);
        out.writeString(title);
        out.writeString(poster_url);
        out.writeString(overview);
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
