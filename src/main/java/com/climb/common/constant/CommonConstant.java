package com.climb.common.constant;

/*
 *
 * @Author lht
 * @Date  2020/9/14 09:40
 */
public interface CommonConstant {
    int SUCCESS_CODE = 200;
    int ERROR_CODE = 500;
    String SUCCESS_MSG = "操作成功";
    String ERROR_MSG = "操作失败";


    /**
     * 用户信息在header的key
     * @author lht
     * @since  2020/11/26 13:56
     */
    String USER_INFO = "user_info";
    /**
     * 任命用户 由系统指定，优先级高于gateway获取的登录用户信息
     * @author lht
     * @since  2021/2/1 16:23
     */
    String APPOINT_USER_INFO = "appoint_user_info";

    /**
     * utf-8
     * @author lht
     * @since  2020/11/26 15:00
     */
    String UTF8 = "UTF-8";
}
