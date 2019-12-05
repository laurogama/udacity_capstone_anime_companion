package com.android.example.animecompanion.data;

import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

public interface IRepository {
    /**
     * Retrieves a LiveData of a List of anime in order of rank
     *
     * @return
     */
    LiveData<List<Anime>> getTopAnime(Integer page);

    void updateTopAnime(Integer page);

    void getAnime(Integer id);

    void updateFavorite(long id, boolean isFavorite);

    void searchAnime(String query, Integer page);

    LiveData<List<Anime>> getSearchResults();

    LiveData<Anime> getSelectedAnime();

    void setSelectedAnime(Anime anime);

    LiveData<List<Anime>> getMyAnimeList();

    void selectGenre(Integer genreId);

    LiveData<List<Anime>> getAnimeByGenre();
}
