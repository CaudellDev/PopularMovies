package com.example.tyler.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
    private TextView runtimeView;
    private ListView trailerList;
    private ListView reviewList;
    private TrailerListAdapter trailerAdpt;
    private ReviewListAdapter reviewAdpt;

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
        runtimeView = (TextView) rootView.findViewById(R.id.detail_movie_runtime);
        trailerList = (ListView) rootView.findViewById(R.id.detail_trailer_list);
        reviewList = (ListView) rootView.findViewById(R.id.detail_review_list);

        return rootView;
    }

    /**
     * Source: http://stackoverflow.com/questions/9587754/how-to-add-two-listview-in-scrollview
     *
     * From what I can tell, what this function does is it expands the ListView
     * to the height of all of it's children. That way, the ListView doesn't need
     * to use the built-in scrolling functionality. This means that the ScrollView
     * the ListView is contained in, will scroll for it instead.
     *
     * @param listView - The ListView to be expanded to the height of all of it's children.
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            Log.d(LOG_TAG, "totalHeight: " + totalHeight);
        }
        totalHeight += 500;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onTaskComplete(Movie movie) {
        mMovie = movie;
        updateViews();
    }

    private void updateViews() {
        Picasso.with(getContext())
                .load(mMovie.getPoster_url())
                .into(posterView);

        titleView.setText(mMovie.getTitle());
        yearView.setText(mMovie.getReleaseYear());
        overviewView.setText(mMovie.getOverview());
        ratingView.setText(getString(R.string.detail_rating, mMovie.getVote_avg()));
        runtimeView.setText(mMovie.getRuntime());

        String[] trailerArray = mMovie.getTrailerList().toArray(new String[mMovie.getTrailerList().size()]);
        trailerAdpt = new TrailerListAdapter(getContext(), android.R.layout.activity_list_item, trailerArray);
        trailerList.setAdapter(trailerAdpt);

        String[] reviewArray = mMovie.getReviewList().toArray(new String[mMovie.getReviewList().size()]);
        reviewAdpt = new ReviewListAdapter(getContext(), android.R.layout.simple_list_item_2, reviewArray);
        reviewList.setAdapter(reviewAdpt);

        setListViewHeightBasedOnChildren(trailerList);
        setListViewHeightBasedOnChildren(reviewList);
    }
}
