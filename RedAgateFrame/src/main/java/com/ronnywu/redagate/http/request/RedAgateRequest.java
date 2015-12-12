package com.ronnywu.redagate.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * 通用请求
 * <p/>
 * 继承并扩展 StringRequest , 让当前类成为一个通用请求
 * <p/>
 * 继承 Volley 的 StringRequest 类,并扩展了参数设置,支持POST请求,请求头设置,超时设置.
 * 使用时同 StringRequest
 *
 * @author ronnywu
 * @date 2015-09-15
 * @time 15:53:26
 */
@SuppressWarnings("JavaDoc")
public class RedAgateRequest extends StringRequest {

    /**
     * HTTP请求参数
     */
    private Map<String, String> params;

    /**
     * 请求时带参的构造器
     * POST等方式
     *
     * @param method        请求方式,具体方式同 Volley {@link Method}
     * @param url           URL地址
     * @param listener      Volley 请求成功的监听器
     * @param errorListener Volley 请求失败的监听器
     * @param params        请求参数
     */
    public RedAgateRequest
    (
            int method,
            String url,
            Map<String, String> params,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        super(method, url, listener, errorListener);
        this.params = params;
    }

    /**
     * 请求时不带参的构造器
     * GET等方式
     *
     * @param method        请求方式,具体方式同 Volley {@link Method}
     * @param url           URL地址
     * @param listener      Volley 请求成功的监听器
     * @param errorListener Volley 请求失败的监听器
     */
    public RedAgateRequest(
            int method,
            String url,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        super(method, url, listener, errorListener);
    }


    /**
     * 获取请求参数
     * 获取 POST 或者 PUT 请求模式时的参数,Auth认证时可能会用到,这里会抛出认证异常
     *
     * @throws AuthFailureError 认证异常
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    /**
     * 子类可以重写当前方法,自定义HTTP请求头
     *
     * @throws AuthFailureError 认证异常
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

}
