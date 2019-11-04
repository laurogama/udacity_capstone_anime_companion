package com.android.example.animecompanion.ui.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.animecompanion.data.models.Anime;

public class AnimeListAdapter extends ListAdapter<Anime, AnimeListAdapter.AnimeViewHolder> {

    public AnimeListAdapter() {
        super(Anime.diffCallback);
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = getItem(position);
        holder.bind(anime);
    }

    class AnimeViewHolder extends RecyclerView.ViewHolder {

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Anime anime) {

        }
    }
}
