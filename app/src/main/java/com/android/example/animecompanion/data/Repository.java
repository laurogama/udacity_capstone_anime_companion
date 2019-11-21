package com.android.example.animecompanion.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.example.animecompanion.data.api.Jikan;
import com.android.example.animecompanion.data.api.JikanResponse;
import com.android.example.animecompanion.data.db.AnimeDao;
import com.android.example.animecompanion.data.db.AnimeDatabase;
import com.android.example.animecompanion.data.db.AppExecutors;
import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository implements IRepository {

    private final static String TAG = Repository.class.getSimpleName();
    private static Repository sInstance;
    private final AnimeDao mAnimeDao;
    private MutableLiveData<List<Anime>> searchResults;
    private MediatorLiveData<List<Anime>> mTopAnime = new MediatorLiveData<>();
    private MutableLiveData<Anime> mSelectedAnime = new MutableLiveData<>();
    private Jikan jikan;

    private Repository(Application application) {
        AnimeDatabase db = AnimeDatabase.getDatabase(application.getApplicationContext());
        mAnimeDao = db.animeDao();
        jikan = new Jikan();
        mTopAnime.addSource(mAnimeDao.getTop(), this::onTopAnimeChanged);
    }

    public static Repository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (Repository.class) {
                sInstance = new Repository(application);
            }
        }
        return sInstance;
    }

    private void onTopAnimeChanged(List<Anime> animes) {
        mTopAnime.postValue(animes);
    }

    @Override
    public LiveData<List<Anime>> getTopAnime(Integer page) {
        Log.d(TAG, "getting top anime from Room");
        updateTopAnime(page);
        return mTopAnime;
    }

    @Override
    public void updateTopAnime(Integer page) {
        jikan.requestTopAnime(page, new Callback<JikanResponse>() {
            @Override
            public void onResponse(Call<JikanResponse> call, Response<JikanResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.message());
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        mAnimeDao.updateAll(response.body().getTop());
                        mTopAnime.postValue(response.body().getTop());
                    });
                }
            }

            @Override
            public void onFailure(Call<JikanResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getAnime(Integer id) {
        if (id == -1) {
            return;
        }
        AppExecutors.getInstance().diskIO().execute(() -> {
            Anime anime = mAnimeDao.findById(id);
            if (anime == null || !anime.isFull()) {
                requestAnime(id);
            } else {
                setSelectedAnime(anime);
            }

        });

    }

    private void requestAnime(Integer id) {
        jikan.requestAnime(id, new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                if (response.isSuccessful()) {
                    Anime anime = response.body();
                    if (anime != null) {
                        anime.setFull(true);
                        Log.d(TAG, response.message());
                        AppExecutors.getInstance().diskIO().execute(() -> {
                            mAnimeDao.updateAnime(anime);
                            mSelectedAnime.postValue(anime);
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public LiveData<List<Anime>> getFavorites() {
        return null;
    }

    @Override
    public void insertAnime(Anime anime) {

    }

    @Override
    public void searchAnime(String query) {
        setSearchResults(mAnimeDao.findByTitle(query));
    }

    @Override
    public LiveData<List<Anime>> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Anime> searchResults) {
        this.searchResults.postValue(searchResults);
    }

    @Override
    public LiveData<Anime> getSelectedAnime() {
        return mSelectedAnime;
    }

    @Override
    public void setSelectedAnime(Anime anime) {
        mSelectedAnime.postValue(anime);
    }

    @Override
    public void updateAnime(Anime anime) {
        ((Runnable) () -> {
            mAnimeDao.updateAnime(anime);
            mSelectedAnime.postValue(anime);
        }
        ).run();

    }

    @Override
    public LiveData<List<Anime>> getMyAnimeList() {
        return mAnimeDao.getMyAnimeList();
    }
}
