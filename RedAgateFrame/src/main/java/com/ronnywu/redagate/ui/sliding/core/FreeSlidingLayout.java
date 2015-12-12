package com.ronnywu.redagate.ui.sliding.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.ronnywu.redagate.utils.view.RedAgateViewUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自由滑动布局.
 * 父类负责共性的约束和共性状态的流转.
 */
public abstract class FreeSlidingLayout extends RelativeLayout {

    /**
     * 初始化状态
     */
    public static final int INIT_EXTRA_VIEW = 1;
    /**
     * 释放刷新
     */
    public static final int RELEASE_TO_REFRESH = 2;
    /**
     * 正在刷新
     */
    public static final int REFRESHING = 3;
    /**
     * 释放加载
     */
    public static final int RELEASE_TO_LOAD = 4;
    /**
     * 正在加载
     */
    public static final int LOADING = 5;
    /**
     * 加载完成
     */
    public static final int FINISHES_LOADING = 7;
    /**
     * 刷新完成
     */
    public static final int FINISHES_REFRESHING = 8;
    /**
     * 停留状态.
     * 当刷新完成或者加载完成后会停留显示刷新或者加载的结果.
     */
    public static final int STAGNATION = 6;
    /**
     * 下拉的距离。
     * 这里pullDownY和pullUpY不可能同时不为0
     */
    public float pullDownY = 0;
    /**
     * 初始回滚/回弹速度
     */
    public float RESTORE_POSITION_MOVE_SPEED = 3;
    /**
     * 布局标签.
     * 用于确定布局,还是改变位置使用.
     */
    private boolean mFreeSlidingLayoutFlag = false;
    /**
     * 顶部视图.
     */
    private View mHeadView;
    /**
     * 内容视图.
     */
    private View mContentView;
    /**
     * 脚部视图.
     */
    private View mFootView;
    /**
     * 按下Y坐标，上一个事件点Y坐标
     */
    private float downY, lastY;
    /**
     * 上拉的距离
     */
    private float pullUpY = 0;

    /**
     * 释放刷新的距离.
     * 默认48dp.
     */
    private float headDist = RedAgateViewUtil.dip2px(getContext(), 70f);
    /**
     * 释放加载的距离
     * 默认48dp.
     */
    private float footDist = RedAgateViewUtil.dip2px(getContext(), 70f);
    /**
     * 是否为多点触碰.
     */
    private boolean isMultiTouch;
    /**
     * 当前状态
     */
    private int state = INIT_EXTRA_VIEW;
    /**
     * 在刷新/加载的过程中,是否允许滑动操作.
     */
    private boolean isTouch = false;
    /**
     * 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
     */
    private float radio = 2;
    /**
     * 回滚定时器
     */
    private RestorePositionTimer restorePositionTimer;
    /**
     * 这两个变量用来控制pull的方向，如果不加控制，会出现上拉时还可以下拉的情况
     */
    private boolean canPullDown = true;

    /**
     * 这两个变量用来控制pull的方向，如果不加控制，会出现上拉时还可以下拉的情况
     */
    private boolean canPullUp = true;

    /**
     * 刷新结果.
     * true 表示成功
     * false 表示失败
     */
    private boolean refreshSuccess;

    /**
     * 加载更多.
     * true 表示成功.
     * false 表示失败.
     */
    private boolean loadMoreSuccess;

    /**
     * 结束后是否停留1.2秒再回滚.
     * 默认开启停留.
     */
    private boolean afterEndStayFlag = true;

