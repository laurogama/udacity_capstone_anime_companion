package com.android.example.animecompanion.data.api;

import android.util.Log;

import com.android.example.animecompanion.data.models.Anime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.example.animecompanion.data.api.IJikan.API_BASE_URL;

public class Jikan {
    final static String TAG = Jikan.class.getSimpleName();
    private static final String RATED = "r17";
    private Retrofit retrofit;

    public Jikan() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private IJikan load() {
        return retrofit.create(IJikan.class);
    }

    public void requestTopAnime(Integer page, Callback<JikanResponse> callback) {
        Call<JikanResponse> call = load().requestTopAnime(page);
        Log.d(TAG, call.request().url().toString());
        call.enqueue(callback);
    }

    public void requestAnime(Integer id, Callback<Anime> callback) {
        Call<Anime> call = load().requestAnime(id);
        Log.d(TAG, call.request().url().toString());
        call.enqueue(callback);
    }

    public void searchAnime(String query, Integer page, Callback<JikanResponse> callback) {
        Call<JikanResponse> call = load().searchAnime(query, page, RATED);
        Log.d(TAG, call.request().url().toString());
        call.enqueue(callback);
    }

    public void requestGenre(Integer genre, Integer page, Callback<GenreResponse> callback) {
        Call<GenreResponse> call = load().requestGenre(genre, page);
        Log.d(TAG, call.request().url().toString());
        call.enqueue(callback);
    }
}
