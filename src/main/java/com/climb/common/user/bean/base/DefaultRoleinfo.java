package com.climb.common.user.bean.base;

import com.climb.common.user.bean.RoleInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lht
 * @since 2021/1/13 15:50
 */
@Getter
@Setter
public class DefaultRoleinfo implements RoleInfo {

    private String id;

    private String name;

}
