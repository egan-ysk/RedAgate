package com.ronnywu.redagate.listener;

/**
 * 框架中运行时状态存储类.
 *
 * @author ronnywu
 * @date 2015-11-20
 * @time 13:46:53
 */
public class RedAgateState {

    /**
     * 单例
     */
    private static RedAgateState ourInstance = new RedAgateState();
    /**
     * 本地网络发生变化时(跟上次不同),上次的网络状态.
     * 用于过滤重复信息,防止多次触发广播接收器.
     */
    public int lastNetworkState = 2;

    /**
     * 单例
     */
    private RedAgateState() {
    }

    /**
     * 单例
     *
     * @return 单例实例
     */
    public static RedAgateState getInstance() {
        return ourInstance;
    }
}
