package com.ronnywu.redagate.utils.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基于 View 的方法封装
 */
public class RedAgateViewUtil {

    /**
     * View 方法中没有提供设置外边距的方法,这里封装一个方法,提供给 View 使用
     *
     * @param v view
     * @param l left margin
     * @param t top margin
     * @param r right margin
     * @param b bottom margin
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将 Activity 改为 Dialog 样式.
     */
    public static void changeStyleToBottomDialog(Activity activity) {
        // 位置改为底部.
        activity.getWindow().setGravity(Gravity.BOTTOM);
        // 宽度填充,高度包裹.
        activity.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 将 Activity 改为 Dialog 样式.
     */
    public static void changeStyleToDialog(Activity activity) {
        // 宽度填充,高度包裹.
        activity.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


}
