package com.example.buttontest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.buttontest.R;
import com.example.buttontest.adapter.MyPagerAdapter;
import com.example.buttontest.entity.TabEntity;
import com.example.buttontest.fragment.CollectFragment;
import com.example.buttontest.fragment.HomeFragment;
import com.example.buttontest.fragment.MyFragment;
import com.example.buttontest.fragment.NewsFragment;
import com.example.buttontest.view.FixedViewPager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"首页", "收藏", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.home_unselect, R.mipmap.collect_unselect,
            R.mipmap.my_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.home_selected, R.mipmap.collect_selected,
            R.mipmap.my_selected};
    private FixedViewPager mViewPager;
    private CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mFragments,mTitles));
        tabLayout();
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        mViewPager = (FixedViewPager) findViewById(R.id.vp_2);
        mTabLayout = (CommonTabLayout)findViewById(R.id.tl_2);
    }

    @Override
    protected void initData() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(NewsFragment.newInstance());
        mFragments.add(MyFragment.newInstance());


        for (int i = 0; i < mTitles.length; i++){
            mTabEntities.add(new TabEntity(mTitles[i],mIconSelectIds[i],mIconUnselectIds[i]));
        }
    }

    private void tabLayout() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}