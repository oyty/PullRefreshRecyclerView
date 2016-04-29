package com.oyty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oyty.BaseRecyclerAdapter;
import com.oyty.BaseViewHolder;
import com.oyty.R;

import java.util.List;

/**
 * Created by oyty on 4/28/16.
 */
public class DataAdapter extends BaseRecyclerAdapter<String> {


    public DataAdapter(Context context, List<String> list) {
        super(context, list);
    }


    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent) {
        return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_data, parent, false));
    }

    class DataViewHolder extends BaseViewHolder {

        private TextView mLabel;

        public DataViewHolder(View itemView) {
            super(itemView);
            mLabel = (TextView) itemView.findViewById(R.id.mLabel);
        }

        @Override
        public void onBindViewHolder(int position) {
            // 设置item的随机高度
//            ViewGroup.LayoutParams lp = mLabel.getLayoutParams();
//            lp.height = mHeights.get(position);
//            mLabel.setLayoutParams(lp);

            mLabel.setText(mDataList.get(position));
        }

        @Override
        protected void onItemClick(View view, int adapterPosition) {
            Toast.makeText(mContext, "This is item " + adapterPosition, Toast.LENGTH_SHORT).show();
        }
    }
}
