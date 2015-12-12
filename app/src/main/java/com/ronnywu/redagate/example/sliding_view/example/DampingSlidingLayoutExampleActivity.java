package com.ronnywu.redagate.example.sliding_view.example;

import android.app.Activity;
import android.os.Bundle;

import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;

/**
 * 阻尼效果控件.
 */
public class DampingSlidingLayoutExampleActivity extends BaseActivity {

    /**
     * 创建页面.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damping_sliding_layout_example);
    }

    public static void actionStart(Activity activity) {
        RedAgateActionUtil.actionStart(activity, DampingSlidingLayoutExampleActivity.class);
    }
}
