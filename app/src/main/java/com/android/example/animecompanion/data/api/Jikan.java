package com.android.example.animecompanion.data.api;

import android.util.Log;

import com.android.example.animecompanion.data.models.Anime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.example.animecompanion.data.api.IJikan.API_BASE_URL;

public class Jikan {
    final static String TAG = Jikan.class.getSimpleName();
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
        System.out.println(call.request().url());
        call.enqueue(callback);
    }

    public void requestAnime(Integer id, Callback<Anime> callback) {
        Call<Anime> call = load().requestAnime(id);
        System.out.println(call.request().url());
        call.enqueue(callback);
    }
}
