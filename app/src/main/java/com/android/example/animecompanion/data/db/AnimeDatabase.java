package com.android.example.animecompanion.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.example.animecompanion.data.models.Anime;

@Database(entities = {Anime.class}, version = 1, exportSchema = false)
public abstract class AnimeDatabase extends RoomDatabase {
    private static volatile AnimeDatabase INSTANCE;

    public static AnimeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AnimeDatabase.class, "anime_database").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AnimeDao animeDao();
}
