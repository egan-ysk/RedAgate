package com.ronnywu.redagate.ui.sliding.core;

/**
 * 执行任务监听器.
 */
public interface DoTaskListener {

    /**
     * 刷新
     */
    void onRefresh();

    /**
     * 加载更多
     */
    void onLoadMore();
}
