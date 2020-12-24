package com.climb.common.user.bean;

import com.climb.common.user.UserDetails;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存在reids中,在请求的header中
 * @author lht
 * @date 2020/9/18 16:52
 */
@Data
public class DefaultUserDetails implements Serializable, UserDetails {

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
