package com.android.example.animecompanion.ui;

import android.content.Intent;
import android.view.View;

import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.ui.detail.DetailActivity;

public class ClickUtil {

    public static void onAnimeClicked(View view, Anime anime) {
        Intent intent = new Intent();
        intent.setClass(view.getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.ANIME_ID, anime.getId());
        view.getContext().startActivity(intent);
    }
}
