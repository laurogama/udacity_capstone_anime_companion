package com.android.example.animecompanion.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.Repository;
import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

public class MyListViewModel extends AndroidViewModel {
    private Repository mRepository;

    public MyListViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(application);
    }


    public LiveData<List<Anime>> getMyAnimeList() {
        return mRepository.getMyAnimeList();
    }
}
