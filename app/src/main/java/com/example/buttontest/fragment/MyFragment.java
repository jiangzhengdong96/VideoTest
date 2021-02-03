package com.example.buttontest.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.buttontest.R;

import butterknife.BindView;
import butterknife.OnClick;


public class MyFragment extends BaseFragment {
    @BindView(R.id.rl_mycollect)
    RelativeLayout mycollect;
    @BindView(R.id.rl_myskin)
    RelativeLayout myskin;
    @BindView(R.id.logout)
    Button logout;

    public MyFragment() {
        // Required empty public constructor
    }


    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }



    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_mycollect,R.id.rl_myskin,R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.rl_mycollect:
                showToast("我的收藏");
                break;
            case R.id.rl_myskin:
                showToast("改皮肤");
                break;
            case  R.id.logout:
                showToast("登出");
                break;
        }
    }
}