package com.patriot.ur254.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dennismwebia on 08/11/16.
 */
public class MainTabAdapter extends FragmentPagerAdapter {
    private CharSequence Titles[];
    private int NumbOfTabs;
    private Fragment fragments[];

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public MainTabAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Fragment fragments[]) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}