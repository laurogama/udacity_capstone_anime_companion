package com.android.example.animecompanion.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.Repository;
import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Repository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(application);
    }

    LiveData<List<Anime>> getTopAnime() {
        return mRepository.getTopAnime();
    }

    void updateTopAnime(Integer page) {
        mRepository.updateTopAnime(page);
    }

    boolean searchAnime(String query) {
        mRepository.searchAnime(query);
        return false;
    }

    LiveData<List<Anime>> getSearchResults() {
        return mRepository.getSearchResults();
    }

    public void onClick(Anime anime) {
        Log.d("MainViewModel", "Clicked");
        mRepository.setSelectedAnime(anime);
    }

    public LiveData<Anime> getSelectedAnime() {
        return mRepository.getSelectedAnime();
    }

}
