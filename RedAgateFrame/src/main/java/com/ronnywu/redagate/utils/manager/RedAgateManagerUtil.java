package com.ronnywu.redagate.utils.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Manager 工具类.
 * 提供获取各种 Manager 的方法.
 */
public class RedAgateManagerUtil {

    /**
     * 获取 ConnectivityManager 对象
     *
     * @return ConnectivityManager 对象
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取 WifiManager 对象
     *
     * @return WifiManager 对象
     */
    public static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取 WifiInfo 对象
     *
     * @return WifiInfo 对象
     */
    public static WifiInfo getWifiInfo(Context context) {
        return getWifiManager(context).getConnectionInfo();
    }

    /**
     * 获取 NetworkInfo[] 对象
     *
     * @return NetworkInfo[] 对象
     */
    @SuppressWarnings("deprecation")
    public static NetworkInfo[] getAllNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        if (connectivityManager != null) {
            return connectivityManager.getAllNetworkInfo();
        }
        return null;
    }

    /**
     * 获取 NetworkInfo 对象
     *
     * @return NetworkInfo 对象
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }
}
