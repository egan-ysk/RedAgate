package com.ronnywu.redagate.crash.exception;

import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.ronnywu.redagate.application.RedAgateApplication;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.crash.tools.RedAgateStack;
import com.ronnywu.redagate.listener.RedAgateLogTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;


/**
 * Crash 捕获类.
 */
public final class RedAgateUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 单例模式.
     */
    private static RedAgateUncaughtExceptionHandler instance = new RedAgateUncaughtExceptionHandler();

    /**
     * 构造器.
     */
    private RedAgateUncaughtExceptionHandler() {

    }

    /**
     * 单例模式.
     *
     * @return RedAgateUncaughtExceptionHandler单例实体对象
     */
    public static RedAgateUncaughtExceptionHandler getInstance() {
        return instance;
    }

    /**
     * 重写未捕获异常的处理方法.
     */
    @Override
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            try {
                throwable.printStackTrace(new PrintStream(baos));
            } finally {
                baos.close();
            }
        } catch (IOException e) {
            throwable.printStackTrace();
        }
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().CRASH).e(baos.toString());
        // 错误对话框
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(RedAgateApplication.getInstance()).
                            setMessage("抱歉,程序因异常而崩溃,是否现在退出?").
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, final int which) {
                                    RedAgateStack.getInstance().exitApp();
                                }
                            }).create();
                    Window window = alertDialog.getWindow();
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    attributes.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                    window.setAttributes(attributes);
                    alertDialog.show();
                } catch (Exception e) {
                    RedAgateLog.wrlog(RedAgateLogTask.getInstance().ERROR).e(e);
                }
                Looper.loop();
            }
        }).start();
    }

    /**
     * 初始化异常处理.
     */
    public void initCrashMange() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).v(" 异常处理初始化成功");
    }
}
