package com.android.example.animecompanion.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.Repository;
import com.android.example.animecompanion.data.models.Anime;

public class DetailViewModel extends AndroidViewModel {
    private Repository mRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(application);
    }

    LiveData<Anime> getSelectedAnime() {
        return mRepository.getSelectedAnime();
    }

    public void requestAnime(Integer animeId) {
        mRepository.getAnime(animeId);
    }

    public void updateFavorite(Anime model) {
        model.toogleFavorite();
        mRepository.updateFavorite(model.getId(), model.isFavorite());
    }
}
