package com.oyty.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by oyty on 4/27/16.
 */
public class CustomLinearLayoutManager extends LinearLayoutManager implements ILayoutManager {

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int lastVisiblePosition() {
        return findLastVisibleItemPosition();
    }

}
