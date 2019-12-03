package com.android.example.animecompanion.data.api;

import com.android.example.animecompanion.data.models.Anime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IJikan {
    String API_BASE_URL = "https://api.jikan.moe/v3/";

    @GET(API_BASE_URL + "top/anime/{page}/bypopularity")
    Call<JikanResponse> requestTopAnime(@Path("page") Integer page);

    @GET(API_BASE_URL + "anime/{id}")
    Call<Anime> requestAnime(@Path("id") Integer id);

    @GET(API_BASE_URL + "search/anime/")
    Call<JikanResponse> searchAnime(@Query("q") String query, @Query("page") Integer page,
                                    @Query("rated") String rated);

    @GET(API_BASE_URL + "genre/anime/{genre}/{page}")
    Call<GenreResponse> requestGenre(@Path("genre") Integer genre, @Path("page") Integer page);

}
