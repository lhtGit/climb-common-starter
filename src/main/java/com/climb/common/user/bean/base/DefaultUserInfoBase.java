package com.climb.common.user.bean.base;

import com.climb.common.user.bean.UserInfoBase;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author lht
 * @since 2021/1/13 15:49
 */
@Getter
@Setter
public class DefaultUserInfoBase implements UserInfoBase {
    /**
     * 用户id
     */
    String id;
    /**
     * 用户名
     */
    String username;
    /**
     * 昵称
     */
    String name;
    /**
     * 手机号
     */
    String phone;
    /**
     * mail
     */
    String mail;
    /**
     * 性别 0 女 1 男
     */
    Integer gender;
}
