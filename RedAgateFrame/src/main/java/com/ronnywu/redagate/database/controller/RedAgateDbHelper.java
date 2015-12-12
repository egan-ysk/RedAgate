package com.ronnywu.redagate.database.controller;

import com.j256.ormlite.dao.Dao;
import com.ronnywu.redagate.crash.tools.RedAgateLog;
import com.ronnywu.redagate.database.entitys.RedAgateDbEntity;
import com.ronnywu.redagate.listener.RedAgateLogTask;

import java.sql.SQLException;
import java.util.List;

/**
 * dao 帮助类,当前类可以理解为模板类.
 * <p/>
 * 对于SQL语句而言,增删查改的语句都是一样的,不一样的地方是使用的方法和参数,
 * 为此,当前类诞生了,这里将处理的DAO和实体类抽取出来作为泛型,以相同的SQL处理为模板.
 *
 * @author ronnywu
 * @version v1.0
 * @date 2015-10-28
 * @time 14:08:57
 */
@SuppressWarnings({"unchecked", "unused", "JavaDoc"})
public class RedAgateDbHelper<K extends Dao, V extends RedAgateDbEntity> {
    /**
     * 保存对象.
     *
     * @param dao    Dao类对应的子类或实现类
     * @param entity 操作实体对象
     * @return 是否成功
     */
    public final synchronized boolean save(final K dao, final V entity) {
        boolean state = false;
        try {
            int result = dao.create(entity);
            if (result > 0) {
                state = true;
            }
        } catch (SQLException e) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().DB).e(e);
        }
        return state;
    }

    /**
     * 更新对象.
     *
     * @param dao    Dao类对应的子类或实现类
     * @param entity 操作实体对象
     * @return 是否成功
     */
    public final synchronized boolean update(final K dao, final V entity) {
        boolean state = false;
        try {
            int result = dao.update(entity);
            if (result > 0) {
                state = true;
            }
        } catch (SQLException e) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().DB).e(e);
        }
        return state;
    }

    /**
     * 删除对象.
     *
     * @param dao    Dao类对应的子类或实现类
     * @param entity 操作实体对象
     * @return 是否成功
     */
    public final synchronized boolean delete(final K dao, final V entity) {
        boolean state = false;
        try {
            int result = dao.delete(entity);
            if (result > 0) {
                state = true;
            }
        } catch (SQLException e) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().DB).e(e);
        }
        return state;
    }

    /**
     * 查询所有.
     *
     * @param dao Dao类对应的子类或实现类
     * @return 查询到的对象列表
     */
    public final List<V> findAll(final K dao) {
        List<V> objV = null;
        try {
            objV = dao.queryForAll();
        } catch (SQLException e) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().DB).e(e);
        }
        return objV;
    }

    /**
     * 查询指定ID.
     *
     * @param dao Dao类对应的子类或实现类
     * @param id  数据库ID
     * @return 查询到的对象
     */
    public final V findById(final K dao, final String id) {
        V objV = null;
        try {
            objV = (V) dao.queryForId(id);
        } catch (SQLException e) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().DB).e(e);
        }
        return objV;
    }

    /**
     * 查询数量.
     *
     * @param dao Dao类对应的子类或实现类
     * @return 查询对象在数据库中的总条数
     */
    public final long countAll(final K dao) {
        long count = 0;
        try {
            count = dao.countOf();
        } catch (SQLException e) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().DB).e(e);
        }
        return count;
    }

    /**
     * 分页查询.
     *
     * @param dao    Dao类对应的子类或实现类
     * @param limit  一次查询的数量
     * @param offset 开始查询下标
     * @return 从指定下标开始查询指定数量的对象列表
     */
    public final List<V> findAllByLimit(final K dao, final long offset, final long limit) {
        List<V> query = null;
        try {
            query = dao.queryBuilder().
                    orderBy("updateTime", false).
                    offset(offset).
                    limit(limit).
                    query();
        } catch (SQLException e) {
            RedAgateLog.wrlog(RedAgateLogTask.getInstance().DB).e(e);
        }
        return query;
    }
}
