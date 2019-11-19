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
        return mRepository.getTopAnime();
    }

    public void updateTopAnime(Integer page) {
        mRepository.updateTopAnime(page);
    }

    public void onClick(Anime anime) {
        mRepository.setSelectedAnime(anime);
        mRepository.getAnime(anime.getId());
    }

    public LiveData<Anime> getSelectedAnime() {
        return mRepository.getSelectedAnime();
    }
}
