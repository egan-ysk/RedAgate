package com.ronnywu.redagate.http.control;

import com.android.volley.toolbox.NetworkImageView;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.http.core.RedAgateImageLoader;
import com.ronnywu.redagate.http.core.RedAgateQueue;
import com.ronnywu.redagate.listener.RedAgateLogTask;

/**
 * 图片工具类,封装的图片下载相关的逻辑.
 *
 * @author ronnywu
 * @date 2015-11-11
 * @time 20:11:47
 */
public class RedAgateImage {

    /**
     * 这个方法必须支持多线程操作.
     *
     * @param url               请求网址
     * @param networkImageView  网络图片控件
     * @param defaultImageResId 默认图片资源ID
     */
    public static void downloadImage(
            final String url,
            final NetworkImageView networkImageView,
            final int defaultImageResId
    ) {
        downloadImage(url, networkImageView, defaultImageResId, defaultImageResId);
    }

    /**
     * 这个方法必须支持多线程操作.
     *
     * @param url               请求网址
     * @param networkImageView  网络图片控件
     * @param defaultImageResId 默认图片资源ID
     * @param errorImageResId   加载失败图片资源ID
     */
    public static void downloadImage(
            final String url,
            final NetworkImageView networkImageView,
            final int defaultImageResId,
            final int errorImageResId
    ) {
        if (networkImageView == null) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().ERROR).e("控件未初始化,不能使用空控件下载图片");
            return;
        }
        networkImageView.setDefaultImageResId(defaultImageResId);
        networkImageView.setErrorImageResId(errorImageResId);
        networkImageView.setImageUrl(url, RedAgateImageLoader.getInstance());
        RedAgateQueue.getInstance().getSequenceNumber();
    }

}
