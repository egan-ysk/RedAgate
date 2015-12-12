package com.ronnywu.redagate.crash.tools;

import android.util.Log;

import java.util.Hashtable;

/**
 * 日志管理类.
 */
@SuppressWarnings("unused")
public final class RedAgateLog {

    /**
     * 吴荣的姓名.
     */
    private static final String WR = "@WR ";
    /**
     * 王纯纲的姓名.
     */
    private static final String WCG = "@WCG ";
    /**
     * 朱金龙的姓名.
     */
    private static final String ZJL = "@ZJL ";
    /**
     * 闫少坤的日志.
     */
    private static final String YSK = "@YSK ";

    /**************************************** 配置信息 ****************************************/
    /**
     * Debug 模式.
     */
    public static boolean DebugModel = true;
    /**
     * 存放所有日志对象的 Hash table.
     */
    private static Hashtable<String, RedAgateLog> hashTable = new Hashtable<>();
    /**
     * 默认 tag 名称.
     */
    private String tag = "[Not set TAG]";
    /**
     * 用户名称.
     */
    private String userName;

    /**
     * 日志构造器.
     *
     * @param tag      日志标签
     * @param userName 用户名称
     */
    private RedAgateLog(final String tag, final String userName) {
        this.tag = tag;
        this.userName = userName;
    }

    /**
     * 吴荣日志构造器.
     *
     * @param tag 日志标签
     * @return 设置好用户参数的日志对象
     */
    public static RedAgateLog wrlog(final String tag) {
        return getLogger(tag, WR);
    }

    /**
     * 王纯纲日志构造器.
     *
     * @param tag 日志标签
     * @return 设置好用户参数的日志对象
     */
    public static RedAgateLog wcglog(final String tag) {
        return getLogger(tag, WCG);
    }

    /**
     * 朱金龙日志构造器.
     *
     * @param tag 日志标签
     * @return 设置好用户参数的日志对象
     */
    public static RedAgateLog zjllog(final String tag) {
        return getLogger(tag, ZJL);
    }


    /***************************************** 构造器 ****************************************/

    /**
     * 朱金龙日志构造器.
     *
     * @param tag 日志标签
     * @return 设置好用户参数的日志对象
     */
    public static RedAgateLog ysklog(final String tag) {
        return getLogger(tag, YSK);
    }

    /**
     * 获取 log 对象.
     *
     * @param tag      日志标签
     * @param userName 用户名称
     * @return 根据用户名和标签返回的日志对象
     */
    private static RedAgateLog getLogger(final String tag, final String userName) {
        RedAgateLog redAgateLog = hashTable.get(userName);
        if (redAgateLog == null) {
            redAgateLog = new RedAgateLog(tag, userName);
            hashTable.put(userName, redAgateLog);
        } else {
            redAgateLog.tag = tag;
        }
        return redAgateLog;
    }

    /**************************************** 获取方法名 ****************************************/

    /**
     * 获取方法名.
     *
     * @return 当前打印日志时所在的函数名称
     */
    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement stackTraceElement : sts) {
            if (stackTraceElement.isNativeMethod()) {
                continue;
            }
            if (stackTraceElement.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (stackTraceElement.getClassName().equals(this.getClass().getName())) {
                continue;
            }
            return userName + "[ " + Thread.currentThread().getName() + ": "
                    + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + " "
                    + stackTraceElement.getMethodName() + " ]";
        }
        return null;
    }

    /**************************************** 日志方法 ****************************************/

    /**
     * verbose log.
     *
     * @param obj 日志打印对象
     */
    public void v(final Object obj) {
        if (DebugModel) {
            String name = getFunctionName();
            if (name != null) {
                Log.v(tag, name + " - " + obj.toString());
            } else {
                Log.v(tag, obj.toString());
            }
        }
    }

    /**
     * debug log.
     *
     * @param obj 日志打印对象
     */
    public void d(final Object obj) {
        if (DebugModel) {
            String name = getFunctionName();
            if (name != null) {
                Log.d(tag, name + " - " + obj.toString());
            } else {
                Log.d(tag, obj.toString());
            }
        }
    }

    /**
     * info log.
     *
     * @param obj 日志打印对象
     */
    public void i(final Object obj) {
        if (DebugModel) {
            String name = getFunctionName();
            if (name != null) {
                Log.i(tag, name + " - " + obj.toString());
            } else {
                Log.i(tag, obj.toString());
            }
        }
    }

    /**
     * warning log.
     *
     * @param obj 日志打印对象
     */
    public void w(final Object obj) {
        if (DebugModel) {
            String name = getFunctionName();
            if (name != null) {
                Log.w(tag, name + " - " + obj.toString());
            } else {
                Log.w(tag, obj.toString());
            }
        }
    }

    /**
     * error log.
     *
     * @param obj 日志打印对象
     */
    public void e(final Object obj) {
        if (DebugModel) {
            String name = getFunctionName();
            if (name != null) {
                Log.e(tag, name + " - " + obj.toString());
            } else {
                Log.e(tag, obj.toString());
            }
        }
    }

    /**
     * error log.
     *
     * @param ex 日志输出错误
     */
    public void e(final Exception ex) {
        if (DebugModel) {
            Log.e(tag, "error", ex);
        }
    }

    /**
     * error log.
     *
     * @param ex      日志输出错误
     * @param message 日志消息
     */
    public void e(final String message, final Exception ex) {
        if (DebugModel) {
            Log.e(tag, message, ex);
        }
    }

}
