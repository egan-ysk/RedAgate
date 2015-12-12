package com.ronnywu.redagate.http.core;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.listener.RedAgateLogTask;

/**
 * 缓存类,单例模式.
 */
public class RedAgateImageCache implements ImageLoader.ImageCache {

    /**
     * 单例模式.
     */
    private static RedAgateImageCache ourInstance = new RedAgateImageCache();
    /**
     * 缓存队列.
     */
    private LruCache<String, Bitmap> mCache;

    /**
     * 单例模式
     */
    private RedAgateImageCache() {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(" 开启图片内存缓存,占用内存10M");
        // 最大值,10M
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    /**
     * 单例模式.
     */
    public static RedAgateImageCache getInstance() {
        return ourInstance;
    }

    @Override
    public Bitmap getBitmap(String url) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(" 图片缓存中(内存)读取图片");
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(" 将图片放入缓存中(内存)");
        mCache.put(url, bitmap);
    }
}
