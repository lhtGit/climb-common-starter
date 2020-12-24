package com.climb.common.user.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lht
 * @since 2020/11/26 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationFrom {
    /**
     * 认证主体
     * @author lht
     * @since  2020/11/26 15:11
     */
    private String principal;
    /**
     * 证书
     * @author lht
     * @since  2020/11/26 15:11
     */
    private String credentials;
    /**
     * 登录类型
     * @author lht
     * @since  2020/11/26 16:29
     */
    private UserLoginType userLoginType;
}
