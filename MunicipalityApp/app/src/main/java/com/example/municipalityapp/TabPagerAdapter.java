package com.example.municipalityapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.municipalityapp.fragments.DataFragment;
import com.example.municipalityapp.fragments.InfoFragment;
import com.example.municipalityapp.fragments.QuizFragment;

public class TabPagerAdapter extends FragmentStateAdapter {
    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new InfoFragment();
        } else if (position == 1) {
            return new DataFragment();
        } else if (position == 2) {
            return new QuizFragment();
        }
        return new InfoFragment();
    }

    @Override
    public int getItemCount() {
        return 3; }

}
