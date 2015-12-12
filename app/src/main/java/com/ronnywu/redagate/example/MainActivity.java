package com.ronnywu.redagate.example;

import android.os.Bundle;
import android.widget.Button;

import com.ronnywu.redagate.example.application.BaseActivity;
import com.ronnywu.redagate.example.hint_view.HintViewExampleActviity;
import com.ronnywu.redagate.example.sliding_view.SlidingViewExampleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 入口页面.
 */
public class MainActivity extends BaseActivity {

    /**
     * 滑动视图控件使用示例.
     */
    @Bind(R.id.button_sliding_view_example)
    Button buttonSlidingViewExample;

    /**
     * 提示控件使用示例.
     */
    @Bind(R.id.button_hint_example)
    Button buttonHintExample;

    /**
     * 创建页面.
     *
     * @param savedInstanceState 保存状态实例引用.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * 滑动控件例子.
     */
    @OnClick(R.id.button_sliding_view_example)
    void slidingViewExampleOnClick() {
        SlidingViewExampleActivity.actionStart(this);
    }

    /**
     * 提示控件使用示例.
     */
    @OnClick(R.id.button_hint_example)
    void hintExampleOnClick() {
        HintViewExampleActviity.actionStart(this);
    }

}
