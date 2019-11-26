package com.android.example.animecompanion.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.MylistItemBinding;

public class MyListAdapter extends ListAdapter<Anime, MyListAdapter.MyListViewHolder> {

    public MyListAdapter() {
        super(Anime.diffCallback);
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MylistItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.mylist_item, parent, false);
        return new MyListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class MyListViewHolder extends RecyclerView.ViewHolder {
        private final MylistItemBinding binding;

        MyListViewHolder(@NonNull MylistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(Anime anime) {
            this.binding.setModel(anime);
        }
    }
}