package com.ronnywu.redagate.web.client;

/**
 * WebView 加载出错接口
 */
public interface WebViewLoadListener {

    /**
     * 没有网络
     */
    void onNotNetwork();

    /**
     * 404等,不限于404
     */
    void onNotFound();
}
