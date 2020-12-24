package com.climb.common.user;

import com.climb.common.user.UserDetails;

/**
 * 获得用户信息
 * @author lht
 * @since 2020/9/28 10:39
 */
public interface UserDetailsService {


    /**
     * 获得用户信息 {@link UserDetails}
     * @author lht
     * @since  2020/9/28 10:41
     */
    UserDetails getUserDetails();

}
