package com.example.tyler.popularmovies;

import android.content.Context;

/**
 * Created by Tyler on 9/17/2015.
 */
public class Utility {

    private static final String posterUrl = "http://image.tmdb.org/t/p/w185";

    public static String getKey(Context context) {
        return context.getString(R.string.api_key);
    }

    public static String getPosterUrl(String urlEnd) {
        return posterUrl + urlEnd;
    }
}
