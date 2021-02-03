package com.example.buttontest.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.security.identity.AccessControlProfileId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buttontest.R;
import com.example.buttontest.adapter.HomeAdapter;
import com.example.buttontest.adapter.MyPagerAdapter;
import com.example.buttontest.api.Api;
import com.example.buttontest.api.TtitCallback;
import com.example.buttontest.entity.CategoryEntity;
import com.example.buttontest.entity.CategoryListResponse;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;
import com.example.buttontest.view.FixedViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private Context mContext;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
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
        requestCategoryList();
    }

    private void requestCategoryList(){

            HashMap<String,Object> map = new HashMap<>();
            Api.config(AppConfig.VIDEO_CATEGORY_LIST,map)
                    .getRequest(getActivity(),new TtitCallback() {
                        @Override
                        public void onSuccess(String res) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CategoryListResponse categoryListResponse = new Gson().fromJson(res,CategoryListResponse.class);
                                    if (categoryListResponse != null && categoryListResponse.getCode() == 0){
                                        List<CategoryEntity> list = categoryListResponse.getPage().getList();
                                        if (list != null && list.size() != 0 ){
                                            mTitles = new String[list.size()];
                                            for (int i = 0;i < list.size(); i++){
                                                mTitles[i] = list.get(i).getCategoryName();
                                                mFragments.add(VideoFragment.newInstance(list.get(i).getCategoryId()));
                                            }
                                            vp.setOffscreenPageLimit(mFragments.size());
                                            mAdapter = new HomeAdapter(getFragmentManager(),mFragments,mTitles);
                                            vp.setAdapter(mAdapter);
                                            tabLayout.setViewPager(vp);
                                        }
                                    }
                                }
                            });


                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
        }

    }


