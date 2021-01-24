package com.example.buttontest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class BaseFragment extends Fragment {

    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
    //主线程中父类已经默认帮我们prepare和loop了，所以在主线程中不需要额外的操作，子线程中药
    public void showToastSync(String msg){
        Looper.prepare();
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

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
}
