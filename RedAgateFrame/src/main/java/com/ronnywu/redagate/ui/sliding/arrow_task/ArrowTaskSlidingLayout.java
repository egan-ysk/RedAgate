package com.ronnywu.redagate.ui.sliding.arrow_task;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronnywu.redagate.R;
import com.ronnywu.redagate.ui.sliding.core.DoTaskListener;
import com.ronnywu.redagate.ui.sliding.core.FreeSlidingLayout;

/**
 * 传统下拉刷新,上啦加载.
 * 所谓传统就是说:当前控件是箭头模式.
 */
public class ArrowTaskSlidingLayout extends FreeSlidingLayout {

    /**
     * 下拉箭头的转180°动画
     */
    private RotateAnimation clockwiseRotationAngleLevelTwo;

    /**
     * 均匀旋转动画
     */
    private RotateAnimation refreshingAnimation;

    /**
     * 下拉的箭头
     */
    private ImageView headArrow;

    /**
     * 正在刷新的图标
     */
    private ImageView headRefreshing;

    /**
     * 刷新结果图标
     */
    private ImageView headResult;

    /**
     * 刷新结果：成功或失败
     */
    private TextView headState;

    /**
     * 上拉的箭头
     */
    private ImageView footArrow;

    /**
     * 正在加载的图标
     */
    private ImageView footLoading;

    /**
     * 加载结果图标
     */
    private ImageView footResult;

    /**
     * 加载结果：成功或失败
     */
    private TextView footState;

    /**
     * 执行任务监听器.
     */
    private DoTaskListener doTaskListener;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public ArrowTaskSlidingLayout(Context context) {
        super(context);
        ArrowTaskSlidingLayoutInitView(context);
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public ArrowTaskSlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ArrowTaskSlidingLayoutInitView(context);
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     */
    public ArrowTaskSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ArrowTaskSlidingLayoutInitView(context);
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    public ArrowTaskSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ArrowTaskSlidingLayoutInitView(context);
    }

