package com.ronnywu.redagate.crash.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ronnywu.redagate.listener.RedAgateLogTask;

import java.util.Iterator;
import java.util.Stack;

/**
 * 堆栈管理类.
 */
public final class RedAgateStack {

    /**************************************** 单例模式 ****************************************/

    /**
     * 单例模式.
     */
    private static RedAgateStack instance = new RedAgateStack();
    /**
     * 自己维护的堆栈.
     */
    private Stack<Activity> activityStack = new Stack<>();

    /**
     * 单例模式.
     */
    private RedAgateStack() {

    }

    /**************************************** 成员属性 ****************************************/

    /**
     * 单例模式.
     *
     * @return RedAgateStack 单例实体.
     */
    public static RedAgateStack getInstance() {
        return instance;
    }

    /**************************************** 私有方法 ****************************************/

    /**
     * 销毁所有Activity.
     */
    private void finishAllActivity() {
        synchronized (RedAgateStack.class) {
            for (Activity activity : activityStack) {
                if (activity != null) {
                    activity.finish();
                }
            }
            activityStack.clear();
        }
    }

    /**************************************** 公开方法 ****************************************/

    /**
     * 添加一个页面.
     *
     * @param activity 页面.
     */
    public void addActivity(final Activity activity) {
        synchronized (RedAgateStack.class) {
            activityStack.push(activity);
        }
    }

    /**
     * 移除一个页面.
     *
     * @param activity 页面.
     */
    public void removeActivity(final Activity activity) {
        synchronized (RedAgateStack.class) {
            if (activity != null) {
                activityStack.remove(activity);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 移除一个页面.
     *
     * @param cls 想要移除堆栈的类名.
     */
    public void removeActivity(final Class<?> cls) {
        synchronized (RedAgateStack.class) {
            for (Iterator<Activity> it = activityStack.iterator(); it.hasNext(); ) {
                Activity activity = it.next();
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    if (!activity.isFinishing()) {
                        activity.finish();
                    }
                }
            }
        }
    }

    /**
     * 获取当前Activity.
     *
     * @return 最后一个进入堆栈的页面.
     */
    public Activity getLastActivity() {
        synchronized (RedAgateStack.class) {
            return activityStack.lastElement();
        }
    }

    /**
     * 注销应用.
     *
     * @param context    上下文
     * @param startClass 注销后想要启动的页面.
     */
    public void cancelApp(final Context context, final Class startClass) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(" 注销应用");
        finishAllActivity();
        Intent logoutIntent = new Intent(context, startClass);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(logoutIntent);
    }

    /**
     * 退出应用.
     */
    public void exitApp() {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(" 退出应用");
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
