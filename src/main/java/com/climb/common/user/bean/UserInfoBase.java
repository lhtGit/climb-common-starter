package com.climb.common.user.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lht
 * @since 2021/1/13 15:45
 */
public interface UserInfoBase extends Serializable {

    /**
     * 用户id
     */
    String getId();
    /**
     * 用户名
     */
    String getUsername();
    /**
     * 昵称
     */
    String getName();
    /**
     * 手机号
     */
    String getPhone();
    /**
     * mail
     */
    String getMail();
    /**
     * 性别 0 女 1 男
     */
    Integer getGender();
}
