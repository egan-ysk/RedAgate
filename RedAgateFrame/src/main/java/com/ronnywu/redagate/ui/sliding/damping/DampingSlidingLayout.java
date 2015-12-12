package com.ronnywu.redagate.ui.sliding.damping;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ronnywu.redagate.R;
import com.ronnywu.redagate.ui.sliding.core.FreeSlidingLayout;

/**
 * 阻尼滑动视图.
 */
public class DampingSlidingLayout extends FreeSlidingLayout {

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public DampingSlidingLayout(Context context) {
        super(context);
        dampingSlidingLayoutInitView();
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public DampingSlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dampingSlidingLayoutInitView();
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     */
    public DampingSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dampingSlidingLayoutInitView();
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    public DampingSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        dampingSlidingLayoutInitView();
    }

    /**
     * 初始化操作.
     */
    private void dampingSlidingLayoutInitView() {
        // 关闭停留效果.
        setAfterEndStayFlag(false);
    }

    /**
     * 初始化 Head 布局.
     *
     * @return 内容视图
     */
    @Override
    protected View initHeadView(FreeSlidingLayout freeSlidingLayout) {
        return findViewById(R.id.damping_sliding_layout_head);
    }

    /**
     * 初始化 content 布局.
     *
     * @return 内容视图
     */
    @Override
    protected View initContentView(FreeSlidingLayout freeSlidingLayout) {
        return findViewById(R.id.damping_sliding_layout_content);
    }

    /**
     * 初始化 Foot 布局.
     *
     * @return 内容视图
     */
    @Override
    protected View initFootView(FreeSlidingLayout freeSlidingLayout) {
        return findViewById(R.id.damping_sliding_layout_foot);
    }

    /**
     * 初始化 HeadView 相关视图.
     *
     * @param view head 视图
     */
    @Override
    protected void findHeadViewById(View view) {

    }

    /**
     * 初始化 FootView 相关视图.
     *
     * @param view foot 视图
     */
    @Override
    protected void findFootViewById(View view) {

    }

    /**
     * 初始化额外视图状态.
     */
    @Override
    protected void initExtraViewState() {

    }

    /**
     * 释放刷新状态
     */
    @Override
    protected void releaseToRefreshState() {

    }

    /**
     * 刷新中状态.
     */
    @Override
    protected void extraViewRefreshingState() {
        setRefreshSuccess(true);
    }

    /**
     * 释放加载状态
     */
    @Override
    protected void releaseToLoadState() {

    }

    /**
     * 加载中状态.
     */
    @Override
    protected void extraViewLoadingState() {
        setLoadMoreSuccess(true);
    }

    /**
     * 结束加载.
     *
     * @param success 加载结果,true 表示成功, false 表示失败
     */
    @Override
    protected void finishesLoadingState(boolean success) {

    }

    /**
     * 结束刷新.
     *
     * @param success 刷新结果,true 表示成功, false 表示失败
     */
    @Override
    protected void finishesRefreshingState(boolean success) {

    }

    /**
     * 内容视图距离顶部的距离发生变化.
     *
     * @param distance 距离顶部的距离.
     */
    @Override
    protected void onTopDistanceChange(int distance) {

    }

    /**
     * 内容视图距离底部的距离发生变化.
     *
     * @param distance 距离底部的距离.
     */
    @Override
    protected void onBottomDistanceChange(int distance) {

    }


}
