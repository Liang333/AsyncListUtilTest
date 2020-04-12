package com.example.asynclistutiltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AsyncListUtilAdapter extends RecyclerView.Adapter<AsyncListUtilAdapter.ViewHolder> {

    private MyAsyncListUtil mMyAsyncListUtil;
    private LayoutInflater mInflater;

    public AsyncListUtilAdapter(Context mContext, MyAsyncListUtil mMyAsyncListUtil){
        this.mMyAsyncListUtil = mMyAsyncListUtil;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public AsyncListUtilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /**
         * ViewHolde构造方法中的View类型参数itemView 从LayoutInflater的inflater()方法获取
         * 构造方法中会首先调用父类的构造方法
         * 在item_test.xml布局文件中， 定义了TextView
         */
        return new ViewHolder(mInflater.inflate(R.layout.item_test, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the {@link ViewHolder#itemView} to reflect
     * the item at the given position.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AsyncListUtilAdapter.ViewHolder holder, int position) {
        TestBean item = mMyAsyncListUtil.getItem(position);
        // 有可能获取为空，这时可以显示加载中，等待同步数据。
        if (item == null) {
            holder.mTvName.setText("加载中...");
        } else {
            holder.mTvName.setText(item.getName());
        }
    }

    @Override
    public int getItemCount() {
        /**
         * getItemCount() 为 MyAsyncListUtil父类方法
         * 子类实例mMyAsyncListUtil可直接调用继承下来的父类方法
         */
        return mMyAsyncListUtil.getItemCount();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;

        ViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
