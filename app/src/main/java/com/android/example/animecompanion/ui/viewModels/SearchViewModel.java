package com.android.example.animecompanion.ui.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.animecompanion.data.IRepository;
import com.android.example.animecompanion.data.Repository;
import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private final IRepository mRepository;

    public SearchViewModel(Application application) {
        super(application);
        mRepository = Repository.getInstance(application);
    }

    public boolean searchAnime(String query) {
        mRepository.searchAnime(query, 1);
        return false;
    }

    public LiveData<List<Anime>> getSearchResults() {
        return mRepository.getSearchResults();
    }
}
