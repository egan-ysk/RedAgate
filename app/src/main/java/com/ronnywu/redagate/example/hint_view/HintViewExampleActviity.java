package com.ronnywu.redagate.example.hint_view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.example.hint_view.example.RedAgateDialogExampleActivity;
import com.ronnywu.redagate.example.hint_view.example.RedAgateToastExampleActivity;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class HintViewExampleActviity extends BaseActivity {

    @Bind(R.id.button_toast_example)
    Button buttonToastExample;
    @Bind(R.id.button_loading_dialog_example)
    Button buttonLoadingDialogExample;

    /**
     * 创建页面.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_view_example);
        ButterKnife.bind(this);
    }

    /**
     * 点击 Toast 控件演示
     */
    @OnClick(R.id.button_toast_example)
    void toastExampleOnClick() {
        RedAgateToastExampleActivity.actionStart(this);
    }

    /**
     * 点击 Dialog 控件演示
     */
    @OnClick(R.id.button_loading_dialog_example)
    void loadingDialogExampleOnClick() {
        RedAgateDialogExampleActivity.actionStart(this);
    }

    /**
     * 启动 HintView 控件演示页面
     */
    public static void actionStart(Activity activity) {
        RedAgateActionUtil.actionStart(activity, HintViewExampleActviity.class);
    }
}
