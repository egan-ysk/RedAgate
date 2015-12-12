package com.ronnywu.redagate.application;

import android.app.Application;
import android.content.Context;

import com.ronnywu.redagate.crash.exception.RedAgateUncaughtExceptionHandler;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.listener.RedAgateLogTask;

/**
 * 框架application.
 */
public class RedAgateApplication extends Application {

    /**
     * Application Context.
     */
    private static Context appContext;

    /**
     * 获取 Application Context.
     *
     * @return Application Context.
     */
    public static Context getInstance() {
        if (appContext == null) {
            throw new NullPointerException("RedAgateApplication"
                    + "#getInstance()方法不得在RedAgateApplication未创建时调用!"
                    + "请注意调用时机和相关依赖方法的使用顺序!"
            );
        }
        return appContext;
    }

    /**
     * 创建应用.
     */
    @Override
    public void onCreate() {
        // 先初始化异常处理
        RedAgateUncaughtExceptionHandler.getInstance().initCrashMange();
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).v(" 开启异常处理");
        // 再创建页面
        super.onCreate();
        // 最后初始化其他操作
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).v(" 启动应用");
        appContext = this;

    }
}
