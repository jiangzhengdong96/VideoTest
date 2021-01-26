package com.example.buttontest.adapter;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.component.PrepareView;
import com.example.buttontest.R;
import com.example.buttontest.entity.VideoEntity;
import com.example.buttontest.listener.OnItemChildClickListener;
import com.example.buttontest.listener.OnItemClickListener;
import com.example.buttontest.util.StringUtil;
import com.example.buttontest.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoEntity> mDatas;
    private Context mContext;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemClickListener mOnItemClickListener;
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
        myViewHolder.tvCollect.setText(String.valueOf(videoEntity.getCollectNum()));
        myViewHolder.tvComment.setText(String.valueOf(videoEntity.getCommentNum()));
        myViewHolder.tvDz.setText(String.valueOf(videoEntity.getLikeNum()));
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
        private ImageView mThumb,iv_header;
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

}



