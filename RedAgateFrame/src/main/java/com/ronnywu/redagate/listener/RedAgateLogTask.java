package com.ronnywu.redagate.listener;

/**
 * 日志跟踪系统,task 索引.
 *
 * @author ronnywu
 * @date 2015-11-20
 * @time 14:17:53
 */
public class RedAgateLogTask {

    /**
     * 单例
     */
    private static RedAgateLogTask ourInstance = new RedAgateLogTask();
    /**
     * 结构化运行流程.
     */
    public String RUNING = "RA-RUN";
    /**
     * HTTP系列索引.
     */
    public String HTTP = "RA-HTTP";
    /**
     * 数据库系列索引.
     */
    public String DB = "RA-DB";
    /**
     * 网络相关索引.
     */
    public String NET = "RA-NET";
    /**
     * 异常相关索引.
     */
    public String CRASH = "RA-CRASH";
    /**
     * 异常相关索引.
     */
    public String ERROR = "RA-ERROR";

    /**
     * 单例
     */
    private RedAgateLogTask() {
    }

    /**
     * 单例
     *
     * @return 单例实例
     */
    public static RedAgateLogTask getInstance() {
        return ourInstance;
    }
}
