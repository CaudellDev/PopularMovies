package com.example.tyler.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment implements FetchMovieDetailTask.DetailTaskCallback {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    private boolean mTwoPanel;
    private Movie mMovie;

    // In case I have to reference these outside onCreateView, save them here.
    private ImageView posterView;
    private TextView titleView;
    private TextView yearView;
    private TextView overviewView;
    private TextView ratingView;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainActivity and DetailActivity should add via arguments.
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(Movie.PARCEL_TAG);
        } else {
            Log.d(LOG_TAG, "getArguments() == null");
        }

        FetchMovieDetailTask fetchTask = new FetchMovieDetailTask(getContext(), mMovie, this);
        fetchTask.execute(mMovie.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        posterView = (ImageView) rootView.findViewById(R.id.detail_movie_poster);
        titleView = (TextView) rootView.findViewById(R.id.detail_movie_title);
        yearView = (TextView) rootView.findViewById(R.id.detail_movie_year);
        overviewView = (TextView) rootView.findViewById(R.id.detail_movie_overview);
        ratingView = (TextView) rootView.findViewById(R.id.detail_movie_rating);

        return rootView;
    }

    @Override
    public void onTaskComplete(Movie movie) {
        mMovie = movie;

        Picasso.with(getContext())
                .load(mMovie.getPoster_url())
                .into(posterView);

        titleView.setText(mMovie.getTitle());
        yearView.setText(mMovie.getReleaseYear());
        overviewView.setText(mMovie.getOverview());
        ratingView.setText(getString(R.string.detail_rating, mMovie.getVote_avg()));
    }
}
