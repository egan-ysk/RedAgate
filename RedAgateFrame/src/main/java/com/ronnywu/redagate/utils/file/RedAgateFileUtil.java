package com.ronnywu.redagate.utils.file;

import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.listener.RedAgateLogTask;

import java.io.File;

/**
 * 文件工具类
 */
public class RedAgateFileUtil {

    /**
     * 递归删除 文件/文件夹
     *
     * @param file 文件
     */
    public static void deleteFile(File file) {

        RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i("递归删除文件/文件夹 >>> 删除路径 >>> " + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().RUNING).i("递归删除文件/文件夹 >>> 目标文件不存在 >>> " + file.getAbsolutePath());
        }
    }
}
