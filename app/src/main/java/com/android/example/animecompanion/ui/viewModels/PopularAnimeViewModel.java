package com.android.example.animecompanion.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.Repository;
import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

public class PopularAnimeViewModel extends AndroidViewModel {
    private Repository mRepository;

    public PopularAnimeViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(application);
    }

    public LiveData<List<Anime>> getTopAnime() {
        Integer currentPage = 1;
        return mRepository.getTopAnime(currentPage);
    }

    public LiveData<Anime> getSelectedAnime() {
        return mRepository.getSelectedAnime();
    }
}
