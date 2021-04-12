package com.example.buttontest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.component.PrepareView;
import com.example.buttontest.R;
import com.example.buttontest.api.Api;
import com.example.buttontest.api.TtitCallback;
import com.example.buttontest.entity.BaseResponse;
import com.example.buttontest.entity.VideoEntity;
import com.example.buttontest.listener.OnItemChildClickListener;
import com.example.buttontest.listener.OnItemClickListener;
import com.example.buttontest.util.AppConfig;
import com.example.buttontest.util.StringUtil;
import com.example.buttontest.view.CircleTransform;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoEntity> mDatas;
    private Context mContext;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemClickListener mOnItemClickListener;
    private boolean isCollect = false;
    private boolean isDianzan = false;
    public VideoAdapter(Context context) {
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoEntity videoEntity = mDatas.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tvTitle.setText(videoEntity.getVtitle());
        myViewHolder.tvAuthor.setText(videoEntity.getAuthor());
//        myViewHolder.tvCollect.setText(String.valueOf(videoEntity.getCollectNum()));
//        myViewHolder.tvComment.setText(String.valueOf(videoEntity.getCommentNum()));
//        myViewHolder.tvDz.setText(String.valueOf(videoEntity.getLikeNum()));
        if (videoEntity.getVideoSocialEntity() != null){
            myViewHolder.tvCollect.setText(String.valueOf(videoEntity.getVideoSocialEntity().getCollectnum()));
            myViewHolder.tvComment.setText(String.valueOf(videoEntity.getVideoSocialEntity().getCommentnum()));
            myViewHolder.tvDz.setText(String.valueOf(videoEntity.getVideoSocialEntity().getLikenum()));
            isCollect = videoEntity.getVideoSocialEntity().isFlagCollect();
            isDianzan = videoEntity.getVideoSocialEntity().isFlagLike();
            if (isDianzan){
                myViewHolder.iv_dianzan.setImageResource(R.mipmap.dianzan_select);
                myViewHolder.tvDz.setTextColor(Color.parseColor("#e21918"));
            }
            if (isCollect){
                myViewHolder.iv_collect.setImageResource(R.mipmap.collect_select);
                myViewHolder.tvCollect.setTextColor(Color.parseColor("#e21918"));
            }
        }
//        myViewHolder.iv_dianzan.setImageResource(R.drawable.dianzan_style);
//        myViewHolder.iv_collect.setImageResource(R.drawable.collect_style);
        Picasso.with(mContext)
                .load(videoEntity.getHeadurl())
                .transform(new CircleTransform())
                .into(myViewHolder.iv_header);
        Picasso.with(mContext)
                .load(videoEntity.getCoverurl())
                .into(myViewHolder.mThumb);
        myViewHolder.mPosition = position;

        myViewHolder.mPlayerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(position);
                }
            }
        });
        myViewHolder.iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int collectNum = Integer.parseInt(myViewHolder.tvCollect.getText().toString());
                if (isCollect){
                    myViewHolder.tvCollect.setText(String.valueOf(--collectNum));
                    myViewHolder.iv_collect.setImageResource(R.mipmap.collect);
                    myViewHolder.tvCollect.setTextColor(Color.parseColor("#161616"));
                }else{
                    myViewHolder.tvCollect.setText(String.valueOf(++collectNum));
                    myViewHolder.iv_collect.setImageResource(R.mipmap.collect_select);
                    myViewHolder.tvCollect.setTextColor(Color.parseColor("#e21918"));
                }
                isCollect = !isCollect;
                requestCount(1,videoEntity.getVid(),isCollect);
            }
        });

        myViewHolder.iv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dianZanNum = Integer.parseInt(myViewHolder.tvDz.getText().toString());
                if (isDianzan){
                    myViewHolder.tvDz.setText(String.valueOf(--dianZanNum));
                    myViewHolder.iv_dianzan.setImageResource(R.mipmap.dianzan);
                    myViewHolder.tvDz.setTextColor(Color.parseColor("#161616"));
                }else{
                    myViewHolder.tvDz.setText(String.valueOf(++dianZanNum));
                    myViewHolder.iv_dianzan.setImageResource(R.mipmap.dianzan_select);
                    myViewHolder.tvDz.setTextColor(Color.parseColor("#e21918"));
                }
                isDianzan = !isDianzan;
                requestCount(2,videoEntity.getVid(),isCollect);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0){
            return mDatas.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private int mPosition;
        private TextView tvTitle,tvAuthor,tvDz,tvComment,tvCollect;
        private ImageView mThumb,iv_header,iv_collect,iv_dianzan;
        public PrepareView prepareView;
        public FrameLayout mPlayerContainer;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlayerContainer = itemView.findViewById(R.id.player_container);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDz = (TextView) itemView.findViewById(R.id.tv_dianzan);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            tvCollect = (TextView) itemView.findViewById(R.id.tv_collect);
//            iv_cover = (ImageView) itemView.findViewById(R.id.iv_cover);
            iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
            iv_collect = (ImageView) itemView.findViewById(R.id.iv_collect);
            iv_dianzan = (ImageView) itemView.findViewById(R.id.iv_dianzan);
            prepareView = (PrepareView) itemView.findViewById(R.id.prepare_view);
            mThumb = prepareView.findViewById(R.id.thumb);
            //通过tag将ViewHolder和itemView绑定
            itemView.setTag(this);
        }



    }

    public void setmDatas(List<VideoEntity> list){
        mDatas = list;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void requestCount(int type, int vid, boolean flag){
        HashMap<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("vid",vid);
        map.put("flag",flag);
        Api.config(AppConfig.VIDEO_UPDATE_COUNT,map).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(res,BaseResponse.class);
                if (baseResponse != null && baseResponse.getCode() == 0){
                    Toast.makeText(mContext,"请求成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}



