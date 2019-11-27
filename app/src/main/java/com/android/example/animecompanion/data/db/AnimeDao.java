package com.android.example.animecompanion.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

@Dao
public interface AnimeDao {

    //Rank equals 0 are NSFW titles so we filter those
    @Query("SELECT * from anime WHERE rank > 0 ORDER BY popularity")
    public LiveData<List<Anime>> getTop();

    @Delete
    void delete(Anime anime);

    @Query("SELECT * from anime WHERE id=:id")
    public Anime findById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Anime anime);

    @Query("SELECT * from anime WHERE title like :title")
    List<Anime> findByTitle(String title);

    @Query("UPDATE anime SET favorite = :favorite WHERE id = :tid")
    int updateFavorite(long tid, boolean favorite);

    @Update
    void updateAll(List<Anime> animeList);

    @Update
    void updateAnime(Anime anime);

    @Query("SELECT * from anime WHERE favorite=1")
    LiveData<List<Anime>> getMyAnimeList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Anime> top);
}
