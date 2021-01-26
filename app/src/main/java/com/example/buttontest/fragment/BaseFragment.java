package com.example.buttontest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dueeeke.videoplayer.player.VideoViewManager;
import com.example.buttontest.R;

import static android.content.Context.MODE_PRIVATE;

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(initLayout(), container, false);
        }
        initView();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
    //主线程中父类已经默认帮我们prepare和loop了，所以在主线程中不需要额外的操作，子线程中药
    public void showToastSync(String msg){
        Looper.prepare();
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void navigateTo(Class c){
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }

    protected void saveBySp(String key,String value){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    protected String getStringFromSp(String key){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");
        return value;
    }
    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }
}
