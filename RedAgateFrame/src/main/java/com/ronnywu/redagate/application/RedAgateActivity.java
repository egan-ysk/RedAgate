package com.ronnywu.redagate.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.crash.tools.RedAgateStack;
import com.ronnywu.redagate.http.core.RedAgateQueue;
import com.ronnywu.redagate.listener.RedAgateLogTask;

/**
 * 框架Activity.
 */
public class RedAgateActivity extends AppCompatActivity {

    /**
     * 是否开启后退功能.
     * 如果不开启,用户点击返回键时,直接返回桌面,应用转入后台运行.
     * 如果开启,用户点击返回键时,执行返回键默认功能-后退.
     */
    private boolean goBackEnable = true;

    /**
     * 创建页面.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 操作:纳入堆栈管理
        RedAgateStack.getInstance().addActivity(this);
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).v(this.getClass().getSimpleName()
                + " 创建页面");
    }

    /**
     * 销毁页面.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消该页面的请求
        RedAgateQueue.getInstance().cancelAll(this);
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).v(this.getClass().getSimpleName()
                + " 取消请求");
        // 操作:移除堆栈管理
        RedAgateStack.getInstance().removeActivity(this);
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).v(this.getClass().getSimpleName()
                + " 销毁页面");
    }

    /**
     * 键盘按下事件.
     */
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        // 过滤按键动作
        if (!goBackEnable && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // 返回键直接将程序设置到后台,不退出
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 重写返回键.
     */
    @Override
    public void onBackPressed() {
        if (!goBackEnable) {
            // 返回键直接将程序设置到后台,不退出
            moveTaskToBack(true);
        }
        super.onBackPressed();
    }

    /**
     * 能否后退.
     * 其中不能后退,表示返回键会将程序转入后台.
     * 能后退,表示返回键就是后退键,同系统.
     *
     * @return 能否后退
     */
    public boolean isGoBackEnable() {
        return goBackEnable;
    }

    /**
     * 设置后退可用性
     *
     * @param goBackEnable 是否可后退
     */
    public void setGoBackEnable(boolean goBackEnable) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).v(this.getClass().getSimpleName()
                + " 开启后台运行,默认为关闭");
        this.goBackEnable = goBackEnable;
    }
}
