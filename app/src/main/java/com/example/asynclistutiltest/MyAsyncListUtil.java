package com.example.asynclistutiltest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyAsyncListUtil extends AsyncListUtil<TestBean> {
    /**
     * 一次加载数据的个数，分页数量
     */
    private static final int TILE_SIZE = 20;

    public MyAsyncListUtil(RecyclerView mRecyclerView){

        super(TestBean.class, TILE_SIZE, new MyDateCallBack(), new MyViewCallBack(mRecyclerView));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 回调的三个变量含义:
             * @param recyclerView: 当前滚动的view
             * @param dx: 水平滚动距离
             * @param dy: 垂直滚动距离
             *
             * dx > 0 时为手指向左滚动,列表滚动显示右面的内容
             * dx < 0 时为手指向右滚动,列表滚动显示左面的内容
             * dy > 0 时为手指向上滚动,列表滚动显示下面的内容
             * dy < 0 时为手指向下滚动,列表滚动显示上面的内容
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 更新当前可见范围数据
                /**
                 * onRangeChanged()为父类AsyncListUtil中方法
                 * 子类继承父类，可直接调用父类方法
                 */
                onRangeChanged();
            }
        });
    }

    /**
     * 获取数据回调
     */
    private static class MyDateCallBack extends DataCallback<TestBean> {

        /**
         * 200 为总数据个数
         * @return
         */
        @Override
        public int refreshData() {
            return 200;
        }

        /**
         * 填充数据（后台线程），一般读取数据库数据
         * @param data item为 TestBean（id， name）
         * @param startPosition
         * @param itemCount
         */
        @Override
        public void fillData(@NonNull TestBean[] data, int startPosition, int itemCount) {
            for (int i=0; i<itemCount; i++) {
                TestBean item = data[i];
                if (item == null) {
                    item = new TestBean(startPosition, "item: " + (startPosition + i));
                    data[i] = item;
                }
            }

            try {
                // 模拟加载数据中
                /**
                 * Thread的静态方法Sleep(),随时可以调用
                 * 而 Object的 wait(),notify() 只能在同步方法（快）中使用
                 */
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于获取可见项范围和更新通知的回调
     */
    private static class MyViewCallBack extends ViewCallback {

        private RecyclerView mRecyclerView;

        public MyViewCallBack(RecyclerView mRecyclerView) {
            this.mRecyclerView = mRecyclerView;
        }

        /**
         * 显示数据的范围
         * @param outRange
         */
        @Override
        public void getItemRangeInto(@NonNull int[] outRange) {
            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
            LinearLayoutManager llm = (LinearLayoutManager) manager;
            outRange[0] = llm.findFirstVisibleItemPosition();
            outRange[1] = llm.findLastVisibleItemPosition();
        }

        /**
         * 刷新数据
         */
        @Override
        public void onDataRefresh() {
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }

        /**
         * item更新
         * @param position
         */
        @Override
        public void onItemLoaded(int position) {
            mRecyclerView.getAdapter().notifyItemChanged(position);
        }
    }

}