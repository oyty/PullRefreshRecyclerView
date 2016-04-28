package com.oyty;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oyty.decoration.DividerGridItemDecoration;
import com.oyty.layoutmanager.CustomLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PullRefreshRecyclerView.OnRecyclerRefreshListener {

    private Context mContext;
    private PullRefreshRecyclerView mPullRefreshRecyclerView;

    private List<String> dataList = new ArrayList<>();
    private ArrayList<Integer> mHeights;

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
        mPullRefreshRecyclerView.setOnRefreshListener(this);
        mPullRefreshRecyclerView.enablePullToRefresh(false);
        mPullRefreshRecyclerView.setLayoutManager(new CustomLinearLayoutManager(mContext));
//        mPullRefreshRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mPullRefreshRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));

        dataList.clear();
        for (int i = 0; i < 50; i ++) {
            dataList.add("This is item " + i);
        }
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < dataList.size(); i++)
        {
            mHeights.add( (int) (100 + Math.random() * 300));
        }
        DataAdapter adapter = new DataAdapter();
        mPullRefreshRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh(int action) {

    }

    class DataAdapter extends BaseRecyclerAdapter {

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DataViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_data, parent, false));
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            holder.onBindViewHolder(position);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
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

            mLabel.setText(dataList.get(position));
        }

        @Override
        protected void onItemClick(View view, int adapterPosition) {
            Toast.makeText(mContext, "This is item " + adapterPosition, Toast.LENGTH_SHORT).show();
        }
    }

}
