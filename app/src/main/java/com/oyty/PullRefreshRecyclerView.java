package com.oyty;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.oyty.layoutmanager.ILayoutManager;

/**
 * Created by oyty on 4/27/16.
 */
public class PullRefreshRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    public static final int ACTION_IDLE = 0;
    private int mCurrentState = ACTION_IDLE;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private ILayoutManager mLayoutManager;
    private BaseRecyclerAdapter mAdapter;

    private boolean isPullToRefreshEnabled;
    private OnRecyclerRefreshListener listener;
    private static final int visibleThreshold = 5;
    private boolean isLoadMoreEnabled;

    public PullRefreshRecyclerView(Context context) {
        super(context);
        initView();
    }

    public PullRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PullRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_pull_refresh_recycler, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        initViewListener();
        initDefaultConfig();
    }

    private void initViewListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mCurrentState == ACTION_IDLE && !isLoadMoreEnabled && checkIfNeedLoadMore()) {
                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
                    mAdapter.onLoadMoreStateChanged(true);
                    mSwipeRefreshLayout.setEnabled(false);
                    listener.onLoadingMore();
                }
            }
        });
    }

    private boolean checkIfNeedLoadMore() {
        int totalItemCount = mLayoutManager.getItemCount();
        int lastVisibleItem = mLayoutManager.lastVisiblePosition();
        return totalItemCount <= (lastVisibleItem + visibleThreshold);
    }

    private void initDefaultConfig() {
        setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
    }

    public void enablePullToRefresh(boolean enable) {
        this.isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void enableLoadMore(boolean enable) {
        this.isLoadMoreEnabled = enable;
    }

    /**
     * 设置recyclerview的管理器
     * @param manager
     */
    public void setLayoutManager(ILayoutManager manager) {
        this.mLayoutManager = manager;
        mRecyclerView.setLayoutManager(manager.getLayoutManager());
    }

    /**
     * 自定义加载动画的颜色渐变值
     * @param colorResIds
     */
    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        if (decoration != null) {
            mRecyclerView.addItemDecoration(decoration);
        }
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 首次进入页面进行加载
     */
    public void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRefresh() {
        mCurrentState = ACTION_PULL_TO_REFRESH;
        listener.onPullToRefresh();
    }

    public void onRefreshCompleted() {
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case ACTION_LOAD_MORE_REFRESH:
                mAdapter.onLoadMoreStateChanged(false);
                mSwipeRefreshLayout.setEnabled(isPullToRefreshEnabled);
                break;
        }
        mCurrentState = ACTION_IDLE;
    }

    public interface OnRecyclerRefreshListener {
        /** 下拉刷新 */
        void onPullToRefresh();

        /** 上拉加载更多 */
        void onLoadingMore();
    }

}
