package com.ronnywu.redagate.utils.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Action Start 封装
 * 对Activity的静态方法ActionStart提供支持
 *
 * @author ronnywu
 * @date 2015-10-10
 * @time 17:23:03
 */
public class RedAgateActionUtil {


    /**
     * 不带参数启动另外一个页面
     *
     * @param activity 上下文
     * @param cls      启动页面类名
     */
    public static void actionStart(Activity activity, Class cls) {
        actionStart(activity, cls, (int[]) null);
    }

    /**
     * 不带参数且带不定数量的IntentFlags启动一个页面
     *
     * @param activity    上下文
     * @param cls         启动页面类名
     * @param intentFlags 一个或多个IntentFlags
     */
    public static void actionStart(Activity activity, Class cls, int... intentFlags) {
        actionStart(activity, cls, null, intentFlags);
    }

    /**
     * 带参数启动另外一个页面
     *
     * @param activity 上下文
     * @param cls      启动页面类名
     * @param bundle   参数
     */
    public static void actionStart(Activity activity, Class cls, Bundle bundle) {
        actionStart(activity, cls, bundle, 0);
    }

    /**
     * 带参数且带不定数量的IntentFlags启动一个页面
     *
     * @param activity    上下文
     * @param cls         启动页面类名
     * @param bundle      携带参数
     * @param intentFlags 一个或多个IntentFlags
     */
    public static void actionStart(Activity activity, Class cls, Bundle bundle, int... intentFlags) {
        Intent intent = new Intent(activity, cls);
        if (intentFlags != null) {
            for (int i = 0; i < intentFlags.length; i++) {
                if (intentFlags[i] != 0) {
                    if (i == 0) {
                        intent.setFlags(intentFlags[i]);
                    } else {
                        intent.addFlags(intentFlags[i]);
                    }
                }
            }
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }
}