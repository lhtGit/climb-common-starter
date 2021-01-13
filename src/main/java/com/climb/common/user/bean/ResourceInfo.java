package com.climb.common.user.bean;

import java.io.Serializable;

/**
 * @author lht
 * @since 2021/1/13 15:46
 */
public interface ResourceInfo extends Serializable {
    /**
     * 资源id
     */
    String getId();
    /**
     * 名称
     */
    String getName();
    /**
     * 路径
     */
    String getPath();
    /**
     * 方法（POST/GET..）必须为大写
     */
    String getMethod();

}
