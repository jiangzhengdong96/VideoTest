package com.example.buttontest.fragment;

import android.content.pm.ActivityInfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.example.buttontest.R;
import com.example.buttontest.activity.LoginActivity;

import com.example.buttontest.adapter.VideoAdapter;
import com.example.buttontest.api.Api;
import com.example.buttontest.api.TtitCallback;
import com.example.buttontest.entity.CategoryEntity;
import com.example.buttontest.entity.VideoEntity;
import com.example.buttontest.entity.VideoListResponse;
import com.example.buttontest.listener.OnItemChildClickListener;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;
import com.example.buttontest.util.Tag;
import com.example.buttontest.util.Utils;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VideoFragment extends BaseFragment implements OnItemChildClickListener {

    private int mCId;
    private RecyclerView recyclerView;
    private int pageNum = 1;
    private VideoAdapter adapter;
    private RefreshLayout refreshLayout;
    private List<VideoEntity> allDatas = new ArrayList<>();
    private LinearLayoutManager linearLayout;

    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;

    public VideoFragment() {
    }

    public static VideoFragment newInstance(int cId) {
        VideoFragment fragment = new VideoFragment();
        fragment.mCId = cId;
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        initVideoView();
        recyclerView = (RecyclerView)mRootView.findViewById(R.id.re_view);
        refreshLayout = (RefreshLayout)mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new VideoAdapter(getActivity());
        adapter.setOnItemChildClickListener(this);
        recyclerView.setAdapter(adapter);



        //离开界面就释放视频
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });
        //加载刷新
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
//请求数据
    private void requestVideoList(boolean type){
        String token = getStringFromSp("token");
        if (!StringUtil.isEmpty(token)){
            HashMap<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("page",pageNum);
            map.put("limit",AppConfig.PAGE_SIZE);
            map.put("categoryId",mCId);
            Api.config(AppConfig.VIDEO_LIST_BY_CATEGORY,map).getRequest(new TtitCallback() {
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


    //初始化视频播放器界面
    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    //释放视频
    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if(getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }

    /**
     * PrepareView被点击
     */
    @Override
    public void onItemChildClick(int position) {
        startPlay(position);
    }

    /**
     * 开始播放
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoEntity videoBean = allDatas.get(position);
        //边播边存
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(videoBean.getUrl());
//        mVideoView.setUrl(proxyUrl);

        mVideoView.setUrl(videoBean.getPlayurl());
        mTitleView.setTitle(videoBean.getVtitle());
        View itemView = linearLayout.findViewByPosition(position);
        if (itemView == null) return;
        VideoAdapter.MyViewHolder viewHolder = (VideoAdapter.MyViewHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.prepareView, true);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager().add(mVideoView, Tag.LIST);
        mVideoView.start();
        mCurPos = position;

    }
}