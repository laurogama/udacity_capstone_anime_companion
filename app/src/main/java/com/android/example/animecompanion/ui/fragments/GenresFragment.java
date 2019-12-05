package com.android.example.animecompanion.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.example.animecompanion.R;
import com.android.example.animecompanion.data.models.Anime;
import com.android.example.animecompanion.databinding.FragmentGenresBinding;
import com.android.example.animecompanion.ui.adapters.AnimeListAdapter;
import com.android.example.animecompanion.ui.viewModels.GenresViewModel;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GenresFragment extends Fragment {
    private static final String TAG = GenresFragment.class.getSimpleName();
    private static final Map<String, Integer> genresMap = Stream.of(
            new SimpleEntry<>("Action", 1),
            new SimpleEntry<>("Adventure", 2),
            new SimpleEntry<>("Cars", 3),
            new SimpleEntry<>("Comedy", 4),
            new SimpleEntry<>("Dementia", 5),
            new SimpleEntry<>("Demons", 6),
            new SimpleEntry<>("Mystery", 7),
            new SimpleEntry<>("Drama", 8),
            new SimpleEntry<>("Ecchi", 9),
            new SimpleEntry<>("Fantasy", 10),
            new SimpleEntry<>("Game", 11),
            new SimpleEntry<>("Historical", 13),
            new SimpleEntry<>("Horror", 14),
            new SimpleEntry<>("Kids", 15),
            new SimpleEntry<>("Magic", 16),
            new SimpleEntry<>("Martial Arts", 17),
            new SimpleEntry<>("Mecha", 18),
            new SimpleEntry<>("Music", 19),
            new SimpleEntry<>("Parody", 20),
            new SimpleEntry<>("Samurai", 21),
            new SimpleEntry<>("Romance", 22),
            new SimpleEntry<>("School", 23),
            new SimpleEntry<>("Sci Fi", 24),
            new SimpleEntry<>("Shoujo", 25),
            new SimpleEntry<>("Shoujo Ai", 26),
            new SimpleEntry<>("Shounen", 27),
            new SimpleEntry<>("Shounen Ai", 28),
            new SimpleEntry<>("Space", 29),
            new SimpleEntry<>("Sports", 30),
            new SimpleEntry<>("Super Power", 31),
            new SimpleEntry<>("Vampire", 32),
            new SimpleEntry<>("Yaoi", 33),
            new SimpleEntry<>("Yuri", 34),
            new SimpleEntry<>("Harem", 35),
            new SimpleEntry<>("Slice Of Life", 36),
            new SimpleEntry<>("Supernatural", 37),
            new SimpleEntry<>("Military", 38),
            new SimpleEntry<>("Police", 39),
            new SimpleEntry<>("Psychological", 40),
            new SimpleEntry<>("Thriller", 41),
            new SimpleEntry<>("Seinen", 42),
            new SimpleEntry<>("Josei", 43))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    private GenresViewModel mViewModel;
    private final AdapterView.OnItemSelectedListener mSpinnerItemClicked = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, parent.getItemAtPosition(position).toString());
            Log.d(TAG, String.valueOf(id));
            String key = (String) parent.getItemAtPosition(position);
            mViewModel.selectGenre(genresMap.getOrDefault(key, 1));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AnimeListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGenresBinding mBinding = FragmentGenresBinding.inflate(inflater, container, false);
        mAdapter = new AnimeListAdapter();
        mBinding.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(GenresViewModel.class);
        mBinding.rvGenre.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.genres, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);

        mBinding.genresSpinner.setAdapter(adapter);
        mBinding.genresSpinner.setOnItemSelectedListener(mSpinnerItemClicked);
        mViewModel.getAnimeByGenre().observe(this.getActivity(), this::onGenreChanged);
        return mBinding.getRoot();
    }

    private void onGenreChanged(List<Anime> animeList) {
        if (animeList != null) {
            mAdapter.submitList(animeList);
        }
    }
}
