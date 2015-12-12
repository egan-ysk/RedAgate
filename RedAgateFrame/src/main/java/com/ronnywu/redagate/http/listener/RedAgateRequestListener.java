package com.ronnywu.redagate.http.listener;

/**
 * 请求监听器
 * 框架拦截到监听以后,自动去分配解析对象,解析完毕以后,调用当前方法给界面,
 * 为了方便理解,这里命名为RequestListener
 * 这里需要制定泛型,后台会自己调用解析分发,调用相关解析类进行解析
 *
 * @author ronnywu
 * @date 2015-09-15
 * @time 15:53:26
 */
public interface RedAgateRequestListener<T> {

    /**
     * 请求成功的回调
     *
     * @param object 请求成功返回的对象
     */
    void requestSuccess(T object);

    /**
     * 请求失败的回调
     * 触发该回调情形如下:
     * 1. 请求错误(网络错误,服务器错误,服务器宕机...)
     * 2. http错误(超时,认证失败...)
     * 3. 解析错误
     *
     * @param errorCode    错误ID
     * @param errorMessage 错误消息
     */
    void requestFailure(String errorCode, String errorMessage);

}
