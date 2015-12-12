package com.ronnywu.redagate.example.hint_view.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ronnywu.redagate.crash.tools.RedAgateStack;
import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;
import com.ronnywu.redagate.utils.view.RedAgateViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退出应用对话框.
 *
 * @author ronnywu
 * @date 2015-11-30
 * @time 16:19:43
 */
public class QuitAppDialogActivity extends BaseActivity {

    /**
     * 退出
     */
    @Bind(R.id.text_view_quit_app)
    TextView textViewQuitApp;

    /**
     * 取消
     */
    @Bind(R.id.text_view_quit_cancel)
    TextView textViewQuitCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_quit_app);
        ButterKnife.bind(this);
        RedAgateViewUtil.changeStyleToBottomDialog(this);
    }

    /**
     * 退出
     */
    @OnClick(R.id.text_view_quit_app)
    void textViewQuitAppOnClick() {
        RedAgateStack.getInstance().exitApp();
    }

    /**
     * 取消
     */
    @OnClick(R.id.text_view_quit_cancel)
    void textViewQuitCancelOnClick() {
        finish();
        overridePendingTransition(android.R.anim.fade_in,R.anim.anim_bottom_out);
    }

    /**
     * 启动退出对话框
     */
    public static void actionStart(Activity activity) {
        RedAgateActionUtil.actionStart(activity, QuitAppDialogActivity.class);
    }

}
