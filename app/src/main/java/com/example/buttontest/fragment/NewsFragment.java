package com.example.buttontest.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buttontest.R;
import com.example.buttontest.activity.LoginActivity;
import com.example.buttontest.activity.WebActivity;
import com.example.buttontest.adapter.NewsAdapter;
import com.example.buttontest.adapter.VideoAdapter;
import com.example.buttontest.api.Api;
import com.example.buttontest.api.TtitCallback;
import com.example.buttontest.entity.NewsEntity;
import com.example.buttontest.entity.NewsListResponse;
import com.example.buttontest.entity.VideoEntity;
import com.example.buttontest.entity.VideoListResponse;
import com.example.buttontest.listener.OnItemClickListener;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private int pageNum = 1;
    private List<NewsEntity> allDatas;
    private NewsAdapter newsAdapter;
    private LinearLayoutManager linearLayout;
    public NewsFragment() {

    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView)mRootView.findViewById(R.id.re_view);
        refreshLayout = (RefreshLayout)mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        newsAdapter = new NewsAdapter(getActivity());
        newsAdapter.setOnItemClickListener(this);
        newsAdapter.setmDatas(allDatas);
        recyclerView.setAdapter(newsAdapter);

        //加载刷新
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                requestNewsList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum ++;
                requestNewsList(false);
            }
        });
        requestNewsList(true);
    }



    private void requestNewsList(boolean type){

            HashMap<String,Object> map = new HashMap<>();
            map.put("page",pageNum);
            map.put("limit", AppConfig.PAGE_SIZE);
            Api.config(AppConfig.NEWS_LIST,map).getRequest(getActivity(),new TtitCallback() {
                @Override
                public void onSuccess(String res) {

                    if (type){
                        refreshLayout.finishRefresh(true);
                    }else{
                        refreshLayout.finishLoadMore(true);
                    }
                    NewsListResponse listResponses = new Gson().fromJson(res, NewsListResponse.class);
                    if (listResponses != null && listResponses.getCode() == 0){
                        List<NewsEntity> datas = listResponses.getPage().getList();
                        if (datas != null && datas.size() > 0){
                            if (type){
                                allDatas = datas;
                            }else{
                                allDatas.addAll(datas);
                            }
                            newsAdapter.setmDatas(allDatas);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    newsAdapter.notifyDataSetChanged();
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
    }

    @Override
    public void onItemClick(Serializable s) {
        NewsEntity newsEntity = (NewsEntity)s;
        Bundle b = new Bundle();
        b.putString("uri","http://192.168.31.32:8089/newsDetail?title=" + newsEntity.getAuthorName());
        navigateTo(WebActivity.class,b);
    }
}