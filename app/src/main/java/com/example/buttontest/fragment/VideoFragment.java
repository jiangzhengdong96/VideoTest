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
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VideoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private int pageNum = 1;
    private VideoAdapter adapter;
    private RefreshLayout refreshLayout;
    private List<VideoEntity> allDatas = new ArrayList<>();
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
        refreshLayout = (RefreshLayout)v.findViewById(R.id.refreshLayout);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new VideoAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                requestVideoList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum ++;
                requestVideoList(false);
            }
        });
        requestVideoList(true);
    }

    private void requestVideoList(boolean type){
        String token = getStringFromSp("token");
        if (!StringUtil.isEmpty(token)){
            HashMap<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("page",pageNum);
            map.put("limit",AppConfig.PAGE_SIZE);
            Api.config(AppConfig.VIDEO_LIST,map).getRequest(new TtitCallback() {
                @Override
                public void onSuccess(String res) {

                    if (type){
                        refreshLayout.finishRefresh(true);
                    }else{
                        refreshLayout.finishLoadMore(true);
                    }
                    VideoListResponse listResponses = new Gson().fromJson(res, VideoListResponse.class);
                    if (listResponses != null && listResponses.getCode() == 0){
                        List<VideoEntity> datas = listResponses.getPage().getList();
                        if (datas != null && datas.size() > 0){
                            if (type){
                                allDatas = datas;
                            }else{
                                allDatas.addAll(datas);
                            }
                            adapter.setmDatas(allDatas);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }else{
                            if (type){
                                showToastSync("刷新失败");
                            }else{
                                showToastSync("无加载内容");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    if (type) {
                        refreshLayout.finishRefresh(true);
                    } else {
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });
        }else {
            navigateTo(LoginActivity.class);
        }
    }
}