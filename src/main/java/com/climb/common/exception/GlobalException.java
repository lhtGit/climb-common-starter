package com.climb.common.exception;

import lombok.Getter;
import lombok.Setter;

/*
 * 全局异常
 * @Author lht
 * @Date  2020/9/14 17:58
 */
@Getter
@Setter
public class GlobalException extends RuntimeException{
    private int code;

    public GlobalException() {
    }

    public GlobalException(ErrorMessage errorMessage) {
        super(errorMessage.getMsg());
        this.code = errorMessage.getCode();
    }

    public GlobalException(ErrorMessage errorMessage,Throwable cause) {
        super(errorMessage.getMsg(),cause);
        this.code = errorMessage.getCode();
    }
    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }

    public GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GlobalException(int code) {
        this.code = code;
    }

    public GlobalException(String message, int code) {
        super(message);
        this.code = code;
    }

    public GlobalException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public GlobalException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
