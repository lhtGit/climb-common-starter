package com.climb.common.user.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lht
 * @since 2021/1/13 17:07
 */
@Getter
@Setter
public class MenuInfo {
    /**
     * 菜单id
     */
    private String id;
    /**
     * 菜单父级id
     */
    private String parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 叶子菜单结合
     */
    private List<MenuInfo> leafMenuInfos;
}
