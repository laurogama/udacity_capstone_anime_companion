package com.android.example.animecompanion.ui.adapters;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * http://fabcoding.com/fragments-with-bottom-navigation-retaining-fragment-state/
 */
public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public SmartFragmentStatePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    // Register the fragment when the item is instantiated
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.append(position, fragment);
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
