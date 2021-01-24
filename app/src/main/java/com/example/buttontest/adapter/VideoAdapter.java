package com.example.buttontest.adapter;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buttontest.R;
import com.example.buttontest.entity.VideoEntity;
import com.example.buttontest.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoEntity> mDatas;
    private Context mContext;
    public VideoAdapter(List<VideoEntity> datas, Context context) {
        mDatas = datas;
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
        myViewHolder.tvCollect.setText(String.valueOf(videoEntity.getCollectNum()));
        myViewHolder.tvComment.setText(String.valueOf(videoEntity.getCommentNum()));
        myViewHolder.tvDz.setText(String.valueOf(videoEntity.getLikeNum()));
        Picasso.with(mContext).load(videoEntity.getHeadurl()).into(myViewHolder.iv_header);
        Picasso.with(mContext).load(videoEntity.getCoverurl()).into(myViewHolder.iv_cover);
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0){
            return mDatas.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvAuthor,tvDz,tvComment,tvCollect;
        private ImageView iv_cover,iv_header;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDz = (TextView) itemView.findViewById(R.id.tv_dianzan);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            tvCollect = (TextView) itemView.findViewById(R.id.tv_collect);
            iv_cover = (ImageView) itemView.findViewById(R.id.iv_cover);
            iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
        }


    }
}
