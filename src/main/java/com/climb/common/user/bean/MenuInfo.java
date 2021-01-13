package com.climb.common.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author lht
 * @since 2021/1/13 17:02
 */
public interface MenuInfo extends Serializable {
    /**
     * 菜单id
     */
    String getId();
    /**
     * 菜单父级id
     */
    String getParentId();
    /**
     * 菜单名称
     */
    String getName();
    /**
     * 图标
     */
    String getIcon();
    /**
     * 叶子菜单结合
     */
    List<MenuInfo> getLeafMenuInfos();
}
