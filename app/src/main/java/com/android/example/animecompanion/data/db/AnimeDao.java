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
    @Query("SELECT * from anime ORDER BY rank")
    public LiveData<List<Anime>> getTop();

    @Delete
    void delete(Anime anime);

    @Query("SELECT * from anime WHERE id=:id LIMIT 1")
    Anime findById(Integer id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Anime anime);

    @Query("SELECT * from anime WHERE title like :title")
    List<Anime> findByTitle(String title);

    @Update
    void updateAll(List<Anime> animeList);
}
