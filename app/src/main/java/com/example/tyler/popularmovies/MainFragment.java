package com.example.tyler.popularmovies;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements FetchMovieTask.TaskCallback {

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

        updateMovies();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            updateMovies();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateMovies() {
        FetchMovieTask fetchMovieTask = new FetchMovieTask(getContext(), this);
        fetchMovieTask.execute("");
    }

    @Override
    public void onTaskComplete(ArrayList<Movie> movies) {
        if (mMovieAdapter == null) {
            mMovieAdapter = new MovieAdapter(getContext(), R.layout.image_item, movies);
            mGridView.setAdapter(mMovieAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Snackbar.make(view, "You have seleted " + mMovieAdapter.getItem(position).getTitle() + "!", Snackbar.LENGTH_LONG).show();
                }
            });
        }
        mMovieAdapter.notifyDataSetChanged();
    }
}
