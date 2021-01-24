package com.example.buttontest.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buttontest.R;
import com.example.buttontest.activity.LoginActivity;
import com.example.buttontest.adapter.VideoAdapter;
import com.example.buttontest.api.Api;
import com.example.buttontest.api.TtitCallback;
import com.example.buttontest.entity.VideoEntity;
import com.example.buttontest.entity.VideoListResponse;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;


public class VideoFragment extends BaseFragment {

    private RecyclerView recyclerView;

    private VideoAdapter adapter;

    private List<VideoEntity> datas = null;
    public VideoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String title) {
        VideoFragment fragment = new VideoFragment();
//        fragment.mTitle = title;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.re_view);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);

        requestVideoList();

    }

    private void requestVideoList(){

        String token = getStringFromSp("token");
        if (!StringUtil.isEmpty(token)){
            HashMap<String,Object> map = new HashMap<>();
            map.put("token",token);
            Api.config(AppConfig.VIDEO_LIST,map).getRequest(new TtitCallback() {
                @Override
                public void onSuccess(String res) {
                    VideoListResponse listResponses = new Gson().fromJson(res, VideoListResponse.class);
                    if (listResponses != null && listResponses.getCode() == 0){
                        datas = listResponses.getPage().getList();
                        adapter = new VideoAdapter(datas,getActivity());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(adapter);
                            }
                        });


                    }
                }

                @Override
                public void onFailure(Exception e) {
                }
            });
        }else {
            navigateTo(LoginActivity.class);
        }
    }


}