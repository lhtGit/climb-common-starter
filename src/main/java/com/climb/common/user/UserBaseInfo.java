package com.climb.common.user;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户基础信息
 * @author lht
 * @since 2020/9/28 10:39
 */
@Data
public class UserBaseInfo implements Serializable{

    private static final long serialVersionUID = -8717357144429994025L;
    /**
     * 用户标识
     * @author lht
     * @since  2020/11/26 11:48
     */
    private String id;
    /**
     * 用户昵称
     * @author lht
     * @since  2020/11/26 11:49
     */
    private String name;

    private String username;

    private String phone;

    private String email;
}
