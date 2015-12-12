package com.ronnywu.redagate.web.client;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.ronnywu.redagate.web.core.RedAgateBaseWebClient;
import com.ronnywu.redagate.web.view.RedAgateWebView;

/**
 *
 */
public class RedAgateWebClient extends RedAgateBaseWebClient {

    /**
     * 构造器
     *
     * @param webView WebView 实例对象
     */
    public RedAgateWebClient(WebView webView) {
        super(webView);
    }

    /**
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        // 如果还没开始加载图片,现在开始加载.
        if (!webView.getSettings().getLoadsImagesAutomatically()) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        }
        // 如果阻止了图片网络,现在关闭阻止.
        if (webView.getSettings().getBlockNetworkImage()) {
            webView.getSettings().setBlockNetworkImage(false);
        }
        //处理代码
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        // 触发监听器
        if (((RedAgateWebView) view).getWebViewLoadListener() != null) {
            ((RedAgateWebView) view).getWebViewLoadListener().onNotFound();
        }
        // 处理代码
        super.onReceivedError(view, request, error);
    }

}
