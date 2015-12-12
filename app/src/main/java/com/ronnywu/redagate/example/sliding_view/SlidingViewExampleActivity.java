package com.ronnywu.redagate.example.sliding_view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.example.sliding_view.example.ArrowTaskSlidingLayoutExampleActivity;
import com.ronnywu.redagate.example.sliding_view.example.BallTaskSlidingLayoutExampleActivity;
import com.ronnywu.redagate.example.sliding_view.example.DampingSlidingLayoutExampleActivity;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SlidingViewExampleActivity extends BaseActivity {

    /**
     * 阻尼视图示例
     */
    @Bind(R.id.button_damping_sliding_layout_example)
    Button buttonDampingSlidingLayoutExample;

    /**
     * 传统(箭头)下拉刷新,上拉加载更多.
     */
    @Bind(R.id.button_arrow_task_sliding_layout_example)
    Button buttonArrowTaskSlidingLayoutExample;

    /**
     * 球形下拉刷新,上拉加载更多.
     */
    @Bind(R.id.button_ball_task_sliding_layout_example)
    Button buttonBallTaskSlidingLayoutExample;

    /**
     * 创建页面.
     *
     * @param savedInstanceState 保存状态实例引用.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_view_example);
        ButterKnife.bind(this);
    }

    /**
     * 启动滑动视图控件演示页面
     */
    public static void actionStart(Activity activity) {
        RedAgateActionUtil.actionStart(activity, SlidingViewExampleActivity.class);
    }

    /**
     * 阻尼视图示例
     */
    @OnClick(R.id.button_damping_sliding_layout_example)
    void dampingSlidingLayoutExampleOnClick() {
        DampingSlidingLayoutExampleActivity.actionStart(this);
    }

    /**
     * 传统(箭头)下拉刷新,上拉加载更多.
     */
    @OnClick(R.id.button_arrow_task_sliding_layout_example)
    void arrowTaskSlidingLayoutExampleOnClick() {
        ArrowTaskSlidingLayoutExampleActivity.actionStart(this);
    }

    /**
     * 球形下拉刷新,上拉加载更多.
     */
    @OnClick(R.id.button_ball_task_sliding_layout_example)
    void ballTaskSlidingLayoutExampleOnClick() {
        BallTaskSlidingLayoutExampleActivity.actinStart(this);
    }
}
