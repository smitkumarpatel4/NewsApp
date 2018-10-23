package com.example.android.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newsapp.Models.NewsItem;
import com.example.android.newsapp.R;
import com.example.android.newsapp.Web.WebActivity;

import java.util.ArrayList;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemHolder>{
    Context mContext;
    public ArrayList<NewsItem>  mNewsList;

    public NewsItemAdapter (Context context, ArrayList<NewsItem> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }
    @Override
    public NewsItemAdapter.NewsItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item_list, viewGroup, shouldAttachToParentImmediately);
        NewsItemHolder newsViewHolder = new NewsItemHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsItemAdapter.NewsItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsItemHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView url;
        TextView date;

        public NewsItemHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            url = (TextView) itemView.findViewById(R.id.url);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        public void bind(final int listIndex){
            title.setText(mNewsList.get(listIndex).getTitle());
            description.setText(mNewsList.get(listIndex).getDescription());
            url.setText(mNewsList.get(listIndex).getUrl());
            date.setText(mNewsList.get(listIndex).getPublishedAt());
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String urlString = mNewsList.get(listIndex).getUrl();
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("urlString", urlString);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
