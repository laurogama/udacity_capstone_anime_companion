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
        new GetAnimeAsyncTask(mAnimeDao, anime -> {
            if (anime == null || !anime.isFull()) {
                requestAnime(id);
            } else {
                setSelectedAnime(anime);
            }
        }).execute(id);


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
                            mAnimeDao.insert(anime);
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
    public void searchAnime(String query, Integer page) {
        setSearchResults(mAnimeDao.findByTitle(query));
        jikan.searchAnime(query, page, new Callback<JikanResponse>() {
            @Override
            public void onResponse(Call<JikanResponse> call, Response<JikanResponse> response) {
                if (response.isSuccessful()) {
                    setSearchResults(response.body().getResults());
                    AppExecutors.getInstance().diskIO().execute(
                            () -> mAnimeDao.updateAll(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(Call<JikanResponse> call, Throwable t) {

            }
        });

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
        Log.d(TAG, "Updating selected anime to: " + anime.getId());
        mSelectedAnime.postValue(anime);
    }

    @Override
    public void updateAnime(Anime anime) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            mAnimeDao.updateAnime(anime);
            mSelectedAnime.postValue(anime);
        });
    }

    @Override
    public LiveData<List<Anime>> getMyAnimeList() {
        return mAnimeDao.getMyAnimeList();
    }

    private interface AsyncCallback {
        void ontaskCompleted(Anime anime);
    }

    private static class GetAnimeAsyncTask extends AsyncTask<Integer, Void, Anime> {

        private final AnimeDao mAsyncTaskDao;
        private AsyncCallback callback;

        GetAnimeAsyncTask(AnimeDao dao, AsyncCallback callback) {
            mAsyncTaskDao = dao;
            this.callback = callback;
        }

        @Override
        protected Anime doInBackground(final Integer... params) {
            return mAsyncTaskDao.findById(params[0]);
        }

        @Override
        protected void onPostExecute(Anime anime) {
            callback.ontaskCompleted(anime);
        }
    }
}
