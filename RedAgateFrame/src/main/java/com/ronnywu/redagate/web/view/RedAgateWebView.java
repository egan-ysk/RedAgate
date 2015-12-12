package com.ronnywu.redagate.web.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.listener.RedAgateLogTask;
import com.ronnywu.redagate.utils.file.RedAgateFileUtil;
import com.ronnywu.redagate.utils.network.RedAgateNetworkUtil;
import com.ronnywu.redagate.web.client.RedAgateWebClient;
import com.ronnywu.redagate.web.client.WebViewLoadListener;
import com.ronnywu.redagate.web.core.RedAgateBaseWebClient;

import java.io.File;

/**
 * 自定义WebView
 */
public class RedAgateWebView extends WebView {

    /**
     * WebView加载监听器.
     */
    private WebViewLoadListener webViewLoadListener;

    /**
     * 上下文
     */
    private Context context;

    /**
     * 进度条
     */
    private ProgressBar progressbar;

    /**
     * WebViewClient
     */
    private RedAgateWebClient redAgateWebClient;

    /**
     * WebChromeClient
     */
    private WebChromeClient webChromeClient;

    /**
     * WebView 缓存目录
     */
    private File webViewCacheDir;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public RedAgateWebView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public RedAgateWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认风格属性集
     */
    public RedAgateWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 实例化操作
     */
    private void initView() {
        // 优化 WebView 控件.
        optimizationWebView();
        // 添加进度条
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 3, 0, 0));
        addView(progressbar);
        // 创建 Client
        redAgateWebClient = new RedAgateWebClient(this);
        webChromeClient = new WebChromeClient();
        // 设置 Client
        setWebViewClient(redAgateWebClient);
        setWebChromeClient(webChromeClient);
    }

    /**
     * 优化 WebView 设置
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void optimizationWebView() {
        // 创建 WebView 缓存目录
        webViewCacheDir = new File(context.getFilesDir().getAbsolutePath() + "/WebCache");
        // 设置  Application Caches 缓存目录
        getSettings().setDatabasePath(webViewCacheDir.toString());
        // 设置  Application Caches 缓存目录
        getSettings().setAppCachePath(webViewCacheDir.toString());
        // 设置定位的数据库路径
        getSettings().setGeolocationDatabasePath(webViewCacheDir.toString());
        // 解决无法打开天猫.淘宝问题
        getSettings().setDomStorageEnabled(true);
        // 支持JS脚本
        getSettings().setJavaScriptEnabled(true);
        // 设置允许缩放
        getSettings().setSupportZoom(true);
        // 设置此属性，可任意比例缩放。
        getSettings().setUseWideViewPort(true);
        // 缩放至屏幕的大
        getSettings().setLoadWithOverviewMode(true);
        // 允许WebView使用File协议
        getSettings().setAllowFileAccess(true);
        // 开启本地DOM存储
        getSettings().setDomStorageEnabled(true);
        // 开启插件支持
        getSettings().setPluginState(WebSettings.PluginState.ON);
        // 开启 DOM storage API 功能
        getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        getSettings().setDatabaseEnabled(true);
        // 开启 Application Caches 功能
        getSettings().setAppCacheEnabled(true);
        // 开启定位功能
        getSettings().setGeolocationEnabled(true);
        // 提高渲染的优先级
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 阻止图片网络数据
        getSettings().setBlockNetworkImage(true);
        // 有网
        if (RedAgateNetworkUtil.getNetworkIsAvailable()) {
            // 设置 缓存模式为默认
            getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            // 设置 缓存模式为打开网络则更新
            getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 根据SDK版本,决定是否最后加载图片.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4.2
            // 先不要自动加载图片，等页面finish后再发起图片加载
            getSettings().setLoadsImagesAutomatically(true);
        } else {
            // 不开启图片延后加载
            getSettings().setLoadsImagesAutomatically(false);
        }
        // 根据SDK版本,决定是否允许通过file url加载的Javascript读取其他的本地文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getSettings().setAllowFileAccessFromFileURLs(true);
        }
    }

    /**
     * 添加一个JS消息处理器
     */
    public void addJsMessageHandler(String handlerName, RedAgateBaseWebClient.RedAgateJsMessageHandler handler) {
        if (redAgateWebClient == null) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().ERROR).e(RedAgateWebView.class.getSimpleName() + "尚未初始化完成");
            return;
        }
        redAgateWebClient.registerHandler(handlerName, handler);
    }

    /**
     * ViewPager兼容,非第一个WebView也可点击.
     *
     * @param ev 点击事件
     * @return 是否消耗事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 清除缓存.
     *
     * @param context 上下文
     */
    public void cleanCache(Context context) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i("准备删除WebView缓存,如果存在 >>> 缓存路径 >>> " + webViewCacheDir.getAbsolutePath());
        if (webViewCacheDir.exists()) {
            RedAgateFileUtil.deleteFile(webViewCacheDir);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 规则:
     * 如果没有网络,且没有设置监听器,则加载
     *
     * @param url the URL of the resource to load
     */
    @Override
    public void loadUrl(String url) {
        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(RedAgateWebView.class.getSimpleName() + " 准备加载 url >>> " + url);
        // 检查URL有效性.
        if (URLUtil.isValidUrl(url)) {
            // 网络不可用,加载本地地址.
            if (!RedAgateNetworkUtil.getNetworkIsAvailable()) {
                // 触发监听器.
                if (webViewLoadListener != null) {
                    webViewLoadListener.onNotNetwork();
                } else {
                    super.loadUrl(url);
                }
                RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i(" >>> 抱歉,当前网络不可用!");
            } else {
                super.loadUrl(url);
            }
        } else {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().ERROR).e(RedAgateWebView.class.getSimpleName() + " 加载的URL无效! url >>> " + url);
        }
    }

    public WebViewLoadListener getWebViewLoadListener() {
        return webViewLoadListener;
    }

    public void setWebViewLoadListener(WebViewLoadListener webViewLoadListener) {
        this.webViewLoadListener = webViewLoadListener;
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }
}
