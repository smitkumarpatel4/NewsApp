package com.example.android.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import com.example.android.newsapp.Adapter.NewsItemAdapter;
import com.example.android.newsapp.Models.NewsItem;
import com.example.android.newsapp.Utilities.JSONUtils;
import com.example.android.newsapp.Utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity //implements LoaderManager.LoaderCallbacks<String>
 {
     public static final String URL_EXTRA = "newsArticleUrl";
     public static final String URL_Result = "newsUrlResult";


    private RecyclerView mRecyclerView;
    private NewsItemAdapter mAdapter;
    private ArrayList<NewsItem> newsItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.tv_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NewsItemAdapter(MainActivity.this, newsItem);
        mRecyclerView.setAdapter(mAdapter);

        makeNewsURL();
    }

    private void  makeNewsURL(){
        URL newsURL = NetworkUtils.buildUrl();
        new NewsAppQueryTask().execute(newsURL);
    }




     public class NewsAppQueryTask extends AsyncTask<URL, Void, String> {
         @Override
         protected String doInBackground(URL... urls) {
             URL clickUrl = urls[0];
             String newsAppClickResult = null;
             try{
                 newsAppClickResult = NetworkUtils.getResponseFromHttpUrl(clickUrl);
             }
             catch (IOException e){
                 e.printStackTrace();
             }
             return newsAppClickResult;
         }

         @Override
         protected void onPostExecute(String s) {
             newsItem = JSONUtils.makeNewsItemList(s);
             populateRecyclerView(newsItem);
         }


     }

     public void populateRecyclerView(ArrayList<NewsItem> newsItem ){
         mAdapter.mNewsList.addAll(newsItem);
         mAdapter.notifyDataSetChanged();
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.main ,menu);
         return true;
     }


}