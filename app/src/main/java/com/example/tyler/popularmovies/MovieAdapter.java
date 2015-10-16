package com.example.tyler.popularmovies;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 10/12/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(getContext());
        } else {
            imageView = (ImageView) convertView;
        }

        String posterUrl = getItem(position).getPoster_url();
        Log.d(LOG_TAG, "Poster URL: " + posterUrl);

        Picasso.with(getContext())
                .load(getItem(position).getPoster_url())
                .placeholder(R.drawable.default_film)
                .fit()
                .centerInside()
                .into(imageView);

        return imageView;
    }
}
