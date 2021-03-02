package com.climb.common.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lht
 * @since 2021/3/1 09:18
 */
@Setter
@Getter
public class Log {
    private String method;
    private String url;
    private String userName;
    private String userId;
    private String requestParams;
    private String startTime;
    private String endTime;
    private Boolean isSuccess;
    private Exception exception;

    @Override
    public String toString() {
        return  "请求信息：  "+url+"   "+method+"    "+startTime+" —— "+endTime+"  "+(isSuccess?"成功":"失败")+
                "   用户信息：  "+userName+"("+userId+")"+
                "   请求参数：  "+requestParams;

    }
}
