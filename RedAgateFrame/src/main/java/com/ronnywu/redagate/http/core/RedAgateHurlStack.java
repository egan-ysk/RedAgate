package com.ronnywu.redagate.http.core;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 自定义 HurlStack.
 * <p/>
 * 使用 OkHttp 作为传输层实现
 *
 * @author ronnywu
 * @date 2015-09-15
 * @time 15:53:26
 */
public final class RedAgateHurlStack extends HurlStack {

    /**
     * 单例模式.
     */
    private static RedAgateHurlStack ourInstance = new RedAgateHurlStack();
    /**
     * 引用一个 OkHttpClient 对象.
     */
    private OkHttpClient okHttpClient;

    /**
     * 单例模式.
     */
    private RedAgateHurlStack() {
        okHttpClient = new OkHttpClient();
        // 连接超时设置
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        // 请求超时设置
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        // 响应超时设置
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
    }

    /**
     * 单例模式.
     */
    public static RedAgateHurlStack getInstance() {
        return ourInstance;
    }

    /**
     * 重写创建连接方法,
     * 使用 OkHttpClient 替换 Volley 的连接.
     *
     * @param url uniform resource locator(统一资源定位器)
     * @throws IOException 抛出一个I/O异常,说明打开网络连接出错
     */
    @Override
    protected HttpURLConnection createConnection(final URL url) throws IOException {
        // URL 网络连接工厂,用于创建连接
        OkUrlFactory okUrlFactory = new OkUrlFactory(okHttpClient);
        return okUrlFactory.open(url);
    }
}
