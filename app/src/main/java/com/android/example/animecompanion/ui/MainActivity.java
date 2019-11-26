package com.android.example.animecompanion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.databinding.ActivityMainBinding;
import com.android.example.animecompanion.ui.adapters.BottomBarAdapter;
import com.android.example.animecompanion.ui.fragments.GenresFragment;
import com.android.example.animecompanion.ui.fragments.MyListFragment;
import com.android.example.animecompanion.ui.fragments.PopularAnimeFragment;
import com.android.example.animecompanion.ui.search.SearchResultsActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding mainBinding;
    private SearchView searchView;
    private final SearchView.OnQueryTextListener mOnQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.clearFocus();
                    Log.d(TAG, "searching: " + query);
                    openSearchActivity(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            };
    private MainViewModel viewModel;

    @BindingAdapter("image")
    public static void setThumbnail(ImageView view, String thumbnailSrc) {
        Glide.with(view).load(thumbnailSrc).placeholder(R.drawable.thumbnail_placeholder_foreground).into(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainBinding.setVm(viewModel);
        BottomBarAdapter mPagerAdapter = new BottomBarAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mPagerAdapter.addFragments(new PopularAnimeFragment());
        mPagerAdapter.addFragments(new GenresFragment());
        mPagerAdapter.addFragments(new MyListFragment());
        mainBinding.viewPager.setAdapter(mPagerAdapter);
        mainBinding.viewPager.setOffscreenPageLimit(3);
        Menu menu = mainBinding.toolbar.getMenu();
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setOnQueryTextListener(mOnQueryTextListener);
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tab_top:
                Log.d(TAG, "Go to Top");
                mainBinding.viewPager.setCurrentItem(0);
                mainBinding.toolbar.setTitle(R.string.app_name);
                break;
            case R.id.tab_genres:
                Log.d(TAG, "Go to Genres");
                mainBinding.viewPager.setCurrentItem(1);
                mainBinding.toolbar.setTitle(R.string.genres);
                break;
            case R.id.tab_my_list:
                Log.d(TAG, "Go to My List");
                mainBinding.viewPager.setCurrentItem(2);
                mainBinding.toolbar.setTitle(R.string.my_list);
                break;
        }
        return false;
    }

    private void openSearchActivity(String query) {
        Intent intent = new Intent();
        intent.setClass(this, SearchResultsActivity.class);
        intent.putExtra(SearchResultsActivity.QUERY, query);
        startActivity(intent);
    }
}
