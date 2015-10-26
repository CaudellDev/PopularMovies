package com.example.tyler.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MovieActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieActivity.class.getSimpleName();

    private DetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Bundle args = new Bundle();
        args.putParcelable(Movie.PARCEL_TAG, getIntent().getParcelableExtra(Movie.PARCEL_TAG));

        fragment = new DetailFragment();
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, fragment)
                .commit();
    }
}