    /**
     * 初始化布局
     *
     * @param context 上下文
     */
    private void ArrowTaskSlidingLayoutInitView(Context context) {
        clockwiseRotationAngleLevelTwo = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.anim_rotation_two);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.anim_rotation_four);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        clockwiseRotationAngleLevelTwo.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);
    }

    /**
     * 初始化 Head 布局.
     *
     * @return 内容视图
     */
    @Override
    protected View initHeadView(FreeSlidingLayout freeSlidingLayout) {
        return freeSlidingLayout.findViewById(R.id.arrow_task_sliding_layout_head);
    }

    /**
     * 初始化 content 布局.
     *
     * @return 内容视图
     */
    @Override
    protected View initContentView(FreeSlidingLayout freeSlidingLayout) {
        return freeSlidingLayout.findViewById(R.id.arrow_task_sliding_layout_content);
    }

    /**
     * 初始化 Foot 布局.
     *
     * @return 内容视图
     */
    @Override
    protected View initFootView(FreeSlidingLayout freeSlidingLayout) {
        return freeSlidingLayout.findViewById(R.id.arrow_task_sliding_layout_foot);
    }

    /**
     * 初始化顶部视图.
     *
     * @param view head 视图
     */
    @Override
    protected void findHeadViewById(View view) {
        // 箭头 - 图片
        headArrow = (ImageView) view.findViewById(R.id.head_arrow_task_sliding_layout_arrow);
        // 刷新中 - 图标
        headRefreshing = (ImageView) view.findViewById(R.id.head_arrow_task_sliding_layout_refreshing);
        // 刷新状态 - 文字
        headState = (TextView) view.findViewById(R.id.head_arrow_task_sliding_layout_state);
        // 刷新结果 - 图片
        headResult = (ImageView) view.findViewById(R.id.head_arrow_task_sliding_layout_result);
    }

    /**
     * 初始化底部视图
     *
     * @param view foot 视图
     */
    @Override
    protected void findFootViewById(View view) {
        // 箭头 - 图片
        footArrow = (ImageView) view.findViewById(R.id.foot_arrow_task_sliding_layout_arrow);
        // 刷新中 - 图标
        footLoading = (ImageView) view.findViewById(R.id.foot_arrow_task_sliding_layout_loading);
        // 刷新状态 - 文字
        footState = (TextView) view.findViewById(R.id.foot_arrow_task_sliding_layout_state);
        // 刷新结果 - 图片
        footResult = (ImageView) view.findViewById(R.id.foot_arrow_task_sliding_layout_result);
    }

    /**
     * 初始化额外视图的状态.
     */
    @Override
    protected void initExtraViewState() {
        // 清除上次的动画
        headArrow.clearAnimation();
        // 隐藏忙碌进度图标
        headRefreshing.setVisibility(GONE);
        // 隐藏结果图标
        headResult.setVisibility(GONE);
        // 显示下拉的旋转箭头
        headArrow.setVisibility(VISIBLE);
        // 修改文案为下拉刷新
        headState.setText("下拉刷新");

        //  清除上次的动画
        footArrow.clearAnimation();
        // 隐藏忙碌进度图标
        footLoading.setVisibility(GONE);
        // 隐藏结果图标
        footResult.setVisibility(GONE);
        // 显示上拉的旋转箭头
        footArrow.setVisibility(VISIBLE);
        // 修改文案为下拉刷新
        footState.setText("上拉加载");
    }

    /**
     * 释放刷新状态
     */
    @Override
    protected void releaseToRefreshState() {
        // 释放刷新状态
        headState.setText("释放刷新");
        headArrow.startAnimation(clockwiseRotationAngleLevelTwo);
    }

    /**
     * 刷新中状态.
     */
    @Override
    protected void extraViewRefreshingState() {
        // 去除旋转动画.
        headArrow.clearAnimation();
        // 刷新时隐藏箭头
        headArrow.setVisibility(View.GONE);
        // 显示忙碌图标
        headRefreshing.setVisibility(View.VISIBLE);
        // 让忙碌图标旋转
        headRefreshing.startAnimation(refreshingAnimation);
        // 修改文案
        headState.setText("正在刷新");
        if (doTaskListener != null) {
            doTaskListener.onRefresh();
        }
    }

    /**
     * 释放加载状态
     */
    @Override
    protected void releaseToLoadState() {
        // 释放加载状态
        footState.setText("释放加载");
        footArrow.startAnimation(clockwiseRotationAngleLevelTwo);
    }

    /**
     * 加载中状态.
     */
    @Override
    protected void extraViewLoadingState() {
        // 去除旋转动画.
        footArrow.clearAnimation();
        // 刷新时隐藏箭头
        footArrow.setVisibility(View.GONE);
        // 显示忙碌图标
        footLoading.setVisibility(View.VISIBLE);
        // 让忙碌图标旋转
        footLoading.startAnimation(refreshingAnimation);
        // 修改文案
        headState.setText("正在加载");
        if (doTaskListener != null) {
            doTaskListener.onLoadMore();
        }
    }

    /**
     * 结束加载.
     *
     * @param success
     */
    @Override
    protected void finishesLoadingState(boolean success) {
        // 隐藏忙碌图标
        footLoading.setVisibility(View.GONE);
        // 移除忙碌图标的旋转动画
        footLoading.clearAnimation();
        // 显示刷新结果图标
        footResult.setVisibility(VISIBLE);
        // 根据结果显示不同的结果图标
        footResult.setImageDrawable(getResources().getDrawable(success ? R.mipmap.red_arrow_task_sliding_layout_succeed : R.mipmap.red_arrow_task_sliding_layout_failed));
        footState.setText(success ? "加载成功" : "加载失败");
    }

    /**
     * 结束刷新.
     *
     * @param success
     */
    @Override
    protected void finishesRefreshingState(boolean success) {
        // 隐藏忙碌图标
        headRefreshing.setVisibility(View.GONE);
        // 移除忙碌图标的旋转动画
        headRefreshing.clearAnimation();
        // 显示刷新结果图标
        headResult.setVisibility(VISIBLE);
        // 根据结果显示不同的结果图标
        headResult.setImageDrawable(getResources().getDrawable(success ? R.mipmap.red_arrow_task_sliding_layout_succeed : R.mipmap.red_arrow_task_sliding_layout_failed));
        headState.setText(success ? "刷新成功" : "刷新失败");
    }

    /**
     * 内容视图距离顶部的距离发生变化.
     *
     * @param distance
     */
    @Override
    protected void onTopDistanceChange(int distance) {
        // 没有依赖于此函数的效果,什么也不做即可.
    }

    /**
     * 内容视图距离底部的距离发生变化.
     *
     * @param distance 距离底部的距离.
     */
    @Override
    protected void onBottomDistanceChange(int distance) {
        // 没有依赖于此函数的效果,什么也不做即可.
    }

    /**
     * 获取任务执行监听器.
     *
     * @return 任务执行监听器.
     */
    public DoTaskListener getDoTaskListener() {
        return doTaskListener;
    }

    /**
     * 设置任务执行监听器.
     *
     * @param doTaskListener 任务执行监听器.
     */
    public void setDoTaskListener(DoTaskListener doTaskListener) {
        this.doTaskListener = doTaskListener;
    }
}
