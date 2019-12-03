package com.android.example.animecompanion.data.api;

import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.data.models.MalUrl;

import java.util.List;

public class GenreResponse {
    private List<Anime> anime;
    private Integer item_count;
    private MalUrl malUrl;

    public List<Anime> getAnime() {
        return anime;
    }

    public void setAnime(List<Anime> anime) {
        this.anime = anime;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }

    public MalUrl getMalUrl() {
        return malUrl;
    }

    public void setMalUrl(MalUrl malUrl) {
        this.malUrl = malUrl;
    }
}
