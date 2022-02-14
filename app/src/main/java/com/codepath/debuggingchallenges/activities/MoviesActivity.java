package com.codepath.debuggingchallenges.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.debuggingchallenges.R;
import com.codepath.debuggingchallenges.adapters.MoviesAdapter;
import com.codepath.debuggingchallenges.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;

public class MoviesActivity extends AppCompatActivity {

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String TAG = "MoviesActivity";

    RecyclerView rvMovies;
    MoviesAdapter adapter;
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        // Create the adapter to convert the array to views
        MoviesAdapter adapter = new MoviesAdapter( movies, this);

        // Attach the adapter to a ListView
        rvMovies.setAdapter(adapter);

        // Set the layout manager
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        //fetchMovies();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON response) {
                Log.d(TAG, "onSuccess");
                try {
                    JSONArray moviesJson = response.jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(moviesJson));
                    Log.i(TAG, "Movies: " + movies.size());
                    adapter.notifyDataSetChanged();
                    //movies = Movie.fromJSONArray(moviesJson);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit jsonexception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(MoviesActivity.class.getSimpleName(), "Error retrieving movies: ", throwable);
            }
        });
    }


    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON response) {
                Log.d(TAG, "onSuccess");
                try {
                    JSONArray moviesJson = response.jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(moviesJson));
                    Log.i(TAG, "Movies: " + movies.size());
                    adapter.notifyDataSetChanged();
                    //movies = Movie.fromJSONArray(moviesJson);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit jsonexception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(MoviesActivity.class.getSimpleName(), "Error retrieving movies: ", throwable);
            }
        });
    }
}
