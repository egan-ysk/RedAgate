package com.ronnywu.redagate.http.core;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ronnywu.redagate.application.RedAgateApplication;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.listener.RedAgateLogTask;

/**
 * 请求队列
 * 使用单例模式,防止多个请求队列的创建
 */
public class RedAgateQueue {

    /****************************************** 成员变量 ****************************************/

    /**
     * 实际单例
     */
    private static RequestQueue mRequestQueue;

    /**
     * 代理单例
     */
    private static RedAgateQueue mInstance;

    /****************************************** 单例模式 ****************************************/


    /**
     * 单例模式
     */
    private RedAgateQueue() {
        if (mRequestQueue == null) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(" 创建请求队列");
            mRequestQueue = Volley.newRequestQueue
                    (
                            RedAgateApplication.getInstance(),
                            RedAgateHurlStack.getInstance()
                    );
        }
    }

    /**
     * 代理单例
     */
    public static synchronized RequestQueue getInstance() {
        if (mRequestQueue == null) {
            mInstance = new RedAgateQueue();
        }
        return mRequestQueue;
    }
}
