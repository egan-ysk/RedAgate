package com.ronnywu.redagate.http.handler;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.http.core.RedAgateQueue;
import com.ronnywu.redagate.http.listener.RedAgateRequestListener;
import com.ronnywu.redagate.http.request.RedAgateRequest;
import com.ronnywu.redagate.listener.RedAgateLogTask;

import java.util.Map;

/**
 * 请求处理类
 * 这是一个抽象类
 *
 * @author ronnywu
 * @date 2015-09-15
 * @time 15:53:26
 */
@SuppressWarnings({"JavaDoc", "unused"})
public abstract class RedAgateRequestHandler<T>
        implements Response.ErrorListener, Response.Listener<String> {

    /***************************************** 成员变量 ****************************************/

    /**
     * Volley 请求队列
     */
    private Activity activity;

    /**
     * UI界面传入的请求回调监听器
     */
    private RedAgateRequestListener<T> redAgateRequestListener;

    /**
     * 构造器.
     *
     * @param activity 页面.
     */
    public RedAgateRequestHandler(Activity activity) {
        this.activity = activity;
    }

    /****************************** 获取一个Volley Request 对象 *****************************/

    /**
     * 根据 params 获取 Request 类
     * 在创建请求时,使用了自定义的请求基类,同时以 RequestQueue 作为 Request TAG
     *
     * @param url    URL地址
     * @param params 请求参数
     * @return Volley Request 对象
     */
    protected Request getRequest
    (
            @NonNull String url,
            @Nullable Map<String, String> params,
            int method
    ) {
        Request request;
        if (params == null) {
            request = new RedAgateRequest(method, url, this, this);
        } else {
            request = new RedAgateRequest(method, url, params, this, this);
        }
        // 设置超时策略
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0F));
        // 使用activity作为请求的标识符,方便销毁页面时取消请求,避免浪费资源.
        request.setTag(activity);
        return request;
    }

    /***************************************** 发送GET请求 ****************************************/

    /**
     * 发送 GET 请求
     *
     * @param url    URL地址
     * @param params 请求参数
     */
    public void sendGetRequest(String url, Map<String, String> params) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).v("请求方式 >>> GET");
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).v("请求url >>> " + url);
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).v("请求参数 >>> " + params.toString());
        RedAgateQueue.getInstance().add(getRequest(url, params, Request.Method.GET));
    }

    /***************************************** 发送POST请求 ****************************************/

    /**
     * 发送 POST 请求
     *
     * @param url    URL地址
     * @param params 请求参数
     */
    public void sendPostRequest(String url, Map<String, String> params) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).v("请求方式 >>> POST");
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).v("请求url >>> " + url);
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).v("请求参数 >>> " + params.toString());
        RedAgateQueue.getInstance().add(getRequest(url, params, Request.Method.POST));
    }

    /************************************** 拦截[Volley已封装的错误] *************************************/

    /**
     * 监听 and 分发 Volley 错误
     *
     * @param error request to return received error.
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        // 分发 Volley 错误
        disposeVolleyError(error);
    }

    /************************************** 拦截[Volley请求成功] *************************************/

    /**
     * 监听 and 分发 请求成功的事件
     */
    @Override
    public void onResponse(String response) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).v("请求完成 >>> " + response);
        // 分发解析
        disposeAnalysis(response);
    }

    /************************************** 处理错误信息 *************************************/

    /**
     * 我自己写的分发 Volley 错误方法
     * 在当前方法中,处理 Volley 错误
     *
     * @param error request to return received error.
     */
    protected void disposeVolleyError(VolleyError error) {
        if (error instanceof AuthFailureError) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).i("HTTP请求认证失败!");
            disposeError("LS-000", "哎呀,Auth认证失败啦!");
        } else if (error instanceof NoConnectionError) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).i("无法创建请求连接");
            disposeError("LS-001", "哎呀,当前网络不可用!");
        } else if (error instanceof NetworkError) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).i("服务器已宕机?");
            disposeError("RS-000", "哎呀,服务器正在升级!");
        } else if (error instanceof ServerError) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).i("服务器参数错误");
            disposeError("RS-001", "哎呀,服务器出错啦!");
        } else if (error instanceof TimeoutError) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).i("请求超时!");
            disposeError("LS-002", "哎呀,网络请求超时啦!");
        }
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().ERROR).e(error);
    }

    /************************************** 设置[解析监听器] *************************************/

    /**
     * 设置 http 请求数据解析监听器
     * 设置我自己写的请求监听器
     */
    public void setRedAgateRequestListener(RedAgateRequestListener<T> redAgateRequestListener) {
        this.redAgateRequestListener = redAgateRequestListener;
    }

    /************************************** 分发[请求成功] *************************************/

    /**
     * 我自己写的请求成功的分发方法
     * <p/>
     * 触发机制:请求成功 and 解析成功
     */
    protected void disposeSuccess(T data) {
        if (redAgateRequestListener != null) {
            redAgateRequestListener.requestSuccess(data);
        }
    }

    /************************************** 分发[请求失败] *************************************/

    /**
     * 我自己写的请求失败分发方法
     * <p/>
     * 触发机制:
     * 1. 请求失败
     * 2. 解析失败
     * 3. 其他原因导致失败
     * 4. 发生逻辑错误
     *
     * @param errorCode    错误编号
     * @param errorMessage 错误消息
     */
    protected void disposeError(String errorCode, String errorMessage) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).i("使用回调将错误消息传递至页面!");
        if (redAgateRequestListener != null) {
            // 直接调用解析失败的处理方法,将错误传递到页面
            redAgateRequestListener.requestFailure(errorCode, errorMessage);
        }
    }

    /************************************** 分发[解析失败错误] *************************************/

    /**
     * 分发解析错误
     * 在当前方法中,分发数据解析失败的错误
     * 当前方法,由解析类调用
     */
    protected void disposeAnalysisError() {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().HTTP).i("解析出错!");
        // 调用分发错误
        disposeError("LS-003", "哎呀,数据解析失败了");
    }

    /************************************** 分发[数据解析]操作 *************************************/

    /**
     * 分发数据解析操作
     * 在当前方法中,分发并处理数据解析操作,然后调用相关方法,分发错误
     *
     * @param response request to return data.
     */
    protected abstract void disposeAnalysis(String response);


}