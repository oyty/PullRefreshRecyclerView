package com.oyty;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by oyty on 4/27/16.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick(view, getAdapterPosition());
            }
        });
    }

    public abstract void onBindViewHolder(int position);

    protected abstract void onItemClick(View view, int adapterPosition);
}
