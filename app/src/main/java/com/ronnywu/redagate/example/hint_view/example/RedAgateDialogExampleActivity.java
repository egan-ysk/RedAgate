package com.ronnywu.redagate.example.hint_view.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ronnywu.redagate.example.R;
import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.ui.hint.RedAgateLoadingDialog;
import com.ronnywu.redagate.utils.action.RedAgateActionUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 */
public class RedAgateDialogExampleActivity extends BaseActivity {

    @Bind(R.id.button_dialog_1)
    Button buttonDialog1;
    @Bind(R.id.button_dialog_2)
    Button buttonDialog2;

    /**
     * 创建页面.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_agate_dialog_example);
        ButterKnife.bind(this);
    }

    /**
     *
     */
    @OnClick(R.id.button_dialog_1)
    void dialogOneOnClick() {
        RedAgateLoadingDialog loadingDialog = new RedAgateLoadingDialog(this);
        loadingDialog.setTitle("正在加载");
        loadingDialog.show();
    }

    /**
     *
     */
    @OnClick(R.id.button_dialog_2)
    void dialogTwoOnClick() {
        QuitAppDialogActivity.actionStart(this);
    }

    /**
     *
     */
    public static void actionStart(Activity activity) {
        RedAgateActionUtil.actionStart(activity, RedAgateDialogExampleActivity.class);
    }
}
