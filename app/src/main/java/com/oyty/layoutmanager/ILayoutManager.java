package com.oyty.layoutmanager;

import android.support.v7.widget.RecyclerView;

/**
 * Created by oyty on 4/27/16.
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();

    int lastVisiblePosition();

    int firstVisiblePosition();

    int getChildCount();

    int getItemCount();


}
