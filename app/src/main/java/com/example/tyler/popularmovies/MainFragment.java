package com.example.tyler.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements FetchMovieListTask.TaskCallback {

    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) view.findViewById(R.id.grid_movie);
        updateMovies(); // Initial fetch.

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Temporary refresh button.
        if (id == R.id.action_refresh) {
            updateMovies();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateMovies() {
        FetchMovieListTask fetchMovieListTask = new FetchMovieListTask(getContext(), this);

        // TODO: Once SettingsActivity works, pull from there.
        String sort = Utility.getSort(getContext());

        fetchMovieListTask.execute(sort);
    }

    @Override
    public void onTaskComplete(final ArrayList<Movie> movies) {
        if (mMovieAdapter == null) {
            // If it's null, it's the first time using it so we need to do some initializing.
            mMovieAdapter = new MovieAdapter(getContext(), R.layout.image_item, movies);
            mGridView.setAdapter(mMovieAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ((FragmentCallback) getActivity()).onItemClick(movies.get(position));
                }
            });
        }
        mMovieAdapter.notifyDataSetChanged();
    }

    public interface FragmentCallback {
        void onItemClick(Movie movie);
    }
}
