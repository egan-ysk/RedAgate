package com.ronnywu.redagate.ui.hint;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ronnywu.redagate.R;
import com.ronnywu.redagate.application.RedAgateApplication;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.crash.tools.RedAgateStack;
import com.ronnywu.redagate.listener.RedAgateLogTask;

/**
 * 吐司工具类
 *
 * @author RonnyWu.pe@gmail.com
 * @date 2015-5-18
 * @time 10:56:31
 */
@SuppressWarnings("ALL")
public class RedAgateToast {

    /**
     * 定义一个吐司
     */
    private static Toast mToast = null;

    /**
     * 显示吐司.
     * 系统吐司和自定义吐司的区别,系统吐司可以在任意位置吐司,
     * 但是自定义吐司只能使用Activity的getLayoutInflater进行吐司.
     */
    public static void showToast(String message, int duration) {
        if (mToast == null) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).wrlog("使用自定义吐司");
            Activity activity = RedAgateStack.getInstance().getLastActivity();
            // 如果拿不到Activity,则使用系统自己的吐司.
            mToast = Toast.makeText(RedAgateApplication.getInstance(), message, duration);
            if (activity != null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View layout = inflater.inflate(R.layout.red_agate_custom_toast, null);
                TextView textViewCustomToastMessage = (TextView) layout.findViewById(R.id.text_view_custom_toast_message);
                textViewCustomToastMessage.setText(message);
                mToast.setView(layout);
            }
        } else {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).wrlog("使用系统吐司");
            try {
                TextView textViewCustomToastMessage = (TextView) mToast.getView().findViewById(R.id.text_view_custom_toast_message);
                textViewCustomToastMessage.setText(message);
            } catch (Exception e) {
                mToast.setText(message);
            }
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * 短吐司
     */
    public static void toastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    /**
     * 长吐司
     */
    public static void toastLong(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

}
