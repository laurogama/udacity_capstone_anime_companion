package com.android.example.animecompanion.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.example.animecompanion.databinding.FragmentMyListBinding;
import com.android.example.animecompanion.ui.viewModels.MyListViewModel;

public class MyListFragment extends Fragment {
    private FragmentMyListBinding myBinding;
    private MyListViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myBinding = FragmentMyListBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(MyListViewModel.class);
        return myBinding.getRoot();
    }
}
