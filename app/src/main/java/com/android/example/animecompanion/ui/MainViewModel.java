package com.android.example.animecompanion.ui;

import android.app.Application;

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

    boolean searchAnime(String query) {
        mRepository.searchAnime(query);
        return false;
    }

    LiveData<List<Anime>> getSearchResults() {
        return mRepository.getSearchResults();
    }
}
