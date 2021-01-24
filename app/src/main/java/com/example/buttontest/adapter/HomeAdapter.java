package com.example.buttontest.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class HomeAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private String[]  mTitles;
    public HomeAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] title) {
        super(fm);
        mFragments = fragments;
        mTitles = title;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
