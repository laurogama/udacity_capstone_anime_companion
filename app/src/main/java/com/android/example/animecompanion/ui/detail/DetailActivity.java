package com.android.example.animecompanion.ui.detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.databinding.ActivityDetailBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        binding.setViewModel(detailViewModel);
        detailViewModel.getSelectedAnime().observe(this, anime -> binding.setModel(anime));
        MobileAds.initialize(this, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        binding.adView.loadAd(adRequest);
    }
}
