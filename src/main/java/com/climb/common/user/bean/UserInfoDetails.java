package com.climb.common.user.bean;

import java.util.Collection;
import java.util.List;

/**
 * @author lht
 * @since 2021/1/13 15:44
 */
public interface UserInfoDetails extends UserInfoBase{
    /**
     * 获取资源信息
     */
    Collection<ResourceInfo> getResourceInfo();
    /**
     * 获取菜单信息（需要排序）
     */
    List<MenuInfo> getMenuInfo();
    /**
     * 获取角色信息
     */
    Collection<RoleInfo> getRoleInfo();

}
