package com.android.example.animecompanion.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.ActivityDetailBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class DetailActivity extends AppCompatActivity {
    public static final String ANIME_ID = "anime_id";
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding mBinding;
    private DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mBinding.setViewModel(detailViewModel);

        if (getIntent().hasExtra(ANIME_ID)) {
            Integer animeId = getIntent().getIntExtra(ANIME_ID, -1);
            detailViewModel.requestAnime(animeId);
        }

        detailViewModel.getSelectedAnime().observe(this, this::onAnimeChanged);

        MobileAds.initialize(this, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mBinding.adView.loadAd(adRequest);
    }

    private void onAnimeChanged(Anime anime) {
        mBinding.setModel(anime);
        mBinding.toolbar.getMenu().findItem(R.id.action_favorite).setIcon(mBinding.getModel().isFavorite() ?
                R.drawable.ic_favorite_24dp :
                R.drawable.ic_favorite_border_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        Log.d(TAG, item.getTitle().toString());
        if (id == R.id.action_favorite) {
            detailViewModel.updateFavorite(mBinding.getModel());
            item.setIcon(mBinding.getModel().isFavorite() ? R.drawable.ic_favorite_24dp :
                    R.drawable.ic_favorite_border_24dp);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
