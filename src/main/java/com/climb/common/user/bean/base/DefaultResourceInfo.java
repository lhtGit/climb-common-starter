package com.climb.common.user.bean.base;

import com.climb.common.user.bean.ResourceInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lht
 * @since 2021/1/13 15:49
 */
@Getter
@Setter
public class DefaultResourceInfo implements ResourceInfo {
    /**
     * 资源id
     */
    String id;
    /**
     * 名称
     */
    String name;
    /**
     * 路径
     */
    String path;
    /**
     * 方法（POST/..）必须为大写
     */
    String method;
}
