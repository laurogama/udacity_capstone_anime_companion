package com.android.example.animecompanion.data;

import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

public interface IRepository {
    LiveData<List<Anime>> getTopAnime();

    void updateTopAnime(Integer page);

    LiveData<Anime> getAnime(Integer id);

    LiveData<List<Anime>> getFavorites();

    void insertAnime(Anime anime);

    void searchAnime(String query);

    LiveData<List<Anime>> getSearchResults();


}
