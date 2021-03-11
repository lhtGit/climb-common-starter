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
     */
    String USER_INFO = "user_info";
    /**
     * 任命用户 由系统指定，优先级高于gateway获取的登录用户信息
     */
    String APPOINT_USER_INFO = "appoint_user_info";

    /**
     * utf-8
     */
    String UTF8 = "UTF-8";
    /**
     * 用户负载均衡websocket instance redis存储前缀
     */
    String PREFIX_USER_WEBSOCKET_INSTANCE = "user:websocket:message:instance:";
}
