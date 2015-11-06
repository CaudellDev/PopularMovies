package com.example.tyler.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by Tyler on 11/5/2015.
 */
public class ReviewListAdapter extends ArrayAdapter<String> {
    public ReviewListAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        } else {
            view = convertView;
        }

        TextView title = (TextView) view.findViewById(android.R.id.text1);
        TextView content = (TextView) view.findViewById(android.R.id.text2);

        String[] review = Movie.convertReviewStr(getItem(position));

        title.setText(review[0]);
        content.setText(review[1]);

        return view;
    }
}
