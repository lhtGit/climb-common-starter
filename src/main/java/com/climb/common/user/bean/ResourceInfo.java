package com.climb.common.user.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lht
 * @since 2021/1/13 15:49
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceInfo  {
    /**
     * 资源id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 方法（POST/..）必须为大写
     */
    private String method;
}
