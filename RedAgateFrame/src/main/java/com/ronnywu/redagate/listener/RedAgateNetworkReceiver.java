package com.ronnywu.redagate.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;

import com.ronnywu.redagate.application.RedAgateApplication;
import com.ronnywu.redagate.utils.manager.RedAgateManagerUtil;

/**
 * 网络状态监听器.
 */
public abstract class RedAgateNetworkReceiver extends BroadcastReceiver {

    /**
     * 网络已连接.
     */
    public abstract void NetworkConnected();

    /**
     * 网络连接中断.
     */
    public abstract void NetworkDisconnected();

    /**
     * 广播接收器.
     *
     * @param context 上下文
     * @param intent  意图
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo networkInfo = RedAgateManagerUtil.getActiveNetworkInfo(RedAgateApplication.getInstance());
        changeNetworkAvailable(networkInfo != null);
    }

    /**
     * 修改网络状态
     * 这是一个同步方法，原因：
     * 1.防止短时间同时接收到多次广播，同时修改网络状态
     * 2.防止网络状态出现不准的可能
     *
     * @param isConnected 网络属性.
     */
    private synchronized void changeNetworkAvailable(boolean isConnected) {
        switch (RedAgateState.getInstance().lastNetworkState) {
            // 上次没有网络
            case 0:
                if (isConnected) {
                    NetworkConnected();
                    RedAgateState.getInstance().lastNetworkState = 1;
                }
                break;
            // 上次有网络
            case 1:
                if (!isConnected) {
                    NetworkDisconnected();
                    RedAgateState.getInstance().lastNetworkState = 0;
                }
                break;
            // 无上次网络状态
            case 2:
                if (isConnected) {
                    NetworkConnected();
                    RedAgateState.getInstance().lastNetworkState = 1;
                } else {
                    NetworkDisconnected();
                    RedAgateState.getInstance().lastNetworkState = 0;
                }
                break;
        }

    }
}
