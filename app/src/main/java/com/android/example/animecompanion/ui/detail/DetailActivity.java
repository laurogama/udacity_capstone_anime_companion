package com.android.example.animecompanion.ui.detail;

import android.os.Bundle;
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
import com.google.firebase.analytics.FirebaseAnalytics;

public class DetailActivity extends AppCompatActivity {
    public static final String ANIME_ID = "anime_id";
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding mBinding;
    private DetailViewModel detailViewModel;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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

        Menu menu = mBinding.toolbar.getMenu();
        MenuItem favorite = menu.findItem(R.id.action_favorite);
        favorite.setOnMenuItemClickListener(item -> {
            detailViewModel.updateFavorite(mBinding.getModel());
            item.setIcon(mBinding.getModel().isFavorite() ? R.drawable.ic_favorite_24dp :
                    R.drawable.ic_favorite_border_24dp);
            return true;
        });
    }

    private void logAnalyticsEventAnimeClick(Anime anime) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(anime.getId()));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, anime.getTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Anime");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void onAnimeChanged(Anime anime) {
        mBinding.setModel(anime);
        logAnalyticsEventAnimeClick(anime);
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
}
