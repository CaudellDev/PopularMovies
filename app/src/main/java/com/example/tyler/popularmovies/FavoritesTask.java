package com.example.tyler.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoritesTask extends AsyncTask<ArrayList<Movie>, Void, Void> {

    private static final String LOG_TAG = FavoritesTask.class.getSimpleName();
    public static final String FAV_NAME = "FAVORITES_NAME";
    public static final String FAV_KEY = "FAVORITES_KEY";

    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";
    public static final String CLEAR = "CLEAR";
    public static final String EMPTY = "";

    private Context mContext;
    private String mAction;
    private TaskCompleteCallback mCallback;

    public FavoritesTask(Context context, String action, TaskCompleteCallback callback) {
        mContext = context;
        mAction = action;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        Log.v(LOG_TAG, "FavoritesTask: onPreExecute() started.");
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(ArrayList<Movie>... params) {
        Log.v(LOG_TAG, "FavoritesTask: doInBackground() started.");

        SharedPreferences prefs = mContext.getSharedPreferences(FAV_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        ArrayList<Movie> movies = (params.length != 0) ? params[0] : null; // Just in case there are no parameters...
        ArrayList<Movie> savedMovies;
        Gson gson = new Gson();

        String favoritesJSON;
        String movieJSON;

        if (prefs.contains(FAV_KEY)) {
            favoritesJSON = prefs.getString(FAV_KEY, "");
            Log.d(LOG_TAG, "Favorites are saved: " + favoritesJSON);

            Type typeToken = new TypeToken<ArrayList<Movie>>(){}.getType();
            savedMovies = gson.fromJson(favoritesJSON, typeToken);
        } else {
            Log.d(LOG_TAG, "No favorites saved yet.");
            savedMovies = new ArrayList<>();
        }

        switch (mAction) {
            case ADD:
                Movie add = movies.get(0);

                if (!Utility.isFavorite(mContext, add.getId())) {
                    Utility.setFavorite(mContext, add.getId(), true);
                    savedMovies.add(add);
                    movieJSON = gson.toJson(savedMovies);
                    Log.d(LOG_TAG, "new movie json string: " + movieJSON);
                    Log.d(LOG_TAG, "savedMovies.size(): " + savedMovies.size());

                    editor.putString(FAV_KEY, movieJSON);
                }
                editor.commit();
                break;
            case REMOVE:
                Movie remove = movies.get(0);

                if (Utility.isFavorite(mContext, remove.getId())) {
                    Utility.setFavorite(mContext, remove.getId(), false);
                    savedMovies.remove(remove);
                    movieJSON = gson.toJson(savedMovies);
                    Log.d(LOG_TAG, "new movie json string: " + movieJSON);

                    editor.putString(FAV_KEY, movieJSON);
                }
                editor.commit();
                break;
            case CLEAR:
                editor.clear();
                editor.apply();
                break;
            case EMPTY:
            default:
                Log.w(LOG_TAG, "Action \"" + mAction + "\" not recognized.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.v(LOG_TAG, "FavoritesTask: onPostExecute() started.");
        mCallback.onTaskComplete();
    }

    public interface TaskCompleteCallback {
        void onTaskComplete();
    }
}
