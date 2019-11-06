package com.android.example.animecompanion.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.example.animecompanion.data.api.Jikan;
import com.android.example.animecompanion.data.api.JikanResponse;
import com.android.example.animecompanion.data.db.AnimeDao;
import com.android.example.animecompanion.data.db.AnimeDatabase;
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
    private MutableLiveData<List<Anime>> mTopAnime = new MutableLiveData<>();
    private MutableLiveData<Anime> mSelectedAnime = new MutableLiveData<>();
    private Jikan jikan;

    private Repository(Application application) {
        AnimeDatabase db = AnimeDatabase.getDatabase(application.getApplicationContext());
        mAnimeDao = db.animeDao();
        jikan = new Jikan();
    }

    public static Repository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (Repository.class) {
                sInstance = new Repository(application);
            }
        }
        return sInstance;
    }

    @Override
    public LiveData<List<Anime>> getTopAnime() {
        return mTopAnime;
    }

    @Override
    public void updateTopAnime(Integer page) {
        jikan.requestTopAnime(page, new Callback<JikanResponse>() {
            @Override
            public void onResponse(Call<JikanResponse> call, Response<JikanResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.message());
                    new updateAsyncTask(mAnimeDao).execute(response.body().getTop());
                    mTopAnime.postValue(response.body().getTop());
                }
            }

            @Override
            public void onFailure(Call<JikanResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void onAnimeListChanged(List<Anime> animeList) {

    }

    @Override
    public LiveData<Anime> getAnime(Integer id) {
        return null;
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

    private static class updateAsyncTask extends AsyncTask<List<Anime>, Void, Void> {

        private final AnimeDao mAsyncTaskDao;

        updateAsyncTask(AnimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Anime>... params) {
            mAsyncTaskDao.updateAll(params[0]);
            return null;
        }
    }
}
