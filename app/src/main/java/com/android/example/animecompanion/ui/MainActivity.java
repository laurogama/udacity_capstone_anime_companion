package com.android.example.animecompanion.ui;

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
import com.android.example.animecompanion.ui.fragments.PopularAnimeFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding mainBinding;
    private MainViewModel viewModel;
    private final SearchView.OnQueryTextListener mOnQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return viewModel.searchAnime(query);
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            };

    private SearchView searchView;

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
        mainBinding.viewPager.setAdapter(mPagerAdapter);
        //optimisation
        mainBinding.viewPager.setOffscreenPageLimit(3);
//        mainBinding.viewPager.setPagingEnabled(false);
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(mOnQueryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tab_top:
                Log.d(TAG, "Go to Top");
                mainBinding.viewPager.setCurrentItem(0);
                break;
            case R.id.tab_genres:
                Log.d(TAG, "Go to Genres");
                mainBinding.viewPager.setCurrentItem(3);
                break;
            case R.id.tab_my_list:
                Log.d(TAG, "Go to My List");
                mainBinding.viewPager.setCurrentItem(2);
                break;
        }
        return false;
    }
}
