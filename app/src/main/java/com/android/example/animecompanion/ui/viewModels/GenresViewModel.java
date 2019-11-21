package com.android.example.animecompanion.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.example.animecompanion.data.Repository;

public class GenresViewModel extends AndroidViewModel {
    private Repository mRepository;

    public GenresViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(application);
    }
}
