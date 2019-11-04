package com.android.example.animecompanion.data.api;

import com.android.example.animecompanion.data.models.Anime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IJikan {
    String API_BASE_URL = "https://api.jikan.moe/v3/";

    @GET(API_BASE_URL + "top/anime/{page}")
    Call<JikanResponse> requestTopAnime(@Path("page") Integer page);

    @GET(API_BASE_URL + "anime/{id}")
    Call<Anime> requestAnime(@Path("id") Integer id);
}
