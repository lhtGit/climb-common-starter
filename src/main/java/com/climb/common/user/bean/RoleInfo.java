package com.climb.common.user.bean;

import java.io.Serializable;

/**
 * @author lht
 * @since 2021/1/13 15:49
 */
public interface RoleInfo extends Serializable {
    /**
     * 角色id
     */
    String getId();
    /**
     * 角色名称
     */
    String getName();
}
