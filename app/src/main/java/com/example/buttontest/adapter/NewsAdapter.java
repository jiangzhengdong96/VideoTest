package com.example.buttontest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buttontest.R;
import com.example.buttontest.entity.NewsEntity;
import com.example.buttontest.entity.VideoEntity;
import com.example.buttontest.view.CircleTransform;
import com.squareup.picasso.Picasso;


import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsEntity> mDatas;
    private Context mContext;
    private RecyclerView.ViewHolder viewHolder;
    public NewsAdapter(Context context) {
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_one,parent,false);
            viewHolder = new NewsAdapter.MyViewHolderOne(view);
        }else if(viewType == 2){
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_two,parent,false);
            viewHolder = new NewsAdapter.MyViewHolderTwo(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.new_item_three,parent,false);
            viewHolder = new NewsAdapter.MyViewHolderThree(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsEntity newsEntity = mDatas.get(position);
        int viewType = newsEntity.getType();
        switch (viewType){
            case 1:
                MyViewHolderOne bindViewHolder = (NewsAdapter.MyViewHolderOne) holder;
                bindViewHolder.title.setText(newsEntity.getNewsTitle());
                bindViewHolder.author.setText(newsEntity.getAuthorName() + " .");
                bindViewHolder.comment.setText(String.valueOf(newsEntity.getCommentCount()) + "评论 .");
                bindViewHolder.time.setText(newsEntity.getReleaseDate());
                Picasso.with(mContext)
                        .load(newsEntity.getHeaderUrl())
                        .transform(new CircleTransform())
                        .into(bindViewHolder.header);
                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(bindViewHolder.thumb);
                break;
            case 2:
                MyViewHolderTwo bindViewHolder2 = (NewsAdapter.MyViewHolderTwo) holder;
                bindViewHolder2.title.setText(newsEntity.getNewsTitle());
                bindViewHolder2.author.setText(newsEntity.getAuthorName() + " .");
                bindViewHolder2.comment.setText(String.valueOf(newsEntity.getCommentCount()) + "评论 .");
                bindViewHolder2.time.setText(newsEntity.getReleaseDate());
                Picasso.with(mContext)
                        .load(newsEntity.getHeaderUrl())
                        .transform(new CircleTransform())
                        .into(bindViewHolder2.header);
                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(bindViewHolder2.thumb1);
                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(bindViewHolder2.thumb2);
                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(bindViewHolder2.thumb3);
                break;
            case 3:
                MyViewHolderThree bindViewHolder3 = (NewsAdapter.MyViewHolderThree) holder;
                bindViewHolder3.title.setText(newsEntity.getNewsTitle());
                bindViewHolder3.author.setText(newsEntity.getAuthorName() + " .");
                bindViewHolder3.comment.setText(String.valueOf(newsEntity.getCommentCount()) + "评论 .");
                bindViewHolder3.time.setText(newsEntity.getReleaseDate());
                Picasso.with(mContext)
                        .load(newsEntity.getHeaderUrl())
                        .transform(new CircleTransform())
                        .into(bindViewHolder3.header);
                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(bindViewHolder3.thumb);
                break;
        }


    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0){
            return mDatas.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        protected TextView title, author, comment ,time;
        protected ImageView header;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            comment = (TextView) itemView.findViewById(R.id.comment);
            time = (TextView) itemView.findViewById(R.id.time);
            header = (ImageView) itemView.findViewById(R.id.header);
        }
    }

    public class MyViewHolderOne extends MyViewHolder{
        private ImageView thumb;
        public MyViewHolderOne(@NonNull View itemView) {
            super(itemView);
            thumb = (ImageView)itemView.findViewById(R.id.thumb);
        }
    }
    public class MyViewHolderTwo extends MyViewHolder{
        private ImageView thumb1,thumb2,thumb3;
        public MyViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            thumb1 = (ImageView)itemView.findViewById(R.id.thumb1);
            thumb2 = (ImageView)itemView.findViewById(R.id.thumb2);
            thumb3 = (ImageView)itemView.findViewById(R.id.thumb3);
        }
    }
    public class MyViewHolderThree extends MyViewHolder{
        private ImageView thumb;
        public MyViewHolderThree(@NonNull View itemView) {
            super(itemView);
            thumb = (ImageView)itemView.findViewById(R.id.thumb);
        }
    }

    public void setmDatas(List<NewsEntity> list){
        mDatas = list;
    }

}
