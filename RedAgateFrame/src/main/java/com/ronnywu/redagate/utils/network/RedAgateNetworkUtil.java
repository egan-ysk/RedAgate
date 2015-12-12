package com.ronnywu.redagate.utils.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.ronnywu.redagate.application.RedAgateApplication;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.listener.RedAgateLogTask;
import com.ronnywu.redagate.utils.manager.RedAgateManagerUtil;

/**
 * 网络工具类.
 */
public class RedAgateNetworkUtil {

    /**
     * 获取当前网络是否可用
     *
     * @return true 表示网络可用，false表示网络不可用
     */
    public static boolean getNetworkIsAvailable() {
        NetworkInfo networkInfo = RedAgateManagerUtil.getActiveNetworkInfo(RedAgateApplication.getInstance());
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * 获取当前网络类型
     *
     * @return “WIFI”,“2G”,“3G”,“4G”四种字符串及未判断到的网络类型
     */
    public static String getNetworkTypeStr() {
        String strNetworkType = "";
        NetworkInfo networkInfo = RedAgateManagerUtil.getActiveNetworkInfo(RedAgateApplication.getInstance());
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String subTypeName = networkInfo.getSubtypeName();
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        strNetworkType = "4G";
                        break;
                    default:
                        if (subTypeName.equalsIgnoreCase("TD-SCDMA")
                                || subTypeName.equalsIgnoreCase("WCDMA")
                                || subTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = subTypeName;
                            RedAgateLog.wrlog(RedAgateLogTask.getInstance().NET).w("发现未判断的网络类型");
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取当前网络类型
     *
     * @return 0、1、2、3、4其一;
     * 未知网络 0 .
     * WIFI网络 1 .
     * 2G网络 2 .
     * 3G网络 3 .
     * 4G网络 4 .
     */
    public static int getNetworkType() {
        String networkTypeString = getNetworkTypeStr();
        //noinspection IfCanBeSwitch
        if (networkTypeString.equals("WIFI")) {
            return 1;
        } else if (networkTypeString.equals("4G")) {
            return 4;
        } else if (networkTypeString.equals("3G")) {
            return 3;
        } else if (networkTypeString.equals("2G")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 获取当前设备的MAC地址
     *
     * @return MAC地址
     */
    public static String getMacAddress() {
        return RedAgateManagerUtil.getWifiInfo(RedAgateApplication.getInstance()).getMacAddress();
    }
}
