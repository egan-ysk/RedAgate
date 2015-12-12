package com.ronnywu.redagate.database.entitys;

import com.j256.ormlite.field.DatabaseField;

/**
 * 数据库基类.
 *
 * @author ronnywu
 * @date 2015-10-23
 * @time 14:27:27
 */
public class RedAgateDbEntity {

    /**
     * 创建时间.
     */
    @DatabaseField(columnName = "createTime")
    public Long createTime;

    /**
     * 更新时间,
     * 这个字段名称不得修改,如果修改了这个字段名称,
     * 对应的需要修改连接中的SQL语句,否则会引起程序报错.
     *
     * @see com.ronnywu.redagate.database.controller.RedAgateDbHelper#findAllByLimit(Dao, long, long)
     */
    @DatabaseField(columnName = "updateTime")
    public Long updateTime;

    @Override
    public String toString() {
        return "DbBase{"
                + "createTime="
                + createTime
                + ", updateTime="
                + updateTime
                + '}';
    }
}
