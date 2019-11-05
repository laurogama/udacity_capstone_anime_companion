package com.android.example.animecompanion.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.AnimeListItemBinding;
import com.android.example.animecompanion.ui.MainViewModel;

public class AnimeListAdapter extends ListAdapter<Anime, AnimeListAdapter.AnimeViewHolder> {
    private MainViewModel viewModel;

    public AnimeListAdapter(MainViewModel viewModel) {
        super(Anime.diffCallback);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnimeListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.anime_list_item, parent, false);
        return new AnimeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class AnimeViewHolder extends RecyclerView.ViewHolder {

        AnimeListItemBinding binding;

        public AnimeViewHolder(@NonNull AnimeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Anime anime) {
            this.binding.setModel(anime);
        }
    }
}
