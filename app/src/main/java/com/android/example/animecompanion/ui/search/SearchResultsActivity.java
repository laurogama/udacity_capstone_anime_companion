package com.android.example.animecompanion.ui.search;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.SearchResultsActivityBinding;
import com.android.example.animecompanion.ui.adapters.AnimeListAdapter;
import com.android.example.animecompanion.ui.viewModels.SearchViewModel;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    public static final String QUERY = "query_extra";
    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    private SearchResultsActivityBinding mBinding;
    private SearchViewModel mViewModel;
    private AnimeListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.search_results_activity);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mAdapter = new AnimeListAdapter();
        mBinding.rvSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        mBinding.setAdapter(mAdapter);
        mViewModel.getSearchResults().observe(this, this::onSearchResultsChanged);
        if (getIntent().hasExtra(QUERY)) {
            mViewModel.searchAnime(getIntent().getStringExtra(QUERY));
        }
    }

    private void onSearchResultsChanged(List<Anime> animeList) {
        mAdapter.submitList(animeList);
    }

}

