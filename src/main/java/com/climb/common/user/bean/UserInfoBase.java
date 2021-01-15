package com.climb.common.user.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author lht
 * @since 2021/1/13 15:49
 */
@Getter
@Setter
public class UserInfoBase  {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * mail
     */
    private String mail;
    /**
     * 性别 0 女 1 男
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birthday;
}