    /**
     * 回滚结束标识.
     * 初始情况为 true , 否则控件以为回弹已经开始且未结束,那么用户将无法下拉.
     */
    private boolean reboundEnd = true;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public FreeSlidingLayout(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public FreeSlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     */
    public FreeSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FreeSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 初始化视图
     *
     * @param context 上下文
     */
    private void initView(Context context) {
        restorePositionTimer = new RestorePositionTimer(new RestorePositionHandler());
    }

    /**
     * 布局子控件.
     *
     * @param changed 当前视图的位置,大小是否改变.
     * @param l       当前视图左侧边界居于父视图左侧边界的距离.
     * @param t       当前视图顶部边界居于父视图顶部边界的距离.
     * @param r       当前视图右侧边界居于父视图左侧边界的距离.
     * @param b       当前视图底部边界居于父视图顶部边界的距离.
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 布局标签
        if (!mFreeSlidingLayoutFlag) {
            // 初始化头部
            mHeadView = initHeadView(this);
            // 初始化内容
            mContentView = initContentView(this);
            // 初始化脚部
            mFootView = initFootView(this);
            // 初始化完成
            mFreeSlidingLayoutFlag = true;
            findHeadViewById(mHeadView);
            findFootViewById(mFootView);
        } else {
            // 头部视图的变化
            if (mHeadView != null) {
                headViewOnLayout();
            }
            // 内容视图的变化
            if (mContentView != null) {
                contentViewOnLayout();
            }
            // 脚部视图的变化
            if (mFootView != null) {
                footViewOnLayout();
            }
        }
    }

    /**
     * 修正 HeadView 的位置.
     */
    private void headViewOnLayout() {
        mHeadView.layout
                (
                        0,
                        (int) (pullDownY + pullUpY) - mHeadView.getMeasuredHeight(),
                        mHeadView.getMeasuredWidth(),
                        (int) (pullDownY + pullUpY)
                );
    }

    /**
     * 修正 ContentView 的位置.
     */
    private void contentViewOnLayout() {
        mContentView.layout
                (
                        0,
                        (int) (pullDownY + pullUpY),
                        mContentView.getMeasuredWidth(),
                        (int) (pullDownY + pullUpY) + mContentView.getMeasuredHeight());
    }

    /**
     * 修正 FootView 的位置.
     */
    private void footViewOnLayout() {
        mFootView.layout
                (
                        0,
                        (int) (pullDownY + pullUpY) + mContentView.getMeasuredHeight(),
                        mFootView.getMeasuredWidth(),
                        (int) (pullDownY + pullUpY) + mContentView.getMeasuredHeight() + mFootView.getMeasuredHeight());
    }


    /**
     * Touch 分发.
     *
     * @param event 分发Down事件
     * @return 是否中断分发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 手势 and 状态拦截器.
        // 如果松手的时候,还在刷新 or 加载 or 未结束回弹,不做任何处理.
        if (state == REFRESHING || state == LOADING || !reboundEnd) {
            super.dispatchTouchEvent(event);
            return true;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                lastY = downY;
                restorePositionTimer.cancel();
                isMultiTouch = false;
                enableFreeSliding();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                isMultiTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isMultiTouch) {
                    if (pullDownY > 0 || (((SlidingWatch) mContentView).extraViewAllowedDrop() && canPullDown && state != LOADING)) {
                        pullDownY = pullDownY + (event.getY() - lastY) / radio;
                        if (pullDownY < 0) {
                            pullDownY = 0;
                            canPullDown = false;
                            canPullUp = true;
                        }
                        if (pullDownY > getMeasuredHeight())
                            pullDownY = getMeasuredHeight();
                        if (state == REFRESHING) {
                            isTouch = true;
                        }
                    } else if (pullUpY < 0 || (((SlidingWatch) mContentView).extraViewAllowedRise() && canPullUp && state != REFRESHING)) {
                        pullUpY = pullUpY + (event.getY() - lastY) / radio;
                        if (pullUpY > 0) {
                            pullUpY = 0;
                            canPullDown = true;
                            canPullUp = false;
                        }
                        if (pullUpY < -getMeasuredHeight())
                            pullUpY = -getMeasuredHeight();
                        if (state == LOADING) {
                            isTouch = true;
                        }
                    } else {
                        enableFreeSliding();
                    }
                } else {
                    isMultiTouch = false;
                }
                lastY = event.getY();
                radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
                if (pullDownY > 0 || pullUpY < 0)
                    requestLayout();
                if (pullDownY > 0) {
                    if (pullDownY <= headDist && (state == RELEASE_TO_REFRESH || state == STAGNATION)) {
                        changeState(INIT_EXTRA_VIEW);
                    }
                    if (pullDownY >= headDist && state == INIT_EXTRA_VIEW) {
                        changeState(RELEASE_TO_REFRESH);
                    }
                } else if (pullUpY < 0) {
                    if (-pullUpY <= footDist && (state == RELEASE_TO_LOAD || state == STAGNATION)) {
                        changeState(INIT_EXTRA_VIEW);
                    }
                    if (-pullUpY >= footDist && state == INIT_EXTRA_VIEW) {
                        changeState(RELEASE_TO_LOAD);
                    }
                }
                if ((pullDownY + Math.abs(pullUpY)) > 8) {
                    event.setAction(MotionEvent.ACTION_CANCEL);
                }
                // 回调底部距离变化函数.有些控件依赖于此函数,例如,根据顶部的距离,设置透明度
                onTopDistanceChange((int) pullDownY);
                // 回调底部距离变化函数.有些控件依赖于此函数,例如,根据底部的距离,设置透明度
                onBottomDistanceChange((int) pullUpY);
                break;
            case MotionEvent.ACTION_UP:
                if (pullDownY > headDist || -pullUpY > footDist) {
                    isTouch = false;
                }
                if (state == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);
                } else if (state == RELEASE_TO_LOAD) {
                    changeState(LOADING);
                }
                restorePositionTimer.schedule(5);
            default:
                break;
        }
        super.dispatchTouchEvent(event);
        return true;
    }

    /**
     * 开启自由滑动.
     * 允许上拉的同时,也允许下拉.
     */
    private void enableFreeSliding() {
        canPullDown = true;
        canPullUp = true;
    }

    /**
     * 修改状态.
     *
     * @param state 当前状态
     */
    private void changeState(int state) {
        this.state = state;
        switch (this.state) {
            case INIT_EXTRA_VIEW:
                initExtraViewState();
                break;
            case RELEASE_TO_REFRESH:
                releaseToRefreshState();
                break;
            case REFRESHING:
                // 开始刷新说明用户已经松手了,这时候,开始计算回弹.
                // 回弹结束改为false
                reboundEnd = false;
                extraViewRefreshingState();
                break;
            case RELEASE_TO_LOAD:
                releaseToLoadState();
                break;
            case LOADING:
                // 开始加载说明用户已经松手了,这时候,开始计算回弹.
                // 回弹结束改为false
                reboundEnd = false;
                extraViewLoadingState();
                break;
            case FINISHES_LOADING:
                finishesLoadingState(loadMoreSuccess);
                new StagnationHandler().sendEmptyMessageDelayed(0, pullUpY < 0 && afterEndStayFlag ? 1200 : 0);
                break;
            case FINISHES_REFRESHING:
                finishesRefreshingState(refreshSuccess);
                new StagnationHandler().sendEmptyMessageDelayed(0, pullDownY > 0 && afterEndStayFlag ? 1200 : 0);
                break;
            case STAGNATION:
                break;
        }
    }

    /**
     * 初始化 Head 布局.
     *
     * @param freeSlidingLayout 支持自由滑动的抽象布局.
     * @return 内容视图
     */
    protected abstract View initHeadView(FreeSlidingLayout freeSlidingLayout);

    /**
     * 初始化 content 布局.
     *
     * @param freeSlidingLayout 支持自由滑动的抽象布局.
     * @return 内容视图
     */
    protected abstract View initContentView(FreeSlidingLayout freeSlidingLayout);

    /**
     * 初始化 Foot 布局.
     *
     * @return 内容视图
     */
    protected abstract View initFootView(FreeSlidingLayout freeSlidingLayout);

    /**
     * 初始化 HeadView 相关视图.
     *
     * @param view head 视图
     */
    protected abstract void findHeadViewById(View view);

    /**
     * 初始化 FootView 相关视图.
     *
     * @param view foot 视图
     */
    protected abstract void findFootViewById(View view);

    /**
     * 初始化额外视图状态.
     */
    protected abstract void initExtraViewState();

    /**
     * 释放刷新状态
     */
    protected abstract void releaseToRefreshState();

    /**
     * 刷新中状态.
     */
    protected abstract void extraViewRefreshingState();

    /**
     * 释放加载状态
     */
    protected abstract void releaseToLoadState();

    /**
     * 加载中状态.
     */
    protected abstract void extraViewLoadingState();

    /**
     * 加载更多结束.
     *
     * @param success 加载结果,true 表示成功, false 表示失败
     */
    protected abstract void finishesLoadingState(boolean success);

    /**
     * 刷新数据结束.
     *
     * @param success 说新结果,true 表示成功, false 表示失败
     */
    protected abstract void finishesRefreshingState(boolean success);

    /**
     * 内容视图距离顶部的距离发生变化.
     *
     * @param distance 距离顶部的距离.
     */
    protected abstract void onTopDistanceChange(int distance);

    /**
     * 内容视图距离底部的距离发生变化.
     *
     * @param distance 距离底部的距离.
     */
    protected abstract void onBottomDistanceChange(int distance);

    /**
     * 设置刷新结束.
     *
     * @param success 刷新数据是否成功.
     */
    public void setRefreshSuccess(boolean success) {
        this.refreshSuccess = success;
        changeState(FINISHES_REFRESHING);
    }

    /**
     * 设置加载结束.
     *
     * @param success 加载更多是否成功.
     */
    public void setLoadMoreSuccess(boolean success) {
        this.loadMoreSuccess = success;
        changeState(FINISHES_LOADING);
    }

    /**
     * 设置刷新结束后是否停留一会儿再回滚.
     *
     * @param afterEndStayFlag 是否停留.
     */
    public void setAfterEndStayFlag(boolean afterEndStayFlag) {
        this.afterEndStayFlag = afterEndStayFlag;
    }

    /**
     * 获取 HeadView 停留时的高度.
     *
     * @return HeadView 停留时的高度,单位 px
     */
    public float getHeadDist() {
        return headDist;
    }

    /**
     * 设置 HeadView 停留时的高度.单位 dp
     *
     * @param headDist HeadView 停留时的高度.单位 dp
     */
    public void setHeadDist(float headDist) {
        this.headDist = RedAgateViewUtil.dip2px(getContext(), headDist);
    }

    /**
     * 获取 FootView 停留时的高度.
     *
     * @return FootView 停留时的高度,单位 px
     */
    public float getFootDist() {
        return footDist;
    }

    /**
     * 设置 FootView 停留时的高度.
     *
     * @param footDist FootView 停留时的高度,单位 dp
     */
    public void setFootDist(float footDist) {
        this.footDist = RedAgateViewUtil.dip2px(getContext(), footDist);
    }

    /**
     * 停滞不回滚.
     */
    class StagnationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            changeState(STAGNATION);
            restorePositionTimer.schedule(5);
        }
    }

    /**
     * 回滚处理器
     */
    class RestorePositionHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            RESTORE_POSITION_MOVE_SPEED = (float) (8 + 5 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
            if (!isTouch) {
                if (state == REFRESHING && pullDownY <= headDist) {
                    pullDownY = headDist;
                    restorePositionTimer.cancel();
                } else if (state == LOADING && -pullUpY <= footDist) {
                    pullUpY = -footDist;
                    restorePositionTimer.cancel();
                }
            }
            if (pullDownY > 0)
                pullDownY -= RESTORE_POSITION_MOVE_SPEED;
            else if (pullUpY < 0)
                pullUpY += RESTORE_POSITION_MOVE_SPEED;
            if (pullDownY < 0) {
                pullDownY = 0;
                if (state != REFRESHING && state != LOADING) {
                    changeState(INIT_EXTRA_VIEW);
                }
                restorePositionTimer.cancel();
                requestLayout();
            }
            if (pullUpY > 0) {
                // 已完成回弹
                // 回弹结束改为 true

                pullUpY = 0;
                if (state != REFRESHING && state != LOADING) {
                    changeState(INIT_EXTRA_VIEW);
                }
                restorePositionTimer.cancel();
                requestLayout();
            }
            // 刷新布局,会自动调用onLayout
            requestLayout();
            if (pullDownY + Math.abs(pullUpY) == 0) {
                restorePositionTimer.cancel();
                // 在回弹任务全部执行完毕后,将回弹标签改为true.说明回弹结束,可以下拉或者上拉了.
                reboundEnd = true;
            }

        }

    }

    /**
     * 回滚定时器
     */
    class RestorePositionTimer {
        private Handler handler;
        private Timer timer;
        private RestorePositionTask mTask;

        public RestorePositionTimer(Handler handler) {
            this.handler = handler;
            timer = new Timer();
        }

        public void schedule(long period) {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
            mTask = new RestorePositionTask(handler);
            timer.schedule(mTask, 0, period);
        }

        public void cancel() {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
        }

        /**
         * 回滚任务
         */
        class RestorePositionTask extends TimerTask {
            private Handler handler;

            public RestorePositionTask(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }


        }
    }

}
