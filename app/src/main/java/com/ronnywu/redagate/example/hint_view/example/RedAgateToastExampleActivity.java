package com.ronnywu.redagate.example.hint_view.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.ui.hint.RedAgateToast;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 */
public class RedAgateToastExampleActivity extends BaseActivity {

    @Bind(R.id.button_toast_1)
    Button buttonToast1;

    /**
     * 创建页面.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_agate_toast_example);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_toast_1)
    void toastOneOnClick() {
        RedAgateToast.toastLong("我是长吐司!");
    }

    /**
     * 启动 RedAgateToastExampleActivity 页面
     *
     * @param activity
     */
    public static void actionStart(Activity activity) {
        RedAgateActionUtil.actionStart(activity, RedAgateToastExampleActivity.class);
    }
}
