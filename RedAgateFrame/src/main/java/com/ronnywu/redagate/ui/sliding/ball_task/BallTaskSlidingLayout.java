package com.ronnywu.redagate.ui.sliding.ball_task;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronnywu.redagate.R;
import com.ronnywu.redagate.ui.sliding.core.DoTaskListener;
import com.ronnywu.redagate.ui.sliding.core.FreeSlidingLayout;
import com.ronnywu.redagate.utils.view.RedAgateViewUtil;

/**
 * 圆球转动效果的下拉刷新,上拉加载.
 *
 * @author ronnywu
 * @date 2015-12-10
 * @time 11:49:05
 */
public class BallTaskSlidingLayout extends FreeSlidingLayout {

    /**
     * 执行任务监听器.
     */
    private DoTaskListener doTaskListener;

    /**
     * 顶部的图片
     */
    private ImageView headBall;

    /**
     * 底部的图片
     */
    private ImageView footBall;

    /**
     * 顶部的序列帧动画
     */
    private AnimationDrawable headAnimationDrawable;

    /**
     * 底部的序列帧动画
     */
    private AnimationDrawable footAnimationDrawable;

    /**
     * 小球距离底部 或 顶部的最大值.
     * 顶部的序列帧动画距离 HeadView 底部边界距离 50 dp 对应的 px 值.
     * 底部的序列帧动画距离 FootView 顶部边界距离 50 dp 对应的 px 值.
     */
    private int maxMarginDistance;

    /**
     * 头部的状态文本.
     */
    private TextView headState;

    /**
     * 底部的状态文本
     */
    private TextView footState;


    /**
     * 构造器
     *
     * @param context 上下文
     */
    public BallTaskSlidingLayout(Context context) {
        super(context);
        ballTaskSlidingLayoutInitView();
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public BallTaskSlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ballTaskSlidingLayoutInitView();
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     */
    public BallTaskSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ballTaskSlidingLayoutInitView();
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    public BallTaskSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ballTaskSlidingLayoutInitView();
    }

    /**
     * 初始化操作
     */
    private void ballTaskSlidingLayoutInitView() {
        // 刷新结束后不做停留
        setAfterEndStayFlag(false);
        // 设置顶部停留时的距离为100dp
        setHeadDist(100);
        // 设置底部停留时的距离为100dp
        setFootDist(100);
        // 计算出在当前设备中,50 dp >>> xxx px
        maxMarginDistance = RedAgateViewUtil.dip2px(getContext(), 50);
    }

    /**
     * 初始化 Head 布局.
     *
     * @param freeSlidingLayout 支持自由滑动的抽象布局.
     * @return 内容视图
     */
    @Override
    protected View initHeadView(FreeSlidingLayout freeSlidingLayout) {
        return freeSlidingLayout.findViewById(R.id.ball_task_sliding_layout_head);
    }

    /**
     * 初始化 content 布局.
     *
     * @param freeSlidingLayout 支持自由滑动的抽象布局.
     * @return 内容视图
     */
    @Override
    protected View initContentView(FreeSlidingLayout freeSlidingLayout) {
        return freeSlidingLayout.findViewById(R.id.ball_task_sliding_layout_content);
    }

    /**
     * 初始化 Foot 布局.
     *
     * @param freeSlidingLayout 支持自由滑动的抽象布局.
     * @return 内容视图
     */
    @Override
    protected View initFootView(FreeSlidingLayout freeSlidingLayout) {
        return freeSlidingLayout.findViewById(R.id.ball_task_sliding_layout_foot);
    }

    /**
     * 初始化 HeadView 相关视图.
     *
     * @param view head 视图
     */
    @Override
    protected void findHeadViewById(View view) {
        headBall = (ImageView) view.findViewById(R.id.head_ball_task_sliding_layout_ball);
        headAnimationDrawable = (AnimationDrawable) headBall.getBackground();
        headState = (TextView) findViewById(R.id.head_ball_task_sliding_layout_state);
    }

    /**
     * 初始化 FootView 相关视图.
     *
     * @param view foot 视图
     */
    @Override
    protected void findFootViewById(View view) {
        footBall = (ImageView) view.findViewById(R.id.foot_ball_task_sliding_layout_ball);
        footAnimationDrawable = (AnimationDrawable) footBall.getBackground();
        footState = (TextView) findViewById(R.id.foot_ball_task_sliding_layout_state);
    }

    /**
     * 初始化额外视图状态.
     */
    @Override
    protected void initExtraViewState() {
        headState.setText("下拉刷新");
        footState.setText("上拉加载");
    }

    /**
     * 释放刷新状态
     */
    @Override
    protected void releaseToRefreshState() {
        headState.setText("释放刷新");
    }

    /**
     * 刷新中状态.
     */
    @Override
    protected void extraViewRefreshingState() {
        headState.setText("正在刷新");
        headAnimationDrawable.start();
        if (doTaskListener != null) {
            doTaskListener.onRefresh();
        }
    }

    /**
     * 释放加载状态
     */
    @Override
    protected void releaseToLoadState() {
        footState.setText("释放加载");
    }

    /**
     * 加载中状态.
     */
    @Override
    protected void extraViewLoadingState() {
        footState.setText("正在加载");
        footAnimationDrawable.start();
        if (doTaskListener != null) {
            doTaskListener.onLoadMore();
        }
    }

    /**
     * 加载更多结束.
     *
     * @param success 加载结果,true 表示成功, false 表示失败
     */
    @Override
    protected void finishesLoadingState(boolean success) {
        footAnimationDrawable.stop();
    }

    /**
     * 刷新数据结束.
     *
     * @param success 说新结果,true 表示成功, false 表示失败
     */
    @Override
    protected void finishesRefreshingState(boolean success) {
        headAnimationDrawable.stop();
    }

    /**
     * 内容视图距离顶部的距离发生变化.
     *
     * @param distance 距离顶部的距离
     */
    @Override
    protected void onTopDistanceChange(int distance) {
        // 如果透明度超过255,则按255显示.避免进行计算轮回.
        headAnimationDrawable.setAlpha(distance / 1.3 < 255 ? (int) (distance / 1.3) : 255);
        // 根据设计图,下拉的时候,文字和图片都在底部,图片在下拉的过程中,不断下移,一直到最大值50dp为止.
        // 根据设计图,最大值为50dp,最小值为0dp
        // 这里的像素变化大小未知,不太好计算,那么为了方便计算,先将像素转为DP
        RedAgateViewUtil.setMargins(headBall, 0, 0, 0, distance / 2 < maxMarginDistance ? (distance / 2) : maxMarginDistance);
    }


    /**
     * 内容视图距离底部的距离发生变化.
     *
     * @param distance 距离底部的距离.
     */
    @Override
    protected void onBottomDistanceChange(int distance) {
        // 这里对原值做了修改,试试看会不会印象程序.
        distance = Math.abs(distance);
        // 如果透明度超过255,则按255显示.避免进行计算轮回.
        footAnimationDrawable.setAlpha(distance / 1.3 < 255 ? (int) (distance / 1.3) : 255);
        // 根据设计图,上拉的时候,文字和图片都在顶部,文字在下拉的过程中,不断下移.一直到最大值50dp为止.
        RedAgateViewUtil.setMargins(footBall, 0, distance / 2 < maxMarginDistance ? (distance / 2) : maxMarginDistance, 0, 0);
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
