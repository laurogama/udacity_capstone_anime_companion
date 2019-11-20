package com.android.example.animecompanion.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.FragmentPopularBinding;
import com.android.example.animecompanion.ui.adapters.AnimeListAdapter;
import com.android.example.animecompanion.ui.viewModels.PopularAnimeViewModel;

import java.util.List;

public class PopularAnimeFragment extends Fragment {
    private AnimeListAdapter mTopListAdapter;
    private PopularAnimeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPopularBinding mBinding = FragmentPopularBinding.inflate(inflater, container,
                false);
        mBinding.rvTop.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        viewModel = ViewModelProviders.of(this).get(PopularAnimeViewModel.class);
        mTopListAdapter = new AnimeListAdapter(viewModel);
        mBinding.setAdapter(mTopListAdapter);
        viewModel.getTopAnime().observe(this, this::onTopAnimeChanged);
        viewModel.updateTopAnime(1);
        return mBinding.getRoot();
    }

    private void onTopAnimeChanged(List<Anime> anime) {
        if (anime != null && !anime.isEmpty()) {
            mTopListAdapter.submitList(anime);
        }
    }
}
