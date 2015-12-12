package com.ronnywu.redagate.example.sliding_view.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.listener.RedAgateLogTask;
import com.ronnywu.redagate.ui.sliding.arrow_task.ArrowTaskSlidingLayout;
import com.ronnywu.redagate.ui.sliding.core.DoTaskListener;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 传统(箭头)下拉刷新,上拉加载更多.
 */
public class ArrowTaskSlidingLayoutExampleActivity extends BaseActivity {

    @Bind(R.id.arrow_task_sliding_layout)
    ArrowTaskSlidingLayout arrowTaskSlidingLayout;

    /**
     * 创建页面.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrow_task_sliding_layout_example);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        arrowTaskSlidingLayout.setDoTaskListener(new DoTaskListener() {
            @Override
            public void onRefresh() {
                RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i("正在刷新...");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrowTaskSlidingLayout.setRefreshSuccess(true);
                        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i("刷新结束!!!");
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i("正在加载...");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrowTaskSlidingLayout.setLoadMoreSuccess(true);
                        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i("加载结束!!!");
                    }
                }, 3000);
            }
        });
    }

    /**
     *
     */
    public static void actionStart(Activity activity){
        RedAgateActionUtil.actionStart(activity,ArrowTaskSlidingLayoutExampleActivity.class);
    }
}
