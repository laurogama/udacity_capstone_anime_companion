package com.android.example.animecompanion.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.FragmentMyListBinding;
import com.android.example.animecompanion.ui.adapters.MyListAdapter;
import com.android.example.animecompanion.ui.viewModels.MyListViewModel;

import java.util.List;

public class MyListFragment extends Fragment {
    private static final String TAG = MyListFragment.class.getSimpleName();
    private FragmentMyListBinding mBinding;
    private MyListViewModel mViewModel;
    private MyListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMyListBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(MyListViewModel.class);
        mAdapter = new MyListAdapter();
        mBinding.setAdapter(mAdapter);
        mViewModel.getMyAnimeList().observe(getActivity(), this::onMyListChanged);
        return mBinding.getRoot();
    }

    private void onMyListChanged(List<Anime> animeList) {
        if (animeList != null) {
            Log.d(TAG, animeList.toString());
            mAdapter.submitList(animeList);
        }
    }
}
