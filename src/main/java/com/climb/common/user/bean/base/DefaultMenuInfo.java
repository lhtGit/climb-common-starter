package com.climb.common.user.bean.base;

import com.climb.common.user.bean.MenuInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lht
 * @since 2021/1/13 17:07
 */
@Getter
@Setter
public class DefaultMenuInfo implements MenuInfo {
    /**
     * 菜单id
     */
    String id;
    /**
     * 菜单父级id
     */
    String parentId;
    /**
     * 菜单名称
     */
    String name;
    /**
     * 图标
     */
    String icon;
    /**
     * 叶子菜单结合
     */
    List<MenuInfo> leafMenuInfos;
}
