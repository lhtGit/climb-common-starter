package com.climb.common.bean;

import java.util.List;

/**
 * 分页bean接口，用于正mybatis的page
 * @author lht
 * @since 2021/1/28 10:45
 */
public interface PageDefinition<D> {
    /**
     * 当前页
     *
     * @return 当前页
     */
    long getCurrent();
    /**
     * 获取每页显示条数
     *
     * @return 每页显示条数
     */
    long getSize();
    /**
     * 当前满足条件总行数
     *
     * @return 总条数
     */
    long getTotal();
    /**
     * 分页记录列表
     *
     * @return 分页对象记录列表
     */
    List<D> getRecords();
}
