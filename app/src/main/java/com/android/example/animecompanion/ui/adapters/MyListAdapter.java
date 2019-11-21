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
import com.android.example.animecompanion.ui.viewModels.MyListViewModel;

public class MyListAdapter extends ListAdapter<Anime, MyListAdapter.MyListViewHolder> {

    private final MyListViewModel viewModel;

    public MyListAdapter(MyListViewModel viewModel) {
        super(Anime.diffCallback);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MylistItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.mylist_item, parent, false);
        return new MyListViewHolder(binding, this.viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class MyListViewHolder extends RecyclerView.ViewHolder {
        private final MylistItemBinding binding;

        MyListViewHolder(@NonNull MylistItemBinding binding, MyListViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setViewModel(viewModel);

        }

        void bind(Anime anime) {
            this.binding.setModel(anime);
        }
    }
}