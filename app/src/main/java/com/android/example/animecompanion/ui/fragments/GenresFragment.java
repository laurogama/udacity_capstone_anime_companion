package com.android.example.animecompanion.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.example.animecompanion.databinding.FragmentGenresBinding;
import com.android.example.animecompanion.ui.viewModels.GenresViewModel;

public class GenresFragment extends Fragment {
    private FragmentGenresBinding mBinding;
    private GenresViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGenresBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(GenresViewModel.class);

        return mBinding.getRoot();
    }
}
