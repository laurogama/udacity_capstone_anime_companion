package com.android.example.animecompanion.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.ActivityMainBinding;
import com.android.example.animecompanion.ui.adapters.AnimeListAdapter;
import com.bumptech.glide.Glide;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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
    private AnimeListAdapter mTopListAdapter;
    private SearchView searchView;

    @BindingAdapter("image")
    public static void setThumbnail(ImageView view, String thumbnailSrc) {
        Glide.with(view).load(thumbnailSrc).placeholder(R.drawable.thumbnail_placeholder_foreground).into(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setVm(viewModel);
        binding.rvTop.setLayoutManager(new GridLayoutManager(this, 2));
        mTopListAdapter = new AnimeListAdapter(viewModel);
        binding.setAdapter(mTopListAdapter);
        viewModel.getTopAnime().observe(this, this::onTopAnimeChanged);
        viewModel.updateTopAnime(1);
    }

    private void onTopAnimeChanged(List<Anime> anime) {
        if (anime != null && !anime.isEmpty()) {
            mTopListAdapter.submitList(anime);
        }
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
}
