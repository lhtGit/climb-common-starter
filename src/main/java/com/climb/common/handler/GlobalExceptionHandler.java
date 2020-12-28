package com.climb.common.handler;

import com.climb.common.bean.Result;
import com.climb.common.exception.GlobalException;
import com.climb.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * @author lht
 * @since 2020/9/25 11:54
 */
@Slf4j
@ControllerAdvice
@ConditionalOnMissingClass({"io.seata.spring.annotation.GlobalTransactionalInterceptor"})
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.info("GlobalExceptionHandler init");
    }

    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public Result<String> globalException(HttpServletRequest req, HttpServletResponse response, GlobalException e){
        response.setStatus(200);
        log.error("业务异常：",e);
        return ResultUtil.error(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> error(HttpServletRequest req, HttpServletResponse response, Exception e){
        response.setStatus(200);
        log.error("未知异常：",e);
        return ResultUtil.error("服务器异常");
    }

}
