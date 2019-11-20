package com.android.example.animecompanion.ui.viewModels;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.Repository;
import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.ui.detail.DetailActivity;

import java.util.List;

public class PopularAnimeViewModel extends AndroidViewModel {
    private Repository mRepository;

    public PopularAnimeViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(application);
    }

    public void onClick(View view, Anime anime) {
        Intent intent = new Intent();
        intent.setClass(view.getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.ANIME_ID, anime.getId());
        view.getContext().startActivity(intent);
//        mRepository.setSelectedAnime(anime);
    }

    public LiveData<List<Anime>> getTopAnime() {
        return mRepository.getTopAnime();
    }

    public void updateTopAnime(Integer page) {
        mRepository.updateTopAnime(page);
    }

    public LiveData<Anime> getSelectedAnime() {
        return mRepository.getSelectedAnime();
    }
}
