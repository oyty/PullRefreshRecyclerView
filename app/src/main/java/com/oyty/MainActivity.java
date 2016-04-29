package com.oyty;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.oyty.adapter.DataAdapter;
import com.oyty.decoration.DividerGridItemDecoration;
import com.oyty.layoutmanager.CustomLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private PullRefreshRecyclerView mPullRefreshRecyclerView;

    private List<String> dataList = new ArrayList<>();
    private ArrayList<Integer> mHeights;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        mPullRefreshRecyclerView = (PullRefreshRecyclerView) findViewById(R.id.mPullRefreshRecyclerView);

        initListView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initListView() {
        mPullRefreshRecyclerView.setOnRefreshListener(new PullRefreshRecyclerView.OnRecyclerRefreshListener() {
            @Override
            public void onPullToRefresh() {
                onLoadUpMore();
            }

            @Override
            public void onLoadingMore() {
                onLoadBottomMore();
            }
        });
//        mPullRefreshRecyclerView.enablePullToRefresh(false);
        mPullRefreshRecyclerView.setLayoutManager(new CustomLinearLayoutManager(mContext));
//        mPullRefreshRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mPullRefreshRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));

        initAdapter();
    }

    private void onLoadUpMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullRefreshRecyclerView.onRefreshCompleted();
            }
        }, 2000);
    }

    private void onLoadBottomMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Load data
                int index = dataList.size();
                int end = index + 20;
                for (int i = index; i < end; i++) {
                    dataList.add("This is item " + i);
                }
                mAdapter.notifyDataSetChanged();
                mPullRefreshRecyclerView.onRefreshCompleted();

            }
        }, 2000);
    }

    private void initAdapter() {
        dataList.clear();
        for (int i = 0; i < 50; i ++) {
            dataList.add("This is item " + i);
        }
        mHeights = new ArrayList<Integer>();
//        for (int i = 0; i < dataList.size(); i++) {
//            mHeights.add( (int) (100 + Math.random() * 300));
//        }
        mAdapter = new DataAdapter(mContext, dataList);
        mAdapter.enableLoadMore(true);
        mPullRefreshRecyclerView.setAdapter(mAdapter);
    }


}
