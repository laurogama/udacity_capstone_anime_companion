package com.android.example.animecompanion.data.api;

import com.android.example.animecompanion.data.models.Anime;

import java.util.List;

public class JikanResponse {
    private List<Anime> top;
    private List<Anime> results;

    public List<Anime> getTop() {
        return top;
    }

    public List<Anime> getResults() {
        return results;
    }
}
