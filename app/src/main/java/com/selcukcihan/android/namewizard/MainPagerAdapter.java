package com.selcukcihan.android.namewizard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by SELCUKCI on 25.5.2016.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return null;/*
        switch (position) {
            case 0:
                return MainFragment.newInstance();
            case 1:
                return ShortlistFragment.newInstance();
            case 2:
                return SettingsFragment.newInstance();
            default:
                return null;
        }*/
    }

    @Override
    public int getCount() {
        return 3;
    }
}
