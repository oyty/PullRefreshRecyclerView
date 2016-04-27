package com.oyty.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by oyty on 4/27/16.
 */
public class CustomStaggerGridLayoutManager extends StaggeredGridLayoutManager implements ILayoutManager {

    public CustomStaggerGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomStaggerGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int lastVisiblePosition() {
        return 0;
    }
}
