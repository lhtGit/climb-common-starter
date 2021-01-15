package com.climb.common.user.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

/**
 * 默认用户详情实现
 * @author lht
 * @since 2021/1/13 15:47
 */
@Setter
@Getter
public class UserInfoDetails extends UserInfoBase {
    /**
     * 获取资源信息
     */
    private Collection<ResourceInfo> resourceInfo;
    /**
     * 获取菜单信息（需要排序）
     */
    private List<MenuInfo> menuInfo;
    /**
     * 获取角色信息
     */
    private Collection<Roleinfo> roleInfo;

}
