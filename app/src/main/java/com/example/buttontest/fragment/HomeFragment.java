package com.example.buttontest.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buttontest.R;
import com.example.buttontest.adapter.HomeAdapter;
import com.example.buttontest.adapter.MyPagerAdapter;
import com.example.buttontest.view.FixedViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {
    private Context mContext;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
    };
    private HomeAdapter mAdapter;
    private SlidingTabLayout tabLayout;
    private FixedViewPager vp;
    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        vp = (FixedViewPager)mRootView.findViewById(R.id.slide_vp);
        tabLayout = (SlidingTabLayout)mRootView.findViewById(R.id.tl_7);
    }

    @Override
    protected void initData() {
        for (String title : mTitles){
            mFragments.add(VideoFragment.newInstance(title));
        }
        vp.setOffscreenPageLimit(mFragments.size());
        mAdapter = new HomeAdapter(getFragmentManager(),mFragments,mTitles);
        vp.setAdapter(mAdapter);
        tabLayout.setViewPager(vp);
    }


}