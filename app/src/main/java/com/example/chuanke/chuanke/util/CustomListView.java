package com.example.chuanke.chuanke.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.chuanke.chuanke.R;

/**
 * Created by Asus on 2017/4/5.
 */

public class CustomListView extends ListView implements AbsListView.OnScrollListener {

    private View footView;
    private Context context;

    private int totalItemCount;//总的条目
    private int lastVisibleItem;//最后可见的item
    private boolean isLoading = true;//正在加载

    private ILoadMoreListener loadMoreListener;

    public void setLoadMoreListener(ILoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public CustomListView(Context context) {
        this(context, null);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    /**
     * 加载底部布局
     */
    private void initView() {
        footView = View.inflate(context, R.layout.view_foot, null);
        footView.findViewById(R.id.footView).setVisibility(View.GONE);
        this.addFooterView(footView);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {//最后一个item并且停止滚动
            if (isLoading) {//正在加载
                isLoading = false;
                footView.findViewById(R.id.footView).setVisibility(VISIBLE);
                //加载更多
                if (null != loadMoreListener) {
                    loadMoreListener.loadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    /**
     * 加载完成
     */
    public void loadComplete() {
        isLoading = true;
        footView.findViewById(R.id.footView).setVisibility(GONE);
    }

    public interface ILoadMoreListener {
        void loadMore();
    }

}
