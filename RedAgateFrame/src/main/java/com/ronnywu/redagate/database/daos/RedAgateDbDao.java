package com.ronnywu.redagate.database.daos;

import java.util.List;

/**
 * Dao基本的操作.
 * <p/>
 * 创建基本的DAO操作封装接口
 *
 * @param <T> 操作的实体对象,它代表一个泛型
 * @author ronnywu
 * @version 1.0
 * @date 2015-10-27
 * @time 09:57:31
 */
public interface RedAgateDbDao<T> {

    /**
     * 保存对象.
     *
     * @param entity 想要保存到数据库的实体对象
     * @return 操作是否成功的标识
     */
    boolean save(final T entity);

    /**
     * 更新对象.
     *
     * @param entity 想要更新到数据库的实体对象
     * @return 操作是否成功的标识
     */
    boolean update(final T entity);

    /**
     * 删除对象.
     *
     * @param entity 想要从数据库删除的实体对象
     * @return 操作是否成功的标识
     */
    boolean delete(final T entity);

    /**
     * 查询所有.
     *
     * @return 查询所有指定实体在数据库中的信息
     */
    List<T> findAll();

    /**
     * 查询指定ID.
     *
     * @param id 指定ID
     * @return 查询指定ID实体在数据库中的信息
     */
    T findById(final String id);

    /**
     * 查询数量.
     *
     * @return 查询指定实体在数据库中的总条数
     */
    long countAll();

    /**
     * 分页查询.
     *
     * @param limit  查询数量
     * @param offset 开始查询下标
     * @return 查询指定下标开始, 指定数量的实体信息
     */
    List<T> findAllByLimit(final long offset, final long limit);
}
