package com.example.tyler.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Tyler on 9/17/2015.
 */
public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private static final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private static final String imgXXS = "w92";
    private static final String imgXS = "w154";
    private static final String imgS = "w185";
    private static final String imgM = "w342";
    private static final String imgL = "w500";
    private static final String imgXL = "w780";
    private static final String imgOrg = "original";

    private Context mContext;
    private TaskCallback mCallback;

    public FetchMovieTask(Context context, TaskCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    private ArrayList<Movie> getMovieDataFromJson(String movieJsonStr) throws JSONException {

        // Json Object Name (JON) for the name/value sets in the movie Json string.
        final String JON_PAGE = "page";
        final String JON_RESULTS = "results";

        final String JON_OVERVIEW = "overview";
        final String JON_RELEASE_DATE = "release_date";
        final String JON_POSTER_PATH = "poster_path";
        final String JON_POPULARITY = "popularity";
        final String JON_TITLE = "title";
        final String JON_VOTE_AVERAGE = "vote_average";

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(JON_RESULTS);

        // Loop through the lines of the file and get the JSON Objects.
        ArrayList<Movie> movies = new ArrayList<>(movieArray.length());
        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject currMovie = movieArray.getJSONObject(i);

            String overview = currMovie.getString(JON_OVERVIEW);
            String release_date = currMovie.getString(JON_RELEASE_DATE);
            String poster_path = Utility.getPosterUrl(currMovie.getString(JON_POSTER_PATH));
            float popularity = (float) currMovie.getDouble(JON_POPULARITY);
            String title = currMovie.getString(JON_TITLE);
            float vote_average = (float) currMovie.getDouble(JON_VOTE_AVERAGE);

            Movie movie = new Movie(title, poster_path, overview, popularity, vote_average, release_date);
            movies.add(movie);
        }

        return movies;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_PARAM = "sort_by";
            final String API_PARAM = "api_key";

            String sort = "popularity.desc";
            String api_key = Utility.getKey(mContext);

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sort)
                    .appendQueryParameter(API_PARAM, api_key)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            movieJsonStr = buffer.toString();
            return getMovieDataFromJson(movieJsonStr);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        mCallback.onTaskComplete(movies);
    }

    public interface TaskCallback {
        void onTaskComplete(ArrayList<Movie> movies);
    }
}
