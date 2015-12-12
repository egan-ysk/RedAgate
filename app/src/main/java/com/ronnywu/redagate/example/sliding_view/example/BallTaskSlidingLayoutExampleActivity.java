package com.ronnywu.redagate.example.sliding_view.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.ui.sliding.ball_task.BallTaskSlidingLayout;
import com.ronnywu.redagate.ui.sliding.core.DoTaskListener;
import com.ronnywu.redagate.ui.sliding.ui.SlidingRelativeLayout;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 */
public class BallTaskSlidingLayoutExampleActivity extends BaseActivity {


    @Bind(R.id.head_ball_task_sliding_layout_ball)
    ImageView headBallTaskSlidingLayoutBall;
    @Bind(R.id.ball_task_sliding_layout_head)
    RelativeLayout ballTaskSlidingLayoutHead;
    @Bind(R.id.ball_task_sliding_layout_content)
    SlidingRelativeLayout ballTaskSlidingLayoutContent;
    @Bind(R.id.ball_task_sliding_layout_foot)
    RelativeLayout ballTaskSlidingLayoutFoot;
    @Bind(R.id.ball_task_sliding_layout)
    BallTaskSlidingLayout ballTaskSlidingLayout;

    /**
     * 创建页面.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_task_sliding_layout_example);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        ballTaskSlidingLayout.setDoTaskListener(new DoTaskListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ballTaskSlidingLayout.setRefreshSuccess(true);
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ballTaskSlidingLayout.setLoadMoreSuccess(true);
                    }
                }, 3000);
            }
        });
    }

    /**
     * 启动 BallTaskSlidingLayout
     *
     * @param activity 上下文
     */
    public static void actinStart(Activity activity) {
        RedAgateActionUtil.actionStart(activity, BallTaskSlidingLayoutExampleActivity.class);
    }


}
