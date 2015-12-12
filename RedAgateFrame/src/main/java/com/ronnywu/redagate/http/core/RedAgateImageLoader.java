package com.ronnywu.redagate.http.core;

import com.android.volley.toolbox.ImageLoader;

/**
 * ImageLoader封装
 * 单例模式
 */
public class RedAgateImageLoader {

    /**
     * 代理单例
     */
    private static RedAgateImageLoader ourInstance;
    /**
     * 实际单例
     */
    private ImageLoader imageLoader;

    /**
     * 单例构造器
     */
    private RedAgateImageLoader() {
        this.imageLoader = new ImageLoader(RedAgateQueue.getInstance(), RedAgateImageCache.getInstance());
    }

    /**
     * 代理单例方法
     */
    public static synchronized ImageLoader getInstance() {
        if (ourInstance == null) {
            ourInstance = new RedAgateImageLoader();
        }
        return ourInstance.imageLoader;
    }


}
